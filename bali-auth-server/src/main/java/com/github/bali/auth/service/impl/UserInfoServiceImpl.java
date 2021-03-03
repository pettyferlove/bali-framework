package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.domain.vo.PersonalDetails;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.mapper.UserInfoMapper;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.core.framework.utils.ConverterUtil;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public IPage<UserInfo> page(UserInfo userInfo, Page<UserInfo> page) {
        return this.page(page, Wrappers.lambdaQuery(userInfo).orderByDesc(UserInfo::getCreateTime));
    }

    @Override
    public UserInfo get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(UserInfo userInfo) {
        userInfo.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        userInfo.setCreateTime(LocalDateTime.now());
        if (this.save(userInfo)) {
            return userInfo.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(UserInfo userInfo) {
        userInfo.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        userInfo.setModifyTime(LocalDateTime.now());
        return this.updateById(userInfo);
    }

    @Override
    public PersonalDetails getDetails() {
        UserInfo userInfo = Optional.ofNullable(this.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, SecurityUtil.getUser().getId()))).orElseGet(UserInfo::new);
        return Optional.ofNullable(ConverterUtil.convert(userInfo, new PersonalDetails())).orElseGet(PersonalDetails::new);
    }

    @Override
    public Boolean updateDetails(PersonalDetails details) {
        UserInfo userInfo = Optional.ofNullable(ConverterUtil.convert(details, new UserInfo())).orElseGet(UserInfo::new);
        LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.<UserInfo>lambdaUpdate();
        updateWrapper.eq(UserInfo::getUserId, SecurityUtil.getUser().getId());
        return this.update(userInfo, updateWrapper);
    }

}
