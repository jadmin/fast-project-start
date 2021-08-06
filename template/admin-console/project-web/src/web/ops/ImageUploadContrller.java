/*
 * @(#)ImageUploadContrller.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.ops;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.util.UuidUtil;

/**
 * ImageUploadContrller
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ImageUploadContrller.java ${currentTime} Exp $
 */
@RequestMapping("/ops")
@RestController
public class ImageUploadContrller {
	
	static final Logger log = LoggerFactory.getLogger(ImageUploadContrller.class);

	static List<String> ALLOWED_IMG_EXT = Arrays.asList("bmp","jpg","jpeg","png","tif","gif","svg","psd","webp");

    @PostMapping(value = "/imageUpload")
    public Map<String, Object> imageUpload(HttpServletRequest request, 
    		@RequestParam("file") MultipartFile file,
    		@RequestHeader HttpHeaders headers) {
    	Map<String, Object> map = new HashMap<String, Object>();
        try {
            String originalFilename = file.getOriginalFilename();
            String directory = "uploads/";
            String suffix = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();
            String finalName = directory + UuidUtil.randomUUID() + suffix;
            if (!ALLOWED_IMG_EXT.contains(suffix.replace(".", ""))) {
            	return Maps.createMap("status", false,
            			"message", "图片格式不支持，请重新上传");
            }
            long fileSize = file.getSize();
            if(fileSize > 5*1024*1024L) { // 文件大小不要超过5M
            	return Maps.createMap("status", false,
            			"message", "文件大小不要超过5Mb，请重新上传");
            }
        	// TODO 上传图片得到
        	String url = "https://static.xinc818.com/aibox/images04b102e5aded4b879f6cf2eb96dae739/kgom8n2qnction.png"; 
            map.put("url", url);
            map.put("total", file.getSize());
            map.put("file", finalName);
            map.put("status", true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }
}
