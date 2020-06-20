package com.zuoxiaolong.blog.client;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolongzuo
 */
public interface CityDubboService {

    List<Map<String, String>> getCities(Integer provinceId);

}
