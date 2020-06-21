package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.ArticleIdVisitorIpRepository;
import com.zuoxiaolong.blog.entity.ArticleIdVisitorIp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleIdVisitorIpService {

    @Resource
    private ArticleIdVisitorIpRepository articleIdVisitorIpRepository;

    public boolean save(final int articleId, final String visitorIp, final String username) {
        ArticleIdVisitorIp articleIdVisitorIp = new ArticleIdVisitorIp();
        articleIdVisitorIp.setArticleId(articleId);
        articleIdVisitorIp.setVisitorIp(visitorIp);
        articleIdVisitorIp.setUsername(username);
        articleIdVisitorIpRepository.save(articleIdVisitorIp);
        return true;
    }

    public boolean exists(final int articleId, final String visitorIp, final String username) {
        ArticleIdVisitorIp articleIdVisitorIp1 = articleIdVisitorIpRepository.findByArticleIdAndAndVisitorIp(articleId, visitorIp);
        ArticleIdVisitorIp articleIdVisitorIp2 = articleIdVisitorIpRepository.findByArticleIdAndUsername(articleId, username);
        return articleIdVisitorIp1 != null || articleIdVisitorIp2 != null;
    }

}
