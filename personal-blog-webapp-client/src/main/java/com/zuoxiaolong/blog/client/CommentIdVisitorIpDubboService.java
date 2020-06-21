package com.zuoxiaolong.blog.client;

public interface CommentIdVisitorIpDubboService {

    boolean save(final int commentId, final String visitorIp, final String username);

    boolean exists(final int commentId, final String visitorIp, final String username);

}
