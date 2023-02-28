package com.wp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wp.dao.HistoryDao;
import com.wp.dao.UserDao;
import com.wp.domain.History;
import com.wp.domain.User;
import com.wp.service.IUserService;
import com.wp.service.impl.HistoryServiceImpl;
import com.wp.util.Code;
import com.wp.util.MyX509TrustManager;
import com.wp.util.Result;
import com.wp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@Slf4j
class WesttwoMusicApplicationTests {

    @Autowired
    IUserService userService;
    @Autowired
    UserDao userDao;

    @Autowired
    HistoryDao historyDao;
    @Autowired
    HistoryServiceImpl historyService;

    /**
     * @Description: 测试https地址下载文件
     * @param: "[urlStr, fileName, savePath]"
     * @Return: void
     * @Author: supenghui
     * @Date: 2021/4/25 16:21
     */
    public void downLoadFromUrlHttps(String urlStr, String fileName,
                                     String savePath) {
        try {
            // 创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new MyX509TrustManager()};
            // 初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            // url对象
            URL url = new URL(urlStr);
            // 打开连接
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            /*
             * 这一步的原因: 当访问HTTPS的网址。您可能已经安装了服务器证书到您的JRE的keystore
             * 但是服务器的名称与证书实际域名不相等。这通常发生在你使用的是非标准网上签发的证书。
             *
             * 解决方法：让JRE相信所有的证书和对系统的域名和证书域名。
             *
             * 如果少了这一步会报错:java.io.IOException: HTTPS hostname wrong: should be <localhost>
             */
            conn.setHostnameVerifier(new MyX509TrustManager().new TrustAnyHostnameVerifier());
            // 设置一些参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();

            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            byte[] getData = readInputStream(inputStream);
            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            //输出流
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("下载成功");
    }

    /**
     * @Description: 测试http地址下载文件
     * @param: "[urlStr, fileName, savePath]"
     * @Return: void
     * @Author: supenghui
     * @Date: 2021/4/25 16:21
     */
    private void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        log.info("下载文件路径url：{}", urlStr);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为10秒
        conn.setConnectTimeout(10 * 1000);
        //防止屏蔽程序抓取而返回403错误
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        conn.setRequestProperty("lfwywxqyh_token",toekn);

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        log.info("---------- 文件地址：" + url + " 下载成功");
    }


    /**
     * @Description: 从输入流中获取字节数组
     * @param: "[inputStream]"
     * @Return: byte[]
     * @Author: supenghui
     * @Date: 2021/4/25 16:21
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    @Test
    public void testHttpsToFile() {
        String url = "https://link.hhtjim.com/kw/507943.mp3";
        String fileName = "507943.mp3";
        String savePath = "F:/test";
        downLoadFromUrlHttps(url, fileName, savePath);
    }
}
