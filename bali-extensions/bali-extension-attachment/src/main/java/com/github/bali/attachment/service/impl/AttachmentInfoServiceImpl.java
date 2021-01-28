package com.github.bali.attachment.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.entity.AttachmentInfo;
import com.github.bali.attachment.mapper.AttachmentInfoMapper;
import com.github.bali.attachment.service.IAttachmentInfoService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-21
 */
@Service
public class AttachmentInfoServiceImpl extends ServiceImpl<AttachmentInfoMapper, AttachmentInfo> implements IAttachmentInfoService {

    @Override
    public IPage<AttachmentInfo> page(AttachmentInfo attachmentInfo, Page<AttachmentInfo> page) {
        return this.page(page, Wrappers.lambdaQuery(attachmentInfo).orderByDesc(AttachmentInfo::getCreateTime));
    }

    @Override
    public AttachmentInfo get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(AttachmentInfo attachmentInfo) {
        attachmentInfo.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        attachmentInfo.setCreateTime(LocalDateTime.now());
        if (this.save(attachmentInfo)) {
            return attachmentInfo.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(AttachmentInfo attachmentInfo) {
        attachmentInfo.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        attachmentInfo.setModifyTime(LocalDateTime.now());
        return this.updateById(attachmentInfo);
    }


    @Override
    public Boolean save(String userId, String fileId, String fileName, String md5, String filePath, Upload upload, String contentType, Long fileSize) {
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setFileId(fileId);
        attachmentInfo.setMd5(md5);
        attachmentInfo.setCreator(userId);
        attachmentInfo.setCreateTime(LocalDateTime.now());
        attachmentInfo.setFileName(fileName);
        attachmentInfo.setFileType(contentType);
        attachmentInfo.setSize(fileSize);
        attachmentInfo.setStorageType(upload.getStorage().getValue());
        attachmentInfo.setPath(filePath);
        return this.save(attachmentInfo);
    }

    @Override
    public AttachmentInfo getAttachmentInfoById(String id) {
        return this.getOne(Wrappers.<AttachmentInfo>lambdaQuery().eq(AttachmentInfo::getFileId, id));
    }

}
