package com.zuoxiaolong.config;/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.zuoxiaolong.util.IOUtil;
import net.sf.json.JSONObject;

import javax.crypto.Cipher;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 左潇龙
 * @since 5/22/2015 3:08 PM
 */
public class TestLogin {

    public static String loginUrl = "http://passport.cnblogs.com/user/signin";

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp0wHYbg/NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0/nhzCzBK8ZC9eCWqd0aHbdgOQT6CuFQBMjbyGYvlVYU2ZP7kG9Ft6YV6oc9ambuO7nPZh+bvXH0zDKfi02prknrScAKC0XhadTHT3Al0QIDAQAB";

    /**
     * 加密的方法,使用公钥进行加密
     * @throws Exception
     */
    public static String publicEnrypy(String data) throws Exception {

        // 得到公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        // 对数据进行加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    public static void main (String[] args ) throws Exception {
        HttpURLConnection loginPageConnection = (HttpURLConnection) new URL(loginUrl + "?ReturnUrl=http%3A%2F%2Fhome.cnblogs.com%2Fu%2Fzuoxiaolong%2F").openConnection();
        loginPageConnection.setRequestProperty("Connection","keep-alive");
        loginPageConnection.setRequestMethod("GET");
        loginPageConnection.connect();
        String body = IOUtil.read(loginPageConnection.getInputStream());
        String cookie = loginPageConnection.getHeaderField("Set-Cookie");
        Pattern pattern = Pattern.compile("'VerificationToken':\\s*?'(.*?)'");
        Matcher matcher = pattern.matcher(body);
        String token = null;
        if (matcher.find()) {
            token = matcher.group(1);
        }

        HttpURLConnection loginConnection = (HttpURLConnection) new URL(loginUrl).openConnection();
        loginConnection.setDoInput(true);
        loginConnection.setDoOutput(true);
        loginConnection.setRequestMethod("POST");
        loginConnection.setRequestProperty("Connection", "keep-alive");
        loginConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        loginConnection.setRequestProperty("VerificationToken",token);
        loginConnection.setRequestProperty("Cookie",cookie);
        loginConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
        loginConnection.setRequestProperty("Referer","http://passport.cnblogs.com/user/signin?ReturnUrl=http%3A%2F%2Fhome.cnblogs.com%2Fu%2Fzuoxiaolong%2F");
        loginConnection.setRequestProperty("X-Requested-With","XMLHttpRequest");
        loginConnection.connect();
        OutputStream outputStream = loginConnection.getOutputStream();
        Map<String,Object> params = new HashMap<>();
        params.put("input1",publicEnrypy("zuoxiaolong"));
        params.put("input2",publicEnrypy("8810850"));
        params.put("remember", false);
        outputStream.write(JSONObject.fromObject(params).toString().getBytes("UTF-8"));
        outputStream.flush();
        String res = IOUtil.read(loginConnection.getInputStream());
        System.out.println(res);
        List<String> cooks = loginConnection.getHeaderFields().get("Set-Cookie");
        String cook = cooks.get(0) + ";" + cooks.get(1);

        HttpURLConnection connection = (HttpURLConnection) new URL("http://i.cnblogs.com/EditPosts.aspx?pg=1").openConnection();
        connection.setRequestProperty("Cookie",cook);
        String con = IOUtil.read(connection.getInputStream());
        System.out.println(con);
    }

}
