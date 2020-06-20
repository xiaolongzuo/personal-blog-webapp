package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.CityRepository;
import com.zuoxiaolong.blog.entity.City;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolongzuo
 */
@Service
public class CityService {

    @Resource
    private CityRepository cityRepository;

    public List<Map<String, String>> getCities(Integer provinceId) {
        List<City> cities = cityRepository.findByProvinceId(provinceId);
        List<Map<String, String>> result = new ArrayList<>();
        if (cities != null) {
            for (City city : cities) {
                result.add(transfer(city));
            }
        }
        return result;
    }

    public Map<String, String> transfer(City city) {
        Map<String, String> tag = new HashMap<String, String>();
        tag.put("id", String.valueOf(city.getId()));
        tag.put("name", city.getName());
        return tag;
    }

}
