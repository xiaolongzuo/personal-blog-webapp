package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.ArticleTagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/articleTag")
public class ArticleTagController {

    @Resource
    private ArticleTagService articleTagService;

    @RequestMapping("/save")
    public boolean save(Integer articleId, Integer tagId) {
        return articleTagService.save(articleId, tagId);
    }

    @RequestMapping("/exists")
    public boolean exists(Integer articleId, Integer tagId) {
        return articleTagService.exsits(articleId, tagId);
    }

}
