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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;

/**
 * @author 左潇龙
 * @since 15/6/22 02:24
 */
public abstract class JsoupUtil {

    public static Element parse(String html) {
        return Jsoup.parse(html);
    }

    public static String getText(String html) {
        StringBuffer stringBuffer = new StringBuffer();
        appendText(parse(html), stringBuffer);
        return stringBuffer.toString();
    }

    public static void appendText(String html, StringBuffer stringBuffer) {
        appendText(parse(html), stringBuffer);
    }

    public static void appendText(Element element, StringBuffer stringBuffer) {
        List<Node> nodes = element.childNodes();
        if (nodes != null && nodes.size() > 0) {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (node instanceof TextNode) {
                    String text = ((TextNode) node).text();
                    if (text.trim().length() > 0) {
                        stringBuffer.append(text);
                    }
                } else {
                    Element child = (Element) node;
                    appendText(child, stringBuffer);
                }
            }
        }
    }

}
