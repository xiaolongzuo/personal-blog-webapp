package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.ArticleTagDubboService;
import com.zuoxiaolong.blog.service.ArticleTagService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class ArticleTagDubboServiceImpl implements ArticleTagDubboService {

    @Resource
    private ArticleTagService articleTagService;

    @Override
    public boolean save(int articleId, int tagId) {
        return articleTagService.save(articleId, tagId);
    }

    @Override
    public boolean exsits(int articleId, int tagId) {
        return articleTagService.exsits(articleId, tagId);
    }

}
