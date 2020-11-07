package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.service.RecordIdVisitorIpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/recordIdVisitorIp")
public class RecordIdVisitorIpController {

    @Resource
    private RecordIdVisitorIpService recordIdVisitorIpService;

    @RequestMapping("/save")
    public boolean save(Integer recordId, String visitorIp, String username) {
        return recordIdVisitorIpService.save(recordId, visitorIp, username);
    }

    @RequestMapping("/exists")
    public boolean exists(Integer recordId, String visitorIp, String username) {
        return recordIdVisitorIpService.exists(recordId, visitorIp, username);
    }
}
