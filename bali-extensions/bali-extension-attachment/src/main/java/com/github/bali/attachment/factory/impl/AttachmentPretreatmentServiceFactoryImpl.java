package com.github.bali.attachment.factory.impl;

import com.github.bali.attachment.factory.IAttachmentPretreatmentServiceFactory;
import com.github.bali.attachment.service.IAttachmentPretreatmentService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pettyfer
 */
@Component
public class AttachmentPretreatmentServiceFactoryImpl implements IAttachmentPretreatmentServiceFactory {

    private Map<String, IAttachmentPretreatmentService> serviceMap;

    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.serviceMap = this.applicationContext.getBeansOfType(IAttachmentPretreatmentService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public List<IAttachmentPretreatmentService> all() {
        return new ArrayList<>(serviceMap.values());
    }
}
