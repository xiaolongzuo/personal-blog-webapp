package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.AnswerIdVisitorIpDubboService;
import com.zuoxiaolong.blog.service.AnswerIdVisitorIpService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class AnswerIdVisitorIpDubboServiceImpl implements AnswerIdVisitorIpDubboService {

    @Resource
    private AnswerIdVisitorIpService answerIdVisitorIpService;

    @Override
    public boolean save(int answerId, String visitorIp, String username) {
        return answerIdVisitorIpService.save(answerId, visitorIp, username);
    }

    @Override
    public boolean exists(int answerId, String visitorIp, String username) {
        return answerIdVisitorIpService.exists(answerId, visitorIp, username);
    }
}
