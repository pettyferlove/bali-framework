package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.mapper.AuthClientDetailsMapper;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 终端信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-09
 */
@Service
public class AuthClientDetailsServiceImpl extends ServiceImpl<AuthClientDetailsMapper, AuthClientDetails> implements IAuthClientDetailsService {

    @Override
    public IPage<AuthClientDetails> page(AuthClientDetails authClientDetails, Page<AuthClientDetails> page) {
        LambdaQueryWrapper<AuthClientDetails> queryWrapper = Wrappers.<AuthClientDetails>lambdaQuery().orderByDesc(AuthClientDetails::getCreateTime);
        if (StrUtil.isNotEmpty(authClientDetails.getClientId())) {
            queryWrapper.likeRight(AuthClientDetails::getClientId, authClientDetails.getClientId());
        }
        return this.page(page, queryWrapper);
    }

    @Override
    public AuthClientDetails get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(AuthClientDetails authClientDetails) {
        authClientDetails.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientDetails.setCreateTime(LocalDateTime.now());
        if (this.save(authClientDetails)) {
            return authClientDetails.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(AuthClientDetails authClientDetails) {
        authClientDetails.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientDetails.setModifyTime(LocalDateTime.now());
        return this.updateById(authClientDetails);
    }

}
