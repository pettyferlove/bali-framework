package com.github.bali.attachment.service.impl;

import cn.hutool.core.lang.UUID;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.constants.StorageType;
import com.github.bali.attachment.domain.dto.AliyunOSSUploadResult;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.entity.AttachmentInfo;
import com.github.bali.attachment.factory.IAttachmentServiceFactory;
import com.github.bali.attachment.service.IAttachmentInfoService;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.attachment.utils.FileUtil;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.core.framework.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Petty
 */
@Slf4j
@Service
public class AttachmentOperaServiceImpl implements IAttachmentOperaService {

    private final IAttachmentServiceFactory factory;

    private final IAttachmentInfoService attachmentInfoService;

    public AttachmentOperaServiceImpl(IAttachmentServiceFactory factory, IAttachmentInfoService attachmentInfoService) {
        this.factory = factory;
        this.attachmentInfoService = attachmentInfoService;
    }

    @Override
    public UploadResult upload(String userId, Upload upload, MultipartFile file) {
        IAttachmentService attachmentService = factory.create(upload.getStorage());
        AliyunOSSUploadResult uploadResult;
        String tempId = UUID.fastUUID().toString();
        FileType parse = FileType.parse(file.getContentType());
        String contextPath = "temp" + File.separator + tempId + parse.getExpansionName();
        File tempFile = null;
        File f = null;
        try {
            tempFile = new File(contextPath);
            if (!tempFile.exists()) {
                //生成图片文件
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            }
            if (parse.isImage() && upload.getCompress()) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                float quality = 0.7f;
                float scale = 0.7f;
                if (!FileUtil.checkFileSize(file.getSize(), 30, "K")) {
                    if (image.getWidth() > 1980) {
                        quality = 0.4f;
                        scale = 0.5f;
                    }
                }
                Thumbnails.of(file.getInputStream()).imageType(BufferedImage.TYPE_INT_RGB).outputQuality(quality).scale(scale).toFile(contextPath);
            }
            f = new File(contextPath);
            uploadResult = attachmentService.upload(upload, f, parse);
        } catch (IOException e) {
            throw new BaseRuntimeException("file process error");
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
            if (f != null) {
                f.delete();
            }
        }
        attachmentInfoService.save(userId, uploadResult.getFileId(), file.getOriginalFilename(), uploadResult.getMd5(), uploadResult.getPath(), upload, file.getContentType(), file.getSize());
        return Optional.ofNullable(ConverterUtil.convert(uploadResult, new UploadResult())).orElseGet(UploadResult::new);
    }

    @Override
    public void download(String id, HttpServletResponse response) throws Exception {
        Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
        if (attachmentInfoOptional.isPresent()) {
            AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
            IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
            attachmentService.download(attachmentInfo.getPath(), response.getOutputStream());
            response.setCharacterEncoding("utf-8");
            response.setContentType(attachmentInfo.getFileType());
            String fileName = null;
            try {
                fileName = URLEncoder.encode(attachmentInfo.getFileName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        } else {
            throw new BaseRuntimeException("not found file", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void view(String id, HttpServletResponse response) throws Exception {
        Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
        if (attachmentInfoOptional.isPresent()) {
            AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
            String fileType = attachmentInfo.getFileType();
            if (FileType.IMAGE_JPEG == FileType.parse(fileType) || FileType.IMAGE_JPG == FileType.parse(fileType)) {
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                response.setCharacterEncoding("utf-8");
                response.setContentType(attachmentInfo.getFileType());
                attachmentService.download(attachmentInfo.getPath(), response.getOutputStream());
            } else {
                throw new BaseRuntimeException("not support view this file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new BaseRuntimeException("not found file", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            boolean result = false;
            Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
            if (attachmentInfoOptional.isPresent()) {
                AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                if (attachmentService.delete(attachmentInfo.getPath())) {
                    result = attachmentInfoService.removeById(attachmentInfo.getId());
                }

            }
            return result;
        } catch (Exception e) {
            throw new BaseRuntimeException("delete file error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteBatch(String[] ids) {
        try {
            boolean result = false;
            List<String> fileIds = Arrays.asList(ids);
            List<AttachmentInfo> attachmentInfos = (List<AttachmentInfo>) attachmentInfoService.listByIds(fileIds);
            for (AttachmentInfo attachmentInfo : attachmentInfos) {
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                if (attachmentService.delete(attachmentInfo.getPath())) {
                    result = attachmentInfoService.removeById(attachmentInfo.getId());
                }
            }
            return result;
        } catch (Exception e) {
            throw new BaseRuntimeException("batch delete file error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
