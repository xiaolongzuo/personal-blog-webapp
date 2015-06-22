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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 左潇龙
 * @since 5/22/2015 3:10 PM
 */
public abstract class IOUtil {

    public static void copy(InputStream inputStream , String file) throws IOException {
        copy(inputStream , new File(file));
    }

    public static void copy(InputStream inputStream , File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(IOUtil.readBytes(inputStream));
        outputStream.flush();
        outputStream.close();
    }

    public static String read(InputStream inputStream) throws IOException {
        return new String(readBytes(inputStream), "UTF-8");
    }
    
    public static byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] stringBytes = new byte[0];
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0) {
            byte[] tempStringBytes = new byte[stringBytes.length + len];
            System.arraycopy(stringBytes, 0, tempStringBytes, 0, stringBytes.length);
            System.arraycopy(bytes, 0, tempStringBytes, stringBytes.length, len);
            stringBytes = tempStringBytes;
        }
        return stringBytes;
    }

}
