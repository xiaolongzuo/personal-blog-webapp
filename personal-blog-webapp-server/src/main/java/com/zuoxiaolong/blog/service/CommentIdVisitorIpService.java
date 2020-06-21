package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.CommentIdVisitorIpRepository;
import com.zuoxiaolong.blog.entity.CommentIdVisitorIp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentIdVisitorIpService {

    @Resource
    private CommentIdVisitorIpRepository commentIdVisitorIpRepository;

    public boolean save(final int commentId, final String visitorIp, final String username) {
        CommentIdVisitorIp commentIdVisitorIp = new CommentIdVisitorIp();
        commentIdVisitorIp.setCommentId(commentId);
        commentIdVisitorIp.setVisitorIp(visitorIp);
        commentIdVisitorIp.setUsername(username);
        commentIdVisitorIpRepository.save(commentIdVisitorIp);
        return true;
    }

    public boolean exists(final int commentId, final String visitorIp, final String username) {
        CommentIdVisitorIp commentIdVisitorIp1 = commentIdVisitorIpRepository.findByCommentIdAndAndVisitorIp(commentId, visitorIp);
        CommentIdVisitorIp commentIdVisitorIp2 = commentIdVisitorIpRepository.findByCommentIdAndUsername(commentId, username);
        return commentIdVisitorIp1 != null || commentIdVisitorIp2 != null;
    }

}
