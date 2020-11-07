package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.CommentIdVisitorIpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/commentIdVisitorIp")
public class CommentIdVisitorIpController {

    @Resource
    private CommentIdVisitorIpService commentIdVisitorIpService;

    @RequestMapping("/save")
    public boolean save(Integer commentId, String visitorIp, String username) {
        return commentIdVisitorIpService.save(commentId, visitorIp, username);
    }

    @RequestMapping("/exists")
    public boolean exists(Integer commentId, String visitorIp, String username) {
        return commentIdVisitorIpService.exists(commentId, visitorIp, username);
    }
}
