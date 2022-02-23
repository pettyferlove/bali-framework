package com.github.bali.extension.message.email;

import lombok.SneakyThrows;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.junit.jupiter.api.Test;

public class EmailTests {

    @Test
    @SneakyThrows
    void contextLoads() {
        Email email = new SimpleEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("763934945@qq.com", "nmdwdqmichssbaij"));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setSubject("TestMail");
        email.setFrom("763934945@qq.com");
        email.setMsg("你好,This is a test mail ... :-)");
        email.addTo("pettyferlove@live.cn");
        email.send();
        System.out.println("Success!");
    }

}
