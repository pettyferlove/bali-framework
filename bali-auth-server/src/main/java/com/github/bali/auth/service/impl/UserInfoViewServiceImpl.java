package com.github.bali.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.domain.vo.UserInfoQueryParams;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.entity.UserRole;
import com.github.bali.auth.mapper.UserInfoViewMapper;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.auth.service.IUserInfoViewService;
import com.github.bali.auth.service.IUserRoleService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-13
 */
@Service
public class UserInfoViewServiceImpl extends ServiceImpl<UserInfoViewMapper, UserInfoView> implements IUserInfoViewService {

    private final IRoleService roleService;

    private final IUserRoleService userRoleService;

    public UserInfoViewServiceImpl(IRoleService roleService, IUserRoleService userRoleService) {
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Override
    public IPage<UserInfoView> page(UserInfoQueryParams params, Page<UserInfoView> page) {
        LambdaQueryWrapper<UserInfoView> queryWrapper = Wrappers.<UserInfoView>lambdaQuery();
        if (params.getChannel() == null) {
            throw new BaseRuntimeException("请指定用户渠道");
        }
        queryWrapper.eq(UserInfoView::getUserChannel, params.getChannel().getValue());
        queryWrapper.likeRight(StrUtil.isNotEmpty(params.getUserName()), UserInfoView::getUserName, params.getUserName());
        queryWrapper.likeRight(StrUtil.isNotEmpty(params.getNickName()), UserInfoView::getNickName, params.getNickName());
        queryWrapper.likeRight(StrUtil.isNotEmpty(params.getUserIden()), UserInfoView::getUserIden, params.getUserIden());
        return this.page(page, queryWrapper);
    }

    @Override
    public IPage<UserInfoView> pageByRole(UserInfoQueryParams params, Page<UserInfoView> page, String role, String excludeUserIds) {
        Role r = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRole, role));
        if (ObjectUtil.isNotNull(r)) {
            List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, r.getId()));
            List<String> userIdList = userRoles.stream().filter(i->{
                if(StrUtil.isEmpty(excludeUserIds)){
                    return true;
                } else {
                   return !excludeUserIds.contains(i.getUserId());
                }
            }).map(UserRole::getUserId).collect(Collectors.toList());
            if (!userIdList.isEmpty()) {
                LambdaQueryWrapper<UserInfoView> queryWrapper = Wrappers.<UserInfoView>lambdaQuery();
                if (params.getChannel() == null) {
                    throw new BaseRuntimeException("请指定用户渠道");
                }
                queryWrapper.in(UserInfoView::getId, userIdList);
                queryWrapper.eq(UserInfoView::getUserChannel, params.getChannel().getValue());
                queryWrapper.likeRight(StrUtil.isNotEmpty(params.getUserName()), UserInfoView::getUserName, params.getUserName());
                queryWrapper.likeRight(StrUtil.isNotEmpty(params.getNickName()), UserInfoView::getNickName, params.getNickName());
                queryWrapper.likeRight(StrUtil.isNotEmpty(params.getUserIden()), UserInfoView::getUserIden, params.getUserIden());
                return this.page(page, queryWrapper);
            } else {
                return new Page<>();
            }
        } else {
            return new Page<>();
        }

    }
}
