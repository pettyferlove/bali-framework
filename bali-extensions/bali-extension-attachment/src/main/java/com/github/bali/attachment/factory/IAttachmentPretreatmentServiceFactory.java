package com.github.bali.attachment.factory;

import com.github.bali.attachment.service.IAttachmentPretreatmentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * 前置处理器工厂
 *
 * @author Pettyfer
 */
public interface IAttachmentPretreatmentServiceFactory extends ApplicationContextAware, InitializingBean {

    /**
     * 加载全部预处理器
     *
     * @return 预处理器集合
     */
    List<IAttachmentPretreatmentService> all();

}
