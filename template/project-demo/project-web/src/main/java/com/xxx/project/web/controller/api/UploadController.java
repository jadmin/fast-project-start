/*
 * @(#)UploadController.java	2017年6月19日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.web.controller.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.cache.CacheManager;
import com.github.javaclub.sword.core.B;
import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.core.ValueWrap;
import com.github.javaclub.sword.util.Utils;
import com.github.javaclub.sword.web.HttpResult;
import #{packagePrefix}#.domain.enumtype.GlobalConfigKeys;
import #{packagePrefix}#.utils.HttpClientUtil;
import #{packagePrefix}#.utils.excel.ExcelUtils;
import #{packagePrefix}#.web.controller.BaseController;

/**
 * ImageUploadController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UploadController.java 2017年6月19日 14:38:37 Exp $
 */
@Controller
@RequestMapping("/api/upload")
public class UploadController extends BaseController {

	static final Logger log = LoggerFactory.getLogger(UploadController.class);
	
	@Value("#{APP_PROP['file.upload.url']}")
    private String url;
	
    @Resource
	private CacheManager<String, Object> kAMemberCacheManager;
	
	@RequestMapping("/imageUpload")
	@ResponseBody
	public HttpResult imageUpload(HttpServletRequest request) {

		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
		if (!multipartResolver.isMultipart(request)) {
			return HttpResult.failure("请上传图片");
		}
		// 转换成多部分request
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 取得request中的所有文件名
		Iterator<String> iter = multiRequest.getFileNames();
		if (iter.hasNext()) {
			// 取得上传文件
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null && file.getSize() != 0) {
				String result = null;
				try {
					result = HttpClientUtil.postWithPart(url, file.getInputStream(),
							file.getOriginalFilename(), false);
					log.warn("uploadPic\t uploadResult=" + result);
					JSONObject json = JSON.parseObject(result);
					if ("0".equals(json.getString("code"))) { // 上传成功
						return HttpResult.success(json.getString("key"));
					}
				} catch (Exception e) {
					log.error("上传图片失败 " + e.getMessage(), e);
				}
			}
		}

		return HttpResult.failure("图片上传失败");
	}
	
	@RequestMapping("/wxImageUpload")
	@ResponseBody
	public HttpResult wxImageUpload(HttpServletRequest request) {
		Long orgId = 0L;
		Long mockOrgId = orgId;
		
		ValueWrap value = getGlobalConfigValue(GlobalConfigKeys.WX_GZH_TEST_ORG_ID.getKey());
		if(null != value) {
			mockOrgId = value.longValue();
		}
		
		// String token = wXApiService.getOrgGzhAccessToken(mockOrgId.intValue()); 
		String token = ""; // TODO
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
		if (!multipartResolver.isMultipart(request)) {
			return HttpResult.failure("请上传图片");
		}
		// 转换成多部分request
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 取得request中的所有文件名
		Iterator<String> iter = multiRequest.getFileNames();
		if (iter.hasNext()) {
			// 取得上传文件
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null && file.getSize() != 0) {
				try {
					String wxresult = HttpClientUtil.wxUpload(token, file.getInputStream(), file.getOriginalFilename());
					log.warn("wxUpload\t uploadResult=" + wxresult);
					JSONObject wx = JSON.parseObject(wxresult);
					
					String sdgresult = HttpClientUtil.postWithPart(url, file.getInputStream(),
							file.getOriginalFilename(), false);
					log.warn("sdgUpload\t uploadResult=" + sdgresult);
					JSONObject sdg = JSON.parseObject(sdgresult);
					
					if(Strings.isNotBlank(wx.getString("errmsg"))) {
						return HttpResult.failure(wx.getString("errmsg"));
					}
					if (!"0".equals(sdg.getString("code"))) { // 上传失败
						return HttpResult.failure("图片上传至闪电购服务器失败");
					}
					
					String wxImageUrl = wx.getString("url");
					String sdgImagePath = sdg.getString("key");
					
					Map<String, String> map = Maps.generateMap("wxImageUrl", wxImageUrl, "sdgImagePath", sdgImagePath);
					
					return HttpResult.success(map);
					
				} catch (Exception e) {
					log.error("上传图片失败 " + e.getMessage(), e);
				}
			}
		}

		return HttpResult.failure("图片上传失败");
	}
	
	@RequestMapping("/getWxMaterial")
	@ResponseBody
	public HttpResult getWxMaterial(String mediaId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			B.requireNotEmpty(mediaId, "mediaId不能为空");
			String key = Strings.concat("wxmedis", "-", mediaId);
			map = (Map<String, String>) kAMemberCacheManager.get(key);
		} catch (BizException e) {
			return HttpResult.failure(e.getMessage());
		} catch (Exception e) {
			log.error("", e);
		}
		
		return HttpResult.success(map);
	}
	
	@RequestMapping("/wxMaterialUpload")
	@ResponseBody
	public HttpResult wxMaterialUpload(HttpServletRequest request) {
		// Integer orgId = SdgSessionUtil.getOrgId();
		
		// String token = wXApiService.getOrgGzhAccessToken(orgId); 
		String token = ""; // TODO
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
		if (!multipartResolver.isMultipart(request)) {
			return HttpResult.failure("请上微信图片素材");
		}
		// 转换成多部分request
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 取得request中的所有文件名
		Iterator<String> iter = multiRequest.getFileNames();
		if (iter.hasNext()) {
			// 取得上传文件
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null && file.getSize() != 0) {
				try {
					String wxresult = HttpClientUtil.wxMaterialUpload(token, file);
					log.warn("wxMaterialUpload\t uploadResult=" + wxresult);
					JSONObject wx = JSON.parseObject(wxresult);
					
					String sdgresult = HttpClientUtil.postWithPart(url, file.getInputStream(),
							file.getOriginalFilename(), false);
					log.warn("sdgUpload\t uploadResult=" + sdgresult);
					JSONObject sdg = JSON.parseObject(sdgresult);
					
					if(Strings.isNotBlank(wx.getString("errmsg"))) {
						return HttpResult.failure(wx.getString("errmsg"));
					}
					if (!"0".equals(sdg.getString("code"))) { // 上传失败
						return HttpResult.failure("图片上传至闪电购服务器失败");
					}
					
					String wxImageUrl = wx.getString("url");
					String wxMediaId = wx.getString("media_id");
					String sdgImagePath = sdg.getString("key");
					
					Map<String, String> map = Maps.generateMap("wxImageUrl", wxImageUrl, 
												"wxMediaId", wxMediaId,
												"sdgImagePath", sdgImagePath);
					
					String key = Strings.concat("wxmedis", "-", wxMediaId);
					kAMemberCacheManager.put(key, map);
					
					return HttpResult.success(map);
					
				} catch (Exception e) {
					log.error("上传图片失败 " + e.getMessage(), e);
				}
			}
		}

		return HttpResult.failure("图片上传失败");
	}
        
	@RequestMapping("/mobileUpload")
	@ResponseBody
	public HttpResult mobileUpload(HttpServletRequest request) {
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
			if (!multipartResolver.isMultipart(request)) {
				return HttpResult.failure("请上传手机号码Excel文件");
			}
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			if (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null && file.getSize() != 0) {
					String filename = file.getOriginalFilename();
					List<String> mobiles = null;
					if(filename.endsWith("xls")) {
						mobiles = ExcelUtils.readXls1rdCol(file.getInputStream());
					} else if(filename.endsWith("xlsx")) {
						mobiles = ExcelUtils.readXlsx1rdCol(file.getInputStream());
					}
					Set<String> removeDuplicate = new HashSet<>();
					if(null != mobiles) {
						removeDuplicate.addAll(mobiles);
					}
					Set<String> finalPhones = Sets.newHashSet();
					for (String phone : removeDuplicate) {
						if(Strings.isBlank(phone)) continue;
						phone = StringUtils.trim(phone);
						if(Utils.isPhoneLegal(phone)) {
							finalPhones.add(phone);
						}
					}
					return HttpResult.success(StringUtils.join(finalPhones, ","));
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}

		return HttpResult.failure("文件上传失败");
	}
	
	HttpResult checkImage(MultipartFile file) {
		try {
			InputStream is = file.getInputStream();
			byte[] temp = new byte[2048];
			int len = 0;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			while ((len = is.read(temp)) > 0) {
				stream.write(temp, 0, len);
			}
			String format = Utils.getTypeByStream(stream.toByteArray());
			if(!Objects.equals("jpg", format)) {
				return HttpResult.failure("请上传jpg格式图片");
			}
		} catch (IOException e) {
			log.error("", e);
			return HttpResult.failure("图片上传失败");
		}
		return null;
	}
        
    private String createUUID() {
        return UUID.randomUUID().toString();
    }

}
