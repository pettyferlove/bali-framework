package com.github.bali.attachment.factory.impl;

import com.github.bali.attachment.constants.StorageType;
import com.github.bali.attachment.factory.IAttachmentServiceFactory;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Petty
 */
@Component
public class AttachmentServiceFactoryImpl implements IAttachmentServiceFactory {

    private Map<String, IAttachmentService> serviceMap;

    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.serviceMap = this.applicationContext.getBeansOfType(IAttachmentService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public IAttachmentService create(StorageType type) {
        if (serviceMap.containsKey(type.getService())) {
            return serviceMap.get(type.getService());
        } else {
            throw new BaseRuntimeException("not supported " + type.getService() + " service, please contact the provider");
        }
    }
}
