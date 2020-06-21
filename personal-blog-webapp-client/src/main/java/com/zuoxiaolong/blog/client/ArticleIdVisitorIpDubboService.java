package com.zuoxiaolong.blog.client;

public interface ArticleIdVisitorIpDubboService {

    boolean save(final int articleId, final String visitorIp, final String username);

    boolean exists(final int articleId, final String visitorIp, final String username);

}
