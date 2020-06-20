package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.CityDubboService;
import com.zuoxiaolong.blog.service.CityService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CityDubboServiceImpl implements CityDubboService {

    @Resource
    private CityService cityService;

    @Override
    public List<Map<String, String>> getCities(Integer provinceId) {
        return cityService.getCities(provinceId);
    }

}
