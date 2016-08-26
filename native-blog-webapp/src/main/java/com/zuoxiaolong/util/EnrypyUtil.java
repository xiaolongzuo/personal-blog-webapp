package com.zuoxiaolong.util;

/*
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

import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * @author 左潇龙
 * @since 2015年5月23日 上午2:09:27
 */
public abstract class EnrypyUtil {

	/**
     * 加密的方法,使用公钥进行加密
     * @throws Exception
     */
    public static String publicEnrypy(String publicKey, String data) throws Exception {

        // 得到公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(base64Decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);

        // 对数据进行加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(data.getBytes());
        return base64Encode(result);
    }
    
    public static String md5(String source) throws Exception {
    	byte [] buffer = source.getBytes("UTF-8");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buffer);
        byte [] temp = md5.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : temp) {
        	stringBuffer.append(Integer.toHexString(b&0xff));
        }
        return stringBuffer.toString();
    }
    
    public static byte[] base64Decode(String data) {
    	return Base64.getDecoder().decode(data);
    }
    
    public static String base64Encode(byte[] data) {
    	return Base64.getEncoder().encodeToString(data);
    }
    
}
