package com.github.bali.core.framework.util;


import lombok.experimental.UtilityClass;

/**
 * 脱敏工具
 * @version 1.0.0
 * @author 廖晨曦
 */
@UtilityClass
public class MaskingUtils {

    /**
     * 公民身份号码脱敏
     *
     * @param idCard 需要脱敏的公民身份号码
     * @return 脱敏后的公民身份号码
     */
    public String idCard(String idCard) {
        if (idCard == null) {
            return "";
        } else if (idCard.length() == 18) {
            return data(idCard, 6, 3, true, "*********");
        } else if (idCard.length() == 15) {
            return data(idCard, 6, 2, true, "*******");
        }
        return data(idCard, 0.2, 0.2, true, "****");
    }

    /**
     * 公民身份号码脱敏（仅仅脱敏初始月日）
     *
     * @param idCard 需要脱敏的公民身份号码
     * @return 脱敏后的公民身份号码
     */
    public String idCardShortMasking(String idCard) {
        if (idCard == null) {
            return "";
        } else if (idCard.length() == 18) {
            return data(idCard, 10, 4, true, "****");
        } else if (idCard.length() == 15) {
            return data(idCard, 8, 3, true, "****");
        }
        return data(idCard, 0.2, 0.2, true, "****");
    }

    /**
     * 姓名脱敏
     *
     * @param name 需要脱敏的姓名
     * @return 脱敏后的姓名
     */
    public String name(String name) {
        if (name == null) {
            return "";
        }
        return data(name, 1, 0, true, "**");
    }

    /**
     * 手机号脱敏
     *
     * @param phoneNumber 需要脱敏的手机号
     * @return 脱敏后的手机号
     */
    public static String phoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        return data(phoneNumber, 3, 4, true, "****");
    }

    /**
     * 银行卡号脱敏
     *
     * @param bankCardNumber 需要脱敏的银行卡号
     * @return 脱敏后的银行卡号
     */
    public String bankCardNumber(String bankCardNumber) {
        if (bankCardNumber == null) {
            return "";
        }
        return data(bankCardNumber, 6, 4, true, "******");
    }

    /**
     * 根据脱敏位数进行数据脱敏
     *
     * @param data           需要脱敏的数据
     * @param prePlainCount  脱敏后保留的前缀明文位数
     * @param postPlainCount 脱敏后保留的后缀明文位数
     * @param isEmptyMasking 为空时是否需要脱敏
     * @param masking        脱敏字符串
     * @return 脱敏后的数据
     */
    public String data(String data, int prePlainCount, int postPlainCount, boolean isEmptyMasking, String masking) {
        if (StringUtils.isEmpty(data)) {
            return isEmptyMasking ? masking : "";
        }
        if (data.length() <= 1) {
            return masking;
        }
        int totalLength = data.length();
        if (totalLength > prePlainCount + postPlainCount) {
            return data.substring(0, prePlainCount) + masking + data.substring(totalLength - postPlainCount, totalLength);
        }
        return data(data, 0.2, 0.2, isEmptyMasking, masking);
    }

    /**
     * 根据脱敏率进行数据脱敏
     *
     * @param data             需要脱敏的数据
     * @param prePlainPercent  脱敏后保留的前缀明文率
     * @param postPlainPercent 脱敏后保留的后缀明文率
     * @param isEmptyMasking   为空时是否需要脱敏
     * @param masking          脱敏字符串
     * @return 脱敏后的数据
     */
    public String data(String data, double prePlainPercent, double postPlainPercent, boolean isEmptyMasking, String masking) {
        if (prePlainPercent + postPlainPercent >= 1.0) {
            throw new IllegalArgumentException("明文总百分比需要小于100%: " + prePlainPercent + " - " + postPlainPercent);
        }
        int totalLength = data.length();
        int prePlainCount = (int) (totalLength * prePlainPercent);
        int postPlainCount = (int) (totalLength * postPlainPercent);
        int overPlainCount = prePlainCount + postPlainCount - totalLength;
        if (overPlainCount > 0) {
            if (postPlainCount >= overPlainCount) {
                postPlainCount -= overPlainCount;
            } else {
                overPlainCount -= postPlainCount;
                if (prePlainCount >= overPlainCount) {
                    prePlainCount -= overPlainCount;
                } else {
                    prePlainCount = 0;
                }
                postPlainCount = 0;
            }
        }
        return data(data, prePlainCount, postPlainCount, isEmptyMasking, masking);
    }
}
