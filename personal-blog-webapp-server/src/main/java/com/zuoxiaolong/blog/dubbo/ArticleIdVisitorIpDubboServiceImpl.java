package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.AnswerIdVisitorIpDubboService;
import com.zuoxiaolong.blog.client.ArticleIdVisitorIpDubboService;
import com.zuoxiaolong.blog.service.AnswerIdVisitorIpService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class ArticleIdVisitorIpDubboServiceImpl implements ArticleIdVisitorIpDubboService {

    @Resource
    private ArticleIdVisitorIpDubboService articleIdVisitorIpDubboService;

    @Override
    public boolean save(int articleId, String visitorIp, String username) {
        return articleIdVisitorIpDubboService.save(articleId, visitorIp, username);
    }

    @Override
    public boolean exists(int articleId, String visitorIp, String username) {
        return articleIdVisitorIpDubboService.exists(articleId, visitorIp, username);
    }
}
