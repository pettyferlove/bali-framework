package com.github.bali.attachment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.bali.attachment.constants.FileType;
import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.service.IAttachmentPretreatmentService;
import com.github.bali.attachment.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author Pettyfer
 */
@Slf4j
@Service
public class CompressServiceImpl implements IAttachmentPretreatmentService {

    @Override
    public File process(Upload upload, File file, FileType fileType) {
        FileInputStream stream = null;
        FileChannel channel = null;
        boolean compress = true;
        try {
            JSONObject params = JSON.parseObject(upload.getAdditionalParams());
            compress = (boolean) params.get("compress");
        } catch (Exception e) {
            compress = false;
        }
        try {
            if (fileType.isImage() && compress) {
                stream = new FileInputStream(file);
                channel = stream.getChannel();
                BufferedImage image = ImageIO.read(stream);
                float quality = 0.7f;
                float scale = 0.7f;
                if (!FileUtil.checkFileSize(channel.size(), 30, "K")) {
                    if (image.getWidth() > 1980) {
                        quality = 0.4f;
                        scale = 0.5f;
                    }
                }
                Thumbnails.of(file).imageType(BufferedImage.TYPE_INT_RGB).outputQuality(quality).scale(scale).toFile(file);
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
