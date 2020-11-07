package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.AnswerIdVisitorIpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/answerIdVisitorIp")
public class AnswerIdVisitorIpController {

    @Resource
    private AnswerIdVisitorIpService answerIdVisitorIpService;

    @RequestMapping("/save")
    public boolean save(Integer answerId, String visitorIp, String username) {
        return answerIdVisitorIpService.save(answerId, visitorIp, username);
    }

    @RequestMapping("/exists")
    public boolean exists(Integer answerId, String visitorIp, String username) {
        return answerIdVisitorIpService.exists(answerId, visitorIp, username);
    }
}
