package #{packagePrefix}#.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.FileUtil;

public abstract class HttpClientUtil {

    static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    static final String BOUNDARY = "----------ThIs_Is_tHe_bouNdaRY_$";
    static final String MULTIPART = "multipart/form-data; " + BOUNDARY;
    
    public static <T> T post(String url, String requestboby, String contentType, Class<T> cls) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(url);

        StringEntity entity = null;

        T result = null;

        CloseableHttpResponse response = null;
        try {
            entity = new StringEntity(requestboby);
            entity.setContentType(contentType);

            httppost.setEntity(entity);

            response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    String reponseBody = EntityUtils.toString(httpEntity, "UTF-8");

                    result = JSON.parseObject(reponseBody, cls);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 成功之后返回文件访问路径
     * 
     * @author shisanyang
     * @param url
     * @param is
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String postWithPart(String url, InputStream is, String fileName, boolean parseJpg) throws Exception {
        if (StringUtils.isEmpty(fileName) || fileName.indexOf(".") < 0) {
            throw new Exception("fileName不能为空,且需要包含文件后缀名.");
        }
        if (parseJpg) {
            // 后缀名统一改成jpg
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".jpg";
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setBoundary(BOUNDARY);
        try {
            builder.addPart("file", new InputStreamBody(is, ContentType.create(MULTIPART), fileName));

            builder.addPart("thumbnails", new StringBody("600*600,320*320,180*180", ContentType.MULTIPART_FORM_DATA));
            // builder.addPart("file", new FileBody(new File("")));
            HttpEntity reqEntity = builder.build();

            post.setEntity(reqEntity);
            CloseableHttpResponse httpResponse = httpclient.execute(post);
            HttpEntity resEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(resEntity);
            LOGGER.debug(result);
            return result;
        } catch (Exception e) {
            LOGGER.error("上传文件至服务器失败:{}", fileName, e);
        } finally {
            if (is != null) {
                is.close();
            }
            post.releaseConnection();
            httpclient.close();
            LOGGER.info("upload to server time : " + (System.currentTimeMillis() - start));
        }
        return null;
    }
    
    /**
     *  上传文件到指定path
     * @param url
     * @param path
     * @param is
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String uploadFile(String url, String path, InputStream is, String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName) || fileName.indexOf(".") < 0) {
            throw new Exception("fileName不能为空,且需要包含文件后缀名.");
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setBoundary(BOUNDARY);
        try {
            builder.addPart("file", new InputStreamBody(is, ContentType.create(MULTIPART), fileName));
            builder.addPart("path", new StringBody(path, ContentType.MULTIPART_FORM_DATA));
            HttpEntity reqEntity = builder.build();
            post.setEntity(reqEntity);
            CloseableHttpResponse httpResponse = httpclient.execute(post);
            HttpEntity resEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(resEntity);

            LOGGER.debug(result);
            if(StringUtils.isNotBlank(result)){
                return JSONObject.parseObject(result).getString("desc");
            }
        } catch (Exception e) {
            LOGGER.error("上传文件至服务器失败:{}", fileName, e);
        } finally {
            if (is != null) {
                is.close();
            }
            post.releaseConnection();
            httpclient.close();
            LOGGER.info("upload to server time : " + (System.currentTimeMillis() - start));
        }
        return null;
    }

    /**
     * 上传文件
     * 
     * @param paths
     * @param url
     * @return
     */
    public static Map<String, String> uploadFile(List<String> paths, String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);

            for (String path : paths) {
                FileBody bin = new FileBody(new File(path));
                StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

                HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();

                httppost.setEntity(reqEntity);

                CloseableHttpResponse response = httpclient.execute(httppost);
                HttpEntity resEntity = null;
                try {
                    if (response.getStatusLine().getStatusCode() == 200) ;
                    {
                        resEntity = response.getEntity();
                        if (resEntity != null) {
                            String reponseBody = EntityUtils.toString(resEntity, "UTF-8");
                            System.out.println(reponseBody);
                        }
                    }
                    EntityUtils.consume(resEntity);

                } finally {
                    response.close();
                }
            }

        } finally {
            httpclient.close();
        }
        return null;

    }
    
    public static String wxUpload(String token, InputStream is, String filename) {

        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        FilePart media = null;
        HttpClient httpClient = new HttpClient();
        //信任任何类型的证书
        Protocol myhttps = new Protocol("https", new CustomSSLProtocolSocketFactory(), 443); 
        Protocol.registerProtocol("https", myhttps);

        String echo = "";
        try {
        	byte[] bytes = FileUtil.readAsByteArray(is);
        	ByteArrayPartSource baps = new ByteArrayPartSource(filename, bytes);
            media = new FilePart("buffer", baps);
            Part[] parts = new Part[] { new StringPart("access_token", token), media };
            MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
            post.setRequestEntity(entity);
            int status = httpClient.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
            	echo = post.getResponseBodyAsString();
            	echo = Strings.replaceIgnoreCase(echo, "\\", "");
            } 
        } catch (Exception e) {
        	LOGGER.error("", e);
        }
        
        return echo;
    }
    
    public static String wxMaterialUpload(String token, MultipartFile file) throws Exception {
		return uploadWxPermanentMedia(token, file.getSize(), file.getOriginalFilename(), file.getInputStream(), "", "");
	}
    
    public static String uploadWxPermanentMedia(String accessToken, 
    		long filelength, String fileName, InputStream fileInputStream, 
			String title, String introduction) {
    	
    	BufferedReader reader = null;
		try {
			//这块是用来处理如果上传的类型是video的类型的
			JSONObject j = new JSONObject();
			j.put("title", title);
			j.put("introduction", introduction);
			
			// 拼装请求地址
			String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken;

			URL url = new URL(uploadMediaUrl);
			String type = "image/jpeg";
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			
			// 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			
			StringBuilder sb = new StringBuilder();
			
			//这块是post提交type的值也就是文件对应的mime类型值
			sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //这里是参数名，参数名和值之间要用两次
			sb.append(type+"\r\n"); //参数的值
			
			//这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
			sb.append(j.toString()+"\r\n");
			
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			//这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ fileName + "\";filelength=\"" + filelength + "\" \r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			LOGGER.warn(sb.toString());
			
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(fileInputStream);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
			String echo = Strings.replaceIgnoreCase(buffer.toString(), "\\", "");
			
			return echo;
			
		} catch (IOException e) {
			LOGGER.error("", e);
		} finally {
			if(null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		
		return null;
	}
    
    public static void main(String[] args) throws Throwable {
		String image = "/Users/chen/i/temp/pics/ok.jpg";
		File file = new File(image);
		InputStream is = new FileInputStream(new File(image));
		String token = "7_qdgkr58TVdv60vdF9jKOVplbY7d1UVsSb-qKhWqtYA_NbcOWXmF4SVODTCu8K1XP-LrEQ5dUdAKYvFrYFwhMZ0iZLwllLvL_ztSSzqCvHLatof7ZTf7-M6K_dHAGHWdADAABQ";
		String echo = HttpClientUtil.uploadWxPermanentMedia(token, file.length(), "ok.jpg", is, "", "");
		System.out.println(echo);
	}

}

class CustomSSLProtocolSocketFactory implements ProtocolSocketFactory {

    private SSLContext sslcontext = null;

    private SSLContext createSSLContext() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null,
                    new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslcontext;
    }

    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }

    public Socket createSocket(Socket socket, String host, int port,
            boolean autoClose) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host,
                port, autoClose);
    }

    public Socket createSocket(String host, int port) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(String host, int port, InetAddress clientHost,
            int clientPort) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port,
                clientHost, clientPort);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress,
            int localPort, HttpConnectionParams params) throws IOException,
            UnknownHostException, ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress,
                    localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress,
                    localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    // 自定义私有类
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

}
