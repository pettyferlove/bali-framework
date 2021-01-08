package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.ClientDetails;
import com.github.bali.auth.mapper.ClientDetailsMapper;
import com.github.bali.auth.service.IClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 终端信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Service
public class ClientDetailsServiceImpl extends ServiceImpl<ClientDetailsMapper, ClientDetails> implements IClientDetailsService {

    @Override
    public IPage<ClientDetails> page(ClientDetails clientDetails, Page<ClientDetails> page) {
        return this.page(page, Wrappers.lambdaQuery(clientDetails).orderByDesc(ClientDetails::getCreateTime));
    }

    @Override
    public ClientDetails get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(ClientDetails clientDetails) {
        clientDetails.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        clientDetails.setCreateTime(LocalDateTime.now());
        if (this.save(clientDetails)) {
            return clientDetails.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(ClientDetails clientDetails) {
        clientDetails.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        clientDetails.setModifyTime(LocalDateTime.now());
        return this.updateById(clientDetails);
    }

}
