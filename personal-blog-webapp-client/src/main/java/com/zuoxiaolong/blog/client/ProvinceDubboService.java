package com.zuoxiaolong.blog.client;

import java.util.List;
import java.util.Map;

public interface ProvinceDubboService {

    Integer getId(final String name);

    List<Map<String, String>> getProvinces();

}
