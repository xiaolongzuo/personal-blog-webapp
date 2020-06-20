package com.zuoxiaolong.blog.client;

import java.util.List;
import java.util.Map;

public interface DictionaryDubboService {

    List<Map<String, String>> getDictionariesByType(final String type);

    String getName(final Integer id);

}
