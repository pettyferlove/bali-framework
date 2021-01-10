package com.github.bali.auth.utils;

import cn.hutool.core.codec.Base64;
import com.github.bali.auth.domain.dto.ClientDTO;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Petty
 */
@UtilityClass
public class BasicAuthorizationUtil {

    public ClientDTO authenticate(HttpHeaders headers) {
        List<String> authorization = Optional.ofNullable(headers.get("Authorization")).orElseGet(ArrayList::new);
        String basicAuthorization = "";
        if (!authorization.isEmpty()) {
            basicAuthorization = authorization.get(0);
        }
        String basic = basicAuthorization.replace("Basic ", "");
        String decode = Base64.decodeStr(basic);
        String[] split = decode.split(":");
        if (split.length == 2) {
            String clientId = split[0];
            String clientSecret = split[1];
            return new ClientDTO(clientId, clientSecret);
        } else {
            throw new BaseRuntimeException("client information error", HttpStatus.UNAUTHORIZED);
        }
    }

}
