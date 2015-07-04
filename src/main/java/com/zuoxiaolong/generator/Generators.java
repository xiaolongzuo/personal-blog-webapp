package com.zuoxiaolong.generator;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zuoxiaolong.orm.BaseDao;
import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;

/**
 * @author 左潇龙
 * @since 5/7/2015 5:44 PM
 */
public abstract class Generators {
	
	private static final Logger logger = Logger.getLogger(Generators.class);
	
    private static final Map<Class<? extends Generator>,Generator> generatorMap;
    
    private static final List<Generator> generatorList;

    static {
        generatorMap = new HashMap<>();
        generatorList = new ArrayList<>();
        File[] files = Configuration.getClasspathFile("com/zuoxiaolong/generator").listFiles();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.endsWith(".class")) {
                fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
            }
            try {
                Class<?> clazz = Configuration.getClassLoader().loadClass("com.zuoxiaolong.generator." + fileName);
                if (Generator.class.isAssignableFrom(clazz) && Generator.class != clazz) {
                    Generator generator = (Generator) clazz.newInstance();
                    generatorMap.put((Class<? extends Generator>) clazz, generator);
                    List<Generator> copy = new ArrayList<>(generatorList);
                    for (int j = 0; j < copy.size() ; j++) {
                        if (copy.get(j).order() > generator.order()) {
                            generatorList.add(j, generator);
                            break;
                        }
                    }
                    if (generatorList.size() == copy.size()) {
                        generatorList.add(generator);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void generate() {
    	String path = Configuration.getContextPath("html");
    	File htmlDirectory = new File(path);
    	if (!htmlDirectory.exists() && !htmlDirectory.mkdir()) {
    		logger.error("mkdir failed for :" + path);
    		return;
		}
        for (int i = 0; i < generatorList.size(); i++) {
			generatorList.get(i).generate();
		}
    }

    public static void generateArticle(Integer id) {
        ((ArticleGenerator)generatorMap.get(ArticleGenerator.class)).generateArticle(id);
    }

	public static void generateQuestion(Integer id) {
		((QuestionGenerator)generatorMap.get(QuestionGenerator.class)).generateQuestion(id);
	}

    public static void generateRecord(Integer id) {
        ((RecordGenerator)generatorMap.get(RecordGenerator.class)).generateRecord(id);
    }
    
}
