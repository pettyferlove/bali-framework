package com.github.bali.persistence.provider.tenant;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.persistence.properties.MultiTenancyProperties;
import com.github.bali.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

import java.util.List;
import java.util.Objects;

/**
 * 多租户过滤控制
 * @author Petty
 */
@Slf4j
public class DefaultTenantHandler implements com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler {

    private final MultiTenancyProperties multiTenancyProperties;

    public DefaultTenantHandler(MultiTenancyProperties multiTenancyProperties) {
        this.multiTenancyProperties = multiTenancyProperties;
    }

    @Override
    public Expression getTenantId(boolean where) {
        // 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
        String tenantId = Objects.requireNonNull(SecurityUtil.getUser(), "multi_tenancy #50003 get user error.").getTenant();
        if (StrUtil.isEmpty(tenantId)) {
            throw new BaseRuntimeException("multi_tenancy #50001 get tenant_id error.");
        }
        return new StringValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return multiTenancyProperties.getTableField();
    }

    @Override
    public boolean doTableFilter(String tableName) {
        /*
                        匿名用户无法拿到租户ID，忽略处理
                         */
        if (ObjectUtil.isNull(SecurityUtil.getUser())) {
            if (log.isDebugEnabled()) {
                log.debug("multi_tenancy #50004 ignore tenant control");
            }
            return true;
        }
        /*
         * 判断需要忽略租户控制的角色
         */
        List<String> ignoreRoles = multiTenancyProperties.getIgnore().getRoles();
        List<String> roles = SecurityUtil.getRoles();
        if(ignoreRoles.stream().anyMatch(roles::contains)){
            return ignoreRoles.stream().anyMatch(roles::contains);
        }
        return  multiTenancyProperties.getIgnore().getTables().stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
    }
}
