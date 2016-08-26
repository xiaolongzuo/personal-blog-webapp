package com.zuoxiaolong.orm;

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

import com.zuoxiaolong.config.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 15/6/22 16:19
 */
public abstract class DaoFactory {

    private static Map<Class<?> , BaseDao> daoMap = new HashMap<>();

    static {
        File[] files = Configuration.getClasspathFile("com/zuoxiaolong/dao").listFiles();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.endsWith(".class")) {
                fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
            }
            try {
                Class<?> clazz = Configuration.getClassLoader().loadClass("com.zuoxiaolong.dao." + fileName);
                if (BaseDao.class.isAssignableFrom(clazz)) {
                    daoMap.put(clazz, (BaseDao) clazz.newInstance());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> T getDao(Class<T> clazz) {
        return (T)daoMap.get(clazz);
    }

}
