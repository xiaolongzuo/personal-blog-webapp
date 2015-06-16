package com.zuoxiaolong.mvc;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.servlet.AbstractServlet;
import com.zuoxiaolong.servlet.Servlet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

/**
 * @author 左潇龙
 * @since 6/16/2015 11:01 AM
 */
public abstract class Scanner {

    public static Map<String, Servlet> scan() {
        Map<String, Servlet> mapping = new HashMap<>();
        File[] files = Configuration.getClasspathFile("com/zuoxiaolong/servlet").listFiles();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.endsWith(".class")) {
                fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
            }
            try {
                Class<?> clazz = Configuration.getClassLoader().loadClass("com.zuoxiaolong.servlet." + fileName);
                if (Servlet.class.isAssignableFrom(clazz) && clazz != Servlet.class && clazz != AbstractServlet.class) {
                    RequestMapping requestMappingAnnotation = clazz.getDeclaredAnnotation(RequestMapping.class);
                    if (requestMappingAnnotation != null) {
                        mapping.put(requestMappingAnnotation.value(), (Servlet) clazz.newInstance());
                    } else {
                        String lowerCaseFileName = fileName.toLowerCase();
                        char[] originChars = fileName.toCharArray();
                        char[] lowerChars = lowerCaseFileName.toCharArray();
                        StringBuffer key = new StringBuffer();
                        for (int j = 0; j < originChars.length; j++) {
                            if (j == 0) {
                                key.append(lowerChars[j]);
                            } else if (j == originChars.length - 1) {
                                key.append(originChars[j]).append(".do");
                            } else {
                                key.append(originChars[j]);
                            }
                        }
                        mapping.put(key.toString(), (Servlet) clazz.newInstance());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return mapping;
    }
}
