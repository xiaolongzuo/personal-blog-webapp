package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.ProvinceDubboService;
import com.zuoxiaolong.blog.service.ProvinceService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProvinceDubboServiceImpl implements ProvinceDubboService {

    @Resource
    private ProvinceService provinceService;

    @Override
    public Integer getId(String name) {
        return provinceService.getId(name);
    }

    @Override
    public List<Map<String, String>> getProvinces() {
        return provinceService.getProvinces();
    }
}
