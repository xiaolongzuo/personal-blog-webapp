package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.ArticleTagRepository;
import com.zuoxiaolong.blog.entity.ArticleTag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleTagService {

    @Resource
    private ArticleTagRepository articleTagRepository;

    public boolean save(final int articleId, final int tagId) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(articleId);
        articleTag.setTagId(tagId);
        articleTagRepository.save(articleTag);
        return true;
    }

    public boolean exsits(final int articleId, final int tagId) {
        ArticleTag articleTag = articleTagRepository.findByArticleIdAndTagId(articleId, tagId);
        return articleTag != null;
    }

}
