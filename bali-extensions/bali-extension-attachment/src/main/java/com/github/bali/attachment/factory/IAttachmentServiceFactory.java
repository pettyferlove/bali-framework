package com.github.bali.attachment.factory;

import com.github.bali.attachment.constants.StorageType;
import com.github.bali.attachment.service.IAttachmentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

/**
 * 附件提供者工厂
 * @author Petty
 */
public interface IAttachmentServiceFactory extends ApplicationContextAware, InitializingBean {

    /**
     * 选择储存类型
     * @param type 枚举
     * @return 上传服务或下载服务
     */
    IAttachmentService create(StorageType type);

}
