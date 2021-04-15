package com.github.bali.attachment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.entity.AttachmentInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-21
 */
public interface IAttachmentInfoService extends IService<AttachmentInfo> {

    /**
     * List查找
     *
     * @param attachmentInfo 查询参数对象
     * @param page           Page分页对象
     * @return IPage 返回结果
     */
    IPage<AttachmentInfo> page(AttachmentInfo attachmentInfo, Page<AttachmentInfo> page);

    /**
     * 通过Id查询AttachmentInfo信息
     *
     * @param id 业务主键
     * @return 对象
     */
    AttachmentInfo get(String id);

    /**
     * 通过Id删除信息
     *
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 创建数据
     *
     * @param attachmentInfo 要创建的对象
     * @return Boolean
     */
    String create(AttachmentInfo attachmentInfo);

    /**
     * 更新数据（必须带Id）
     *
     * @param attachmentInfo 对象
     * @return Boolean
     */
    Boolean update(AttachmentInfo attachmentInfo);


    /**
     * 保存文件上传记录
     *
     * @param userId      UserId
     * @param fileId      文件ID，如果为Null则自动生成
     * @param fileName    FileName
     * @param md5         MD5
     * @param filePath    文件相对地址
     * @param upload      上传对象
     * @param contentType 类型
     * @param fileSize    大小
     * @return Boolean
     */
    Boolean save(String userId, String fileId, String fileName, String md5, String filePath, Upload upload, String contentType, Long fileSize);


    /**
     * 通过附件ID获取附件内容
     *
     * @param id 附件ID
     * @return AttachmentInfo
     */
    AttachmentInfo getAttachmentInfoById(String id);

}
