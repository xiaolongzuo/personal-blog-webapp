package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.AnswerIdVisitorIpRepository;
import com.zuoxiaolong.blog.entity.AnswerIdVisitorIp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnswerIdVisitorIpService {

    @Resource
    private AnswerIdVisitorIpRepository answerIdVisitorIpRepository;

    public boolean save(final int answerId, final String visitorIp, final String username) {
        AnswerIdVisitorIp answerIdVisitorIp = new AnswerIdVisitorIp();
        answerIdVisitorIp.setAnswerId(answerId);
        answerIdVisitorIp.setVisitorIp(visitorIp);
        answerIdVisitorIp.setUsername(username);
        answerIdVisitorIpRepository.save(answerIdVisitorIp);
        return true;
    }

    public boolean exists(final int answerId, final String visitorIp, final String username) {
        AnswerIdVisitorIp answerIdVisitorIp1 = answerIdVisitorIpRepository.findByAnswerIdAndAndVisitorIp(answerId, visitorIp);
        AnswerIdVisitorIp answerIdVisitorIp2 = answerIdVisitorIpRepository.findByAnswerIdAndUsername(answerId, username);
        return answerIdVisitorIp1 != null || answerIdVisitorIp2 != null;
    }

}
