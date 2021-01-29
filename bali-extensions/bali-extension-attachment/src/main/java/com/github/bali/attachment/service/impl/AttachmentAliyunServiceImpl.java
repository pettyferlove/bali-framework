package com.github.bali.attachment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.constants.SecurityType;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.entity.AttachmentInfo;
import com.github.bali.attachment.properties.AttachmentAliyunProperties;
import com.github.bali.attachment.service.IAttachmentInfoService;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.attachment.utils.FileUtil;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Petty
 */
@Slf4j
@Service("aliyun")
public class AttachmentAliyunServiceImpl implements IAttachmentService {

    private final IAttachmentInfoService attachmentInfoService;

    private final AttachmentAliyunProperties aliyunProperties;

    private final OSS oss;

    private final String URL_STR = "https://%s.%s/%s";

    public AttachmentAliyunServiceImpl(IAttachmentInfoService attachmentInfoService, AttachmentAliyunProperties aliyunProperties, OSS oss) {
        this.attachmentInfoService = attachmentInfoService;
        this.aliyunProperties = aliyunProperties;
        this.oss = oss;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult upload(String userId, Upload upload, MultipartFile file) {
        Assert.notNull(file, "上传文件不可为空");
        UploadResult result = new UploadResult();
        String fileId = IdUtil.simpleUUID();
        FileType parse = FileType.parse(file.getContentType());
        StringBuilder filePath = new StringBuilder();
        filePath.append(aliyunProperties.getRoot());
        filePath.append("/");
        filePath.append(upload.getGroup());
        filePath.append("/");
        filePath.append(fileId);
        filePath.append(parse.getExpansionName());

        // 图片压缩
        String contextPath = "temp" + File.separator + fileId + parse.getExpansionName();

        File tempFile = null;
        File f = null;
        InputStream inputStream = null;
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
            inputStream = new FileInputStream(f);
            ObjectMetadata meta = new ObjectMetadata();
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(FileUtils.readFileToByteArray(f)));
            meta.setContentMD5(md5);
            if (attachmentInfoService.save(userId, fileId, file.getOriginalFilename(), md5, filePath.toString(), upload, file.getContentType(), file.getSize())) {
                result.setMd5(md5);
                result.setFileId(fileId);
                result.setStoreType(upload.getStorage().getValue());
                @NotNull SecurityType security = upload.getSecurity();
                if (security == SecurityType.Private) {
                    meta.setObjectAcl(CannedAccessControlList.Private);
                } else if (security == SecurityType.PublicRead) {
                    result.setUrl(String.format(URL_STR, aliyunProperties.getBucket(), aliyunProperties.getEndpoint(), filePath.toString()));
                    meta.setObjectAcl(CannedAccessControlList.PublicRead);
                }
                oss.putObject(aliyunProperties.getBucket(), filePath.toString(), inputStream, meta);
                result.setMd5(md5);
                result.setFileId(fileId);
                result.setStoreType(upload.getStorage().getValue());
            } else {
                throw new BaseRuntimeException("file upload info save error");
            }
        } catch (IOException e) {
            throw new BaseRuntimeException("file upload error");
        } finally {
            tempFile.delete();
            f.delete();
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void download(AttachmentInfo attachmentInfo, OutputStream outputStream) {
        try {
            OSSObject ossObject = oss.getObject(aliyunProperties.getBucket(), attachmentInfo.getPath());
            int ch;
            while ((ch = ossObject.getObjectContent().read()) != -1) {
                outputStream.write(ch);
            }
        } catch (Exception e) {
            throw new BaseRuntimeException("file download error");
        }
    }

    @Override
    public Boolean delete(AttachmentInfo attachmentInfo) {
        try {
            oss.deleteObject(aliyunProperties.getBucket(), attachmentInfo.getPath());
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("file delete error");
        }
    }
}
