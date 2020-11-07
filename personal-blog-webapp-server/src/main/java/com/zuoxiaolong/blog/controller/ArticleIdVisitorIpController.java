package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.ArticleIdVisitorIpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/articleIdVisitorIp")
public class ArticleIdVisitorIpController {

    @Resource
    private ArticleIdVisitorIpService articleIdVisitorIpService;

    @RequestMapping("/save")
    public boolean save(Integer articleId, String visitorIp, String username) {
        return articleIdVisitorIpService.save(articleId, visitorIp, username);
    }

    @RequestMapping("/exists")
    public boolean exists(Integer articleId, String visitorIp, String username) {
        return articleIdVisitorIpService.exists(articleId, visitorIp, username);
    }
}
