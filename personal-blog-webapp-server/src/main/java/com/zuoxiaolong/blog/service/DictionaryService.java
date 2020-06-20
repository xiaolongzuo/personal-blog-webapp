package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.DictionaryRepository;
import com.zuoxiaolong.blog.entity.Dictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    public List<Map<String, String>> getDictionariesByType(final String type) {
        List<Dictionary> dictionaries = dictionaryRepository.findByType(type);
        if (dictionaries != null) {
            return dictionaries.stream().map(dictionary -> transfer(dictionary)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String getName(final Integer id) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(id);
        if (dictionary.isPresent()) {
            return dictionary.get().getName();
        }
        return null;
    }

    public Map<String, String> transfer(Dictionary dictionary) {
        Map<String, String> diactionaryMap = new HashMap<String, String>();
        diactionaryMap.put("id", String.valueOf(dictionary.getId()));
        diactionaryMap.put("name", dictionary.getName());
        return diactionaryMap;
    }

}
