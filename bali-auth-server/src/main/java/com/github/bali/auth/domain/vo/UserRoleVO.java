package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.auth.entity.Role;
import com.github.bali.core.framework.domain.vo.IVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserRoleVO implements IVO {

    private static final long serialVersionUID = -6810270250523251044L;

    private List<Role> toBeSelected;

    private List<Role> selected;


}
