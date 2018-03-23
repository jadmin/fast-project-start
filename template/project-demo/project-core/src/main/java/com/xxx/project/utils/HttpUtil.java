package #{packagePrefix}#.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String sendPost(String url, String jsonData, Map<String, String> headerMap) {
        // 创建默认的httpClient实例
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = getPostRep(httpClient, url, jsonData, headerMap);
            return getPostResp(response);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static HttpResponse getPostRep(HttpClient httpClient, String url, String jsonData, Map<String, String> headerMap) {
        // 响应内容
        byte[] bs = null;

        // 创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        };

        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);

            SSLSocketFactory sf = new SSLSocketFactory(
                    ctx,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, sf);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            // 创建HttpPost
            HttpPost httpPost = new HttpPost(url);
            for (String header : headerMap.keySet()) {
                httpPost.setHeader(header, headerMap.get(header));
            }
            StringEntity s = new StringEntity(jsonData, "UTF-8");
            s.setContentType("text/html");
            //httpPost.setHeader("Content-Length", contentLength+"");
            httpPost.setEntity(s);

            // 执行POST请求
            HttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse sendPostReq(String url , Map<String, String> headerMap, List<NameValuePair> formParams) {
        // 创建默认的httpClient实例
        HttpClient httpClient = new DefaultHttpClient();

        // 创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        };

        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);

            SSLSocketFactory sf = new SSLSocketFactory(
                    ctx,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, sf);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            // 创建HttpPost
            HttpPost httpPost = new HttpPost(url);
            for (String header : headerMap.keySet()) {
                httpPost.setHeader(header, headerMap.get(header));
            }

            StringEntity s = new UrlEncodedFormEntity(formParams, "UTF-8");
            s.setContentType("application/x-www-form-urlencoded");
            //httpPost.setHeader("Content-Length", contentLength+"");
            httpPost.setEntity(s);


            // 执行POST请求
            HttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (Exception e) {
            return null;
        } finally {
            // 关闭连接,释放资源
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static Header[] getPostResponseHeader(HttpResponse response, String headerName) {
        if (response != null) {
            return response.getHeaders(headerName);
        }
        return null;
    }

    public static String getPostResp(HttpResponse response) {
        // 响应内容
        try {
            byte[] bs = null;
            // 执行POST请求
            if (response != null) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                bs = IOUtils.toByteArray(entity.getContent());
                if (null != entity) {
                    EntityUtils.consume(entity); // Consume response content
                }
                String out = new String(bs);
                return out;
            }
        } catch (Exception e) {
            logger.error("getPostResp error", e);
        }
        return null;
    }


    public static String getPostResp(String url , Map<String, String> headerMap, List<NameValuePair> formParams) {
        // 响应内容
        HttpResponse response = sendPostReq(url, headerMap, formParams);
        return getPostResp(response);
    }


    public static String sendGet(String url, String param){
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try{
            if(!StringUtils.isEmpty(param)){//如果参数不为空
                url = url + "?" + param;
            }
            URL realUrl = new URL(url);
            //打开连接
            URLConnection connection = realUrl.openConnection();
            //建立实际连接
            connection.connect();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch(Exception e){
            }
        }

        return result.toString();
    }

    private static String getRealUrl(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://waimai.baidu.com/waimai?qt=shoplist&lat=4678661.96&lng=13529493.33&address=%E5%A4%A7%E8%BF%9E%E7%90%86%E5%B7%A5%E5%A4%A7%E5%AD%A6&city_id=167");
        try {
            //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
            HttpResponse response = httpClient.execute(httpGet, httpContext);
            //获取重定向之后的主机地址信息,即"http://127.0.0.1:8088"
            HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            //获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
            HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            return targetHost + realRequest.getURI().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static String sendHttpsGet(String url, Map<String, String> headerMap,Map<String,String> paras) {
        if (paras != null && paras.size()  >0)
        {
            Iterator<Map.Entry<String, String>> iterator = paras.entrySet().iterator();
            Map.Entry<String, String> entry = null;
            StringBuffer sb = new StringBuffer();
            while (iterator.hasNext())
            {
                entry = iterator.next();
                sb.append(entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&");
            }
            url = url + "?" + sb.substring(0, sb.length() - 1);
        }

        return sendHttpsGet(url, headerMap);
    }

    public static String sendHttpsGet(String url, Map<String, String> headerMap) {
        // 响应内容
        byte[] bs = null;

        // 创建默认的httpClient实例
        HttpClient httpClient = new DefaultHttpClient();

        // 创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        };

        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);

            SSLSocketFactory sf = new SSLSocketFactory(
                    ctx,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, sf);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            // 创建HttpPost
            HttpGet httpGet = new HttpGet(url);
            for (String header : headerMap.keySet()) {
                httpGet.setHeader(header, headerMap.get(header));
            }

            // 执行POST请求
            HttpResponse response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            bs = IOUtils.toByteArray(entity.getContent());
            if (null != entity) {
                EntityUtils.consume(entity); // Consume response content
            }
            String out = new String(bs);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static void main(String args[]) {

        String accessToken = "7pUouFj5f_Zx-TYDaKszZJE9hEVRypMfN96lR6SLpxPQ4nDx1Ccp1XAfik2Y7svRaYgndHMl91vYegGIywq0Ij4LsplujR1ybHtu1E-HmVeOroZWkSCVxISA74Ghay_5LPHcAJAGKC";
        String loginV2 = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + accessToken;
        String json = "{\n" +
                "   \"touser\":[\n" +
                "    \"o5TAy02dvxsbYICZE6_Fq0d5WHSM\"\n" +
                "   ],\n" +
                "        \"wxcard\": {\"card_id\":\"pVgoZszFRduCCnIQydSa4ooQNSfU\"}\n" +
                "        \"msgtype\":\"wxcard\"\n" +
                "}";
        String resp1 = HttpUtil.sendPost(loginV2, json, new HashMap<String, String>());
        System.out.println(resp1);
    }


}