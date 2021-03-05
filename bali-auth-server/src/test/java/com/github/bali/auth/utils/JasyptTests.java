package com.github.bali.auth.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

/**
 * Jasypt加密
 * @author Pettyfer
 */
public class JasyptTests {

    /**
     * Jasypt生成加密结果
     *
     * @param password 配置文件中设定的加密盐值
     * @param value    加密值
     * @return 加密结果
     */
    public String encryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return encryptor.encrypt(value);
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密盐值
     * @param value    解密密文
     * @return 加密结果
     */
    public String decryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return encryptor.decrypt(value);
    }

    public SimpleStringPBEConfig cryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }


    @Test
    public void contextLoads (){
        // 加密
        String encPwd = encryptPwd("bali-auth-server", "@adadYY&&*^daa");
        // 解密
        String decPwd = decryptPwd("bali-auth-server", encPwd);
        System.out.println(encPwd);
        System.out.println(decPwd);
    }
}
