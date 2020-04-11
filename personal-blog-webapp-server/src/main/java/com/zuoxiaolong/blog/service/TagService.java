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

package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.TagRepository;
import com.zuoxiaolong.blog.entity.Tag;
import com.zuoxiaolong.util.StringUtil;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolongzuo
 */
@Service
public class TagService {

    @Resource
    private TagRepository tagRepository;

    public List<Map<String, Object>> getHotTags() {
        List<Tag> tags = tagRepository.getHotTags();
        List<Map<String, Object>> result = new ArrayList<>();
        if (tags != null) {
            for (Tag resultSet : tags) {
                Map<String, Object> tag = new HashMap<>();
                tag.put("id", resultSet.getId());
                tag.put("tag_name", resultSet.getTagName());
                tag.put("short_tag_name", StringUtil.substring(resultSet.getTagName(), 4));
                result.add(tag);
            }
        }
        return result;
    }
}
