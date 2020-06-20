package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.DictionaryDubboService;
import com.zuoxiaolong.blog.service.DictionaryService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryDubboServiceImpl implements DictionaryDubboService {

    @Resource
    private DictionaryService dictionaryService;

    @Override
    public List<Map<String, String>> getDictionariesByType(String type) {
        return dictionaryService.getDictionariesByType(type);
    }

    @Override
    public String getName(Integer id) {
        return dictionaryService.getName(id);
    }

}
