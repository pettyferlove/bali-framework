package com.github.bali.attachment.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.constants.SecurityType;
import com.github.bali.attachment.domain.dto.FileProcessResult;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.properties.AttachmentAliyunProperties;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import javax.validation.constraints.NotNull;
import java.io.*;

/**
 * @author Petty
 */
@Slf4j
@SuppressWarnings("ALL")
public class AttachmentAliyunServiceImpl implements IAttachmentService {


    private final AttachmentAliyunProperties aliyunProperties;

    private final OSS oss;

    private final String URL_STR = "https://%s.%s/%s";

    public AttachmentAliyunServiceImpl(AttachmentAliyunProperties aliyunProperties, OSS oss) {
        this.aliyunProperties = aliyunProperties;
        this.oss = oss;
    }

    @Override
    public FileProcessResult upload(Upload upload, File file, FileType fileType) {
        FileProcessResult result = new FileProcessResult();
        InputStream inputStream = null;
        try {
            String fileId = IdWorker.getIdStr();
            StringBuilder filePath = new StringBuilder();
            filePath.append(aliyunProperties.getRoot());
            filePath.append("/");
            filePath.append(upload.getGroup());
            filePath.append("/");
            filePath.append(fileId);
            filePath.append(fileType.getExpansionName());
            inputStream = new FileInputStream(file);
            ObjectMetadata meta = new ObjectMetadata();
            byte[] calculateMd5 = BinaryUtil.calculateMd5(FileUtils.readFileToByteArray(file));
            String md5 = BinaryUtil.toBase64String(calculateMd5);
            meta.setContentMD5(md5);
            @NotNull SecurityType security = upload.getSecurity();
            if (security == SecurityType.Private) {
                meta.setObjectAcl(CannedAccessControlList.Private);
            } else if (security == SecurityType.PublicRead) {
                result.setUrl(String.format(URL_STR, aliyunProperties.getBucket(), aliyunProperties.getEndpoint(), filePath.toString()));
                meta.setObjectAcl(CannedAccessControlList.PublicRead);
            }
            oss.putObject(aliyunProperties.getBucket(), filePath.toString(), inputStream, meta);
            result.setMd5(new String(Hex.encodeHex(calculateMd5)));
            result.setPath(filePath.toString());
            result.setFileId(fileId);
            result.setStoreType(upload.getStorage().getValue());
            return result;
        } catch (IOException e) {
            throw new BaseRuntimeException("file upload error");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void download(String path, String md5, OutputStream outputStream) {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            OSSObject ossObject = oss.getObject(aliyunProperties.getBucket(), path);
            String fileMd5 = new String(Hex.encodeHex(BinaryUtil.fromBase64String(ossObject.getObjectMetadata().getContentMD5())));
            if (!md5.equals(fileMd5)) {
                throw new BaseRuntimeException("file md5 does not match");
            }
            bufferedInputStream = new BufferedInputStream(ossObject.getObjectContent());
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            FileCopyUtils.copy(bufferedInputStream, bufferedOutputStream);
        } catch (Exception e) {
            if (e instanceof BaseRuntimeException) {
                throw new BaseRuntimeException(e.getMessage());
            } else {
                throw new BaseRuntimeException("file download error");
            }
        }
    }

    @Override
    public Boolean delete(String path) {
        try {
            oss.deleteObject(aliyunProperties.getBucket(), path);
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("file delete error");
        }
    }
}
