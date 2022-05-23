package com.github.bali.auth.endpoint;

import com.github.bali.auth.provider.service.ICaptchaValidateService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Pettyfer
 */
@Slf4j
@FrameworkEndpoint
@AllArgsConstructor
@RequestMapping("/captcha")
public class CaptchaEndpoint {

    private final DefaultKaptcha kaptcha;

    private final ICaptchaValidateService captchaValidateService;

    @GetMapping("/render/{machineCode}")
    public void createKaptcha(@PathVariable String machineCode, HttpServletResponse response) throws IOException {
        String randomStr = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(randomStr);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        captchaValidateService.create(machineCode, randomStr, 60);
        out.flush();
        out.close();
    }

    @GetMapping("/validate/{code}/{captcha}")
    public boolean validateKaptcha(@PathVariable String code, @PathVariable String captcha) {
        return captchaValidateService.validate(code, captcha);
    }

}
