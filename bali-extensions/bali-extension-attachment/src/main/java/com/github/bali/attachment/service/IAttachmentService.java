package com.github.bali.attachment.service;

import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.domain.dto.AliyunOSSUploadResult;
import com.github.bali.attachment.domain.vo.Upload;

import java.io.File;
import java.io.OutputStream;

/**
 * 附件核心服务，对接具体的存储方式，比如本地文件操作 阿里云附件操作
 *
 * @author Petty
 */
public interface IAttachmentService {

    /**
     * 文件上传
     *
     * @param file     文件对象
     * @param upload   上传文件基本信息
     * @param fileType 文件类型
     * @return AliyunOSSUploadResult
     */
    AliyunOSSUploadResult upload(Upload upload, File file, FileType fileType);

    /**
     * 下载
     *
     * @param path         储存路径
     * @param outputStream 输出流
     */
    void download(String path, OutputStream outputStream);


    /**
     * 删除文件（同时删除数据记录）
     *
     * @param path 储存路径
     * @return Boolean
     */
    Boolean delete(String path);

}
