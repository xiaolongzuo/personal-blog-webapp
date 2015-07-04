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

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.model.ViewMode;

/**
 * @author 左潇龙
 * @since 6/1/2015 5:53 PM
 */
public class ExceptionGenerator implements Generator {

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void generate() {
        Writer writerError = null;
        Writer writerNotFound = null;
        try {
            Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(ViewMode.DYNAMIC);
            data.put("error", "网站发生内部错误，请联系站长！");
            String htmlPath = Configuration.getContextPath("html/error.html");
            writerError = new FileWriter(htmlPath);
            FreemarkerHelper.generate("common","error", writerError, data);

            data.put("error", "该页面没有找到，请检查链接地址是否正确！");
            htmlPath = Configuration.getContextPath("html/not_found.html");
            writerNotFound = new FileWriter(htmlPath);
            FreemarkerHelper.generate("common","error", writerNotFound, data);

            data.put("error", "该页面需要先登录才能访问！");
            htmlPath = Configuration.getContextPath("html/login_warn.html");
            writerNotFound = new FileWriter(htmlPath);
            FreemarkerHelper.generate("common","error", writerNotFound, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writerError != null) {
                    writerError.close();
                }
                if (writerNotFound != null) {
                    writerNotFound.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
