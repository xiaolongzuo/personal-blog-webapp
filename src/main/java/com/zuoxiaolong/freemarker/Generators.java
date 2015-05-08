package com.zuoxiaolong.freemarker;

import com.zuoxiaolong.dao.ArticleDao;

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
 * @since 5/7/2015 5:44 PM
 */
public abstract class Generators {

    private static final Map<Class<? extends Generator>,Generator> generators;

    static {
        generators = new HashMap<Class<? extends Generator>, Generator>();
        generators.put(IndexGenerator.class, new IndexGenerator());
        generators.put(ArticleGenerator.class, new ArticleGenerator());
    }

    public static void generate() {
        for (Class<? extends Generator> key : generators.keySet()) {
            generators.get(key).generate();
        }
    }

    public static void generate(Integer id) {
        ((ArticleGenerator)generators.get(ArticleGenerator.class)).generateArticle(id, ArticleDao.getArticles("id"));
    }

}
