package com.github.bali.attachment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.domain.dto.FileProcessResult;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.properties.AttachmentLocalProperties;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.attachment.utils.FileUtil;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @author Pettyfer
 */
@Slf4j
public class AttachmentLocalServiceImpl implements IAttachmentService {

    private final AttachmentLocalProperties properties;

    public AttachmentLocalServiceImpl(AttachmentLocalProperties properties) {
        this.properties = properties;
    }

    @Override
    public FileProcessResult upload(Upload upload, File file, FileType fileType) {
        FileProcessResult result = new FileProcessResult();
        InputStream inputStream = null;
        try {
            String fileId = IdUtil.simpleUUID();
            StringBuilder filePath = new StringBuilder();
            filePath.append(properties.getRoot());
            filePath.append(File.separator);
            filePath.append(upload.getGroup());
            inputStream = new FileInputStream(file);
            File path = new File(filePath.toString());
            if (path.isDirectory() && !path.exists()) {
                path.mkdir();
            }
            filePath.append(File.separator);
            filePath.append(fileId);
            filePath.append(fileType.getExpansionName());
            File localFile = new File(filePath.toString());
            if (path.isFile() && !path.exists()) {
                path.mkdir();
            }
            String md5 = FileUtil.md5(file);
            FileUtils.copyInputStreamToFile(inputStream, localFile);

            result.setMd5(md5);
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
    public void download(String path, OutputStream outputStream) {
        InputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            int ch;
            while ((ch = inputStream.read()) != -1) {
                outputStream.write(ch);
            }
        } catch (Exception e) {
            throw new BaseRuntimeException("file download error");
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
    public Boolean delete(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("file delete error");
        }
    }
}
