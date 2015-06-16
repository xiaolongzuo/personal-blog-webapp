package com.zuoxiaolong.mvc;

import com.zuoxiaolong.config.Configuration;

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
 * @since 5/27/2015 11:05 AM
 */
public abstract class DataMapLoader {

    public static Map<String, DataMap> load() {
        Map<String, DataMap> dataMap = new HashMap<String, DataMap>();
        File[] files = Configuration.getClasspathFile("com/zuoxiaolong/dynamic").listFiles();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.endsWith(".class")) {
                fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
            }
            try {
                Class<?> clazz = Configuration.getClassLoader().loadClass("com.zuoxiaolong.dynamic." + fileName);
                if (DataMap.class.isAssignableFrom(clazz) && clazz != DataMap.class) {
                    String lowerCaseFileName = fileName.toLowerCase();
                    char[] originChars = fileName.toCharArray();
                    char[] lowerChars = lowerCaseFileName.toCharArray();
                    StringBuffer key = new StringBuffer();
                    for (int j = 0; j < originChars.length; j++) {
                        if (j == 0) {
                            key.append(lowerChars[j]);
                        } else if (originChars[j] != lowerChars[j]) {
                            key.append('_').append(lowerChars[j]);
                        } else {
                            key.append(originChars[j]);
                        }
                    }
                    Namespace namespaceAnnotation = clazz.getDeclaredAnnotation(Namespace.class);
                    if (namespaceAnnotation == null) {
                        throw new RuntimeException(clazz.getName() + " must has annotation with @Namespace");
                    }
                    dataMap.put(namespaceAnnotation.value() + "/" + key.toString(), (DataMap) clazz.newInstance());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return dataMap;
    }
}
