package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.DictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping("/getDictionariesByType")
    public List<Map<String, String>> getDictionariesByType(String type) {
        return dictionaryService.getDictionariesByType(type);
    }

    @RequestMapping("/getName")
    public String getName(Integer id) {
        return dictionaryService.getName(id);
    }

}
