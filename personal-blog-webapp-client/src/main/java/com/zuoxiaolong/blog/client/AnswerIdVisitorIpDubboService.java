package com.zuoxiaolong.blog.client;

public interface AnswerIdVisitorIpDubboService {

    boolean save(final int answerId, final String visitorIp, final String username);

    boolean exists(final int answerId, final String visitorIp, final String username);

}
