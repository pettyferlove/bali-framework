package com.github.bali.security.access.expression;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author Pettyfer
 */
@SuppressWarnings("All")
public class BaliMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    private final String defaultScopePrefix = "SCOPE_";

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public BaliMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public final boolean hasScope(String scope) {
        return hasAnyScope(scope);
    }

    public final boolean hasAnyScope(String... scope) {
        return hasAnyAuthorityName(defaultScopePrefix, scope);
    }

    private boolean hasAnyAuthorityName(String prefix, String... scopes) {
        for (String scope : scopes) {
            String defaultedScope = getAuthorityWithDefaultPrefix(prefix, scope);
            if(this.hasAuthority(defaultedScope)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    private static String getAuthorityWithDefaultPrefix(String defaultScopePrefix, String scope) {
        if (scope == null) {
            return scope;
        }
        if (defaultScopePrefix == null || defaultScopePrefix.length() == 0) {
            return scope;
        }
        if (scope.startsWith(defaultScopePrefix)) {
            return scope;
        }
        return defaultScopePrefix + scope;
    }

}
