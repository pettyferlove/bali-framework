package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.persistence.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("uc_auth_client_details_scope")
@ApiModel(value="AuthClientDetailsScope对象", description="")
public class AuthClientDetailsScope extends BaseEntity<AuthClientDetailsScope> {

    private static final long serialVersionUID = 1L;

    private String detailsId;

    private String scopeId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
