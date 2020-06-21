package com.zuoxiaolong.blog.client;

public interface ArticleTagDubboService {

    boolean save(final int articleId, final int tagId);

    boolean exsits(final int articleId, final int tagId);

}
