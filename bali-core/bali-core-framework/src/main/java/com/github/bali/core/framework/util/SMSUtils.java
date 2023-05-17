package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Petty
 */
@UtilityClass
public class SMSUtils {

    private final int SMS_CODE_LENGTH = 6;

    private final Pattern P = Pattern.compile("^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");

    public String createRandomVcode() {
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < SMS_CODE_LENGTH; i++) {
            vcode.append(new Random().nextInt(10));
        }
        return vcode.toString();
    }

    /**
     * 校验手机号是否合法
     */
    public Boolean isMobile(String number) {
        boolean flag = false;
        try {
            Matcher m = P.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
