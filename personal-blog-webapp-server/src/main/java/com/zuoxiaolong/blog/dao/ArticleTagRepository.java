/*
 * Copyright (C) 2019 the original author or authors.
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

package com.zuoxiaolong.blog.dao;

import com.zuoxiaolong.blog.entity.ArticleTag;
import com.zuoxiaolong.blog.entity.ArticleTagId;
import com.zuoxiaolong.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xiaolongzuo
 */
public interface ArticleTagRepository extends JpaRepository<ArticleTag, ArticleTagId> {

    int deleteByTagId(Integer articleId);

    List<ArticleTag> findByArticleId(Integer articleId);

}
