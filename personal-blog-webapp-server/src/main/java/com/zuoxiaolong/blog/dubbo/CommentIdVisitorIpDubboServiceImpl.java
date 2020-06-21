package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.ArticleIdVisitorIpDubboService;
import com.zuoxiaolong.blog.client.CommentIdVisitorIpDubboService;
import com.zuoxiaolong.blog.service.CommentIdVisitorIpService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class CommentIdVisitorIpDubboServiceImpl implements CommentIdVisitorIpDubboService {

    @Resource
    private CommentIdVisitorIpService commentIdVisitorIpService;

    @Override
    public boolean save(int commentId, String visitorIp, String username) {
        return commentIdVisitorIpService.save(commentId, visitorIp, username);
    }

    @Override
    public boolean exists(int commentId, String visitorIp, String username) {
        return commentIdVisitorIpService.exists(commentId, visitorIp, username);
    }
}
