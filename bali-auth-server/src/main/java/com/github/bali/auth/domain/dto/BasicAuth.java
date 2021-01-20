package com.github.bali.auth.domain.dto;

import com.github.bali.core.framework.domain.dto.IDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pettyfer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicAuth implements IDTO {
    private static final long serialVersionUID = 3378361693921665811L;

    private String tenantId;

    private String clientId;

}
