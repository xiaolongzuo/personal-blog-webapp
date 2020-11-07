package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
public class CityController {

    @Resource
    private CityService cityService;

    @RequestMapping("/getCities")
    public List<Map<String, String>> getCities(Integer provinceId) {
        return cityService.getCities(provinceId);
    }

}
