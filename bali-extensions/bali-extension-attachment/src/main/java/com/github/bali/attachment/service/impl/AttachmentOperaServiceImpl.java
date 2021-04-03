package com.github.bali.attachment.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.constants.StorageType;
import com.github.bali.attachment.domain.dto.FileProcessResult;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.entity.AttachmentInfo;
import com.github.bali.attachment.factory.IAttachmentPretreatmentServiceFactory;
import com.github.bali.attachment.factory.IAttachmentServiceFactory;
import com.github.bali.attachment.service.IAttachmentInfoService;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.attachment.service.IAttachmentPretreatmentService;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.core.framework.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    private final IAttachmentPretreatmentServiceFactory pretreatmentServiceFactory;

    public AttachmentOperaServiceImpl(IAttachmentServiceFactory factory, IAttachmentInfoService attachmentInfoService, IAttachmentPretreatmentServiceFactory pretreatmentServiceFactory) {
        this.factory = factory;
        this.attachmentInfoService = attachmentInfoService;
        this.pretreatmentServiceFactory = pretreatmentServiceFactory;
    }

    @Override
    public UploadResult upload(String userId, Upload upload, MultipartFile file) {
        IAttachmentService attachmentService = factory.create(upload.getStorage());
        FileProcessResult uploadResult = null;
        String tempId = UUID.fastUUID().toString();
        String fileName = file.getOriginalFilename();
        FileType fileType = FileType.parse(file.getContentType());
        String tempPath = "temp" + File.separator + tempId + fileType.getExpansionName();
        File tempFile = null;
        long length;
        try {
            tempFile = new File(tempPath);
            if (!tempFile.exists()) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            }
            List<IAttachmentPretreatmentService> pretreatmentServices = pretreatmentServiceFactory.all();
            for (IAttachmentPretreatmentService pretreatmentService : pretreatmentServices) {
                tempFile = pretreatmentService.process(upload, tempFile, fileType);
            }
            length = tempFile.length();
            uploadResult = attachmentService.upload(upload, tempFile, fileType);
        } catch (IOException e) {
            throw new BaseRuntimeException("file pretreatment process error");
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
        attachmentInfoService.save(userId, uploadResult.getFileId(), fileName, uploadResult.getMd5(), uploadResult.getPath(), upload, fileType.getContentType(), length);
        UploadResult result = Optional.ofNullable(ConverterUtil.convert(uploadResult, new UploadResult())).orElseGet(UploadResult::new);
        result.setAdditionalData(new JSONObject().toJSONString());
        result.setFileName(fileName);
        return result;
    }

    @Override
    public void download(String id, HttpServletResponse response) throws Exception {
        Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
        if (attachmentInfoOptional.isPresent()) {
            AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
            IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
            attachmentService.download(attachmentInfo.getPath(), attachmentInfo.getMd5(), response.getOutputStream());
            response.setCharacterEncoding("utf-8");
            response.setContentType(attachmentInfo.getFileType());
            String fileName = null;
            try {
                fileName = URLEncoder.encode(attachmentInfo.getFileName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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
            if (FileType.parse(fileType).isImage()) {
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                response.setCharacterEncoding("utf-8");
                response.setContentType(attachmentInfo.getFileType());
                attachmentService.download(attachmentInfo.getPath(), attachmentInfo.getMd5(), response.getOutputStream());
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
