package com.github.bali.attachment.service;

import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.domain.vo.Upload;

import java.io.File;

/**
 * @author Pettyfer
 */
public interface IAttachmentPretreatmentService {

    /**
     * 文件处理
     *
     * @param upload 文件上传参数
     * @param file 文件对象
     * @param fileType 文件类型
     * @return 处理后的文件
     */
    File process(Upload upload, File file, FileType fileType);

}
