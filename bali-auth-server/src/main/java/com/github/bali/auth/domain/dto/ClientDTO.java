package com.github.bali.auth.domain.dto;

import com.github.bali.core.framework.domain.dto.IDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements IDTO {
    private static final long serialVersionUID = -9063295362323632850L;

    private String clientId;

    private String clientSecret;

}
