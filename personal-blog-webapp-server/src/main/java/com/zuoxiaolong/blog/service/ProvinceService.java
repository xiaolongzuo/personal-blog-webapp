package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.ProvinceRepository;
import com.zuoxiaolong.blog.entity.Province;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProvinceService {

    @Resource
    private ProvinceRepository provinceRepository;

    public Integer getId(final String name) {
        Province province = provinceRepository.findByName(name);
        if (province != null) {
            return province.getId();
        }
        return null;
    }

    public List<Map<String, String>> getProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        if (provinces != null) {
            return provinces.stream().map(province -> transfer(province)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public Map<String, String> transfer(Province province) {
        Map<String, String> provinceMap = new HashMap<String, String>();
        provinceMap.put("id", String.valueOf(province.getId()));
        provinceMap.put("name", province.getName());
        return provinceMap;
    }

}
