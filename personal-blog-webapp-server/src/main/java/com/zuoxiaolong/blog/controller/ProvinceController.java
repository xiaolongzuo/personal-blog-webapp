package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.ProvinceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Resource
    private ProvinceService provinceService;

    @RequestMapping("/getId")
    public Integer getId(String name) {
        return provinceService.getId(name);
    }

    @RequestMapping("/getProvinces")
    public List<Map<String, String>> getProvinces() {
        return provinceService.getProvinces();
    }
}
