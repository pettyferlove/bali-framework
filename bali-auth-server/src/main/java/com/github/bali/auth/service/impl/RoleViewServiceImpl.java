package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.RoleView;
import com.github.bali.auth.mapper.RoleViewMapper;
import com.github.bali.auth.service.IRoleViewService;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-17
 */
@Service
public class RoleViewServiceImpl extends ServiceImpl<RoleViewMapper, RoleView> implements IRoleViewService {

    @Override
    public IPage<RoleView> page(String role, String roleName, Page<RoleView> page) {
        LambdaQueryWrapper<RoleView> queryWrapper = Wrappers.<RoleView>lambdaQuery();
        queryWrapper.eq(StrUtil.isNotEmpty(role), RoleView::getRole, role);
        queryWrapper.eq(StrUtil.isNotEmpty(roleName), RoleView::getRoleName, roleName);
        queryWrapper.eq(RoleView::getTenantId, SecurityUtil.getUser().getTenant());
        if(SecurityUtil.getRoles().contains(SecurityConstant.TENANT_ADMIN_ROLE)){
            queryWrapper.or().isNull(RoleView::getTenantId);
        }
        return this.page(page, queryWrapper);
    }

}
