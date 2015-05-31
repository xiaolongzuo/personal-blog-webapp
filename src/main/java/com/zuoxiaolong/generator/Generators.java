package com.zuoxiaolong.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;

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
 * @since 5/7/2015 5:44 PM
 */
public abstract class Generators {
	
	private static final Logger logger = Logger.getLogger(Generators.class);
	
    private static final Map<Class<? extends Generator>,Generator> generatorMap;
    
    private static final List<Generator> generatorList;

    static {
    	IndexGenerator indexGenerator = new IndexGenerator();
    	ArticleGenerator articleGenerator = new ArticleGenerator();
    	ArticleListGenerator articleListGenerator = new ArticleListGenerator();
    	generatorMap = new HashMap<Class<? extends Generator>, Generator>();
    	generatorMap.put(IndexGenerator.class, indexGenerator);
    	generatorMap.put(ArticleGenerator.class, articleGenerator);
    	generatorMap.put(ArticleListGenerator.class, articleListGenerator);
    	
    	generatorList = new ArrayList<Generator>();
    	generatorList.add(indexGenerator);
    	generatorList.add(articleListGenerator);
    	generatorList.add(articleGenerator);
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

    public static void generate(Integer id) {
        ((ArticleGenerator)generatorMap.get(ArticleGenerator.class)).generateArticle(id);
    }
    
}
