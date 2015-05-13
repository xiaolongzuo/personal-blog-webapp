<div class="l_box f_l">
    <!-- 主题内容模块 -->
    <div class="topnews">
        <h2>
            <b>全部文章</b>
        </h2>
    <#if pageArticles??>
        <#list pageArticles as article>
            <div class="blogs">
                <figure><img src="${article.icon}"></figure>
                <ul>
                    <h3><a href="article_${article.id}.html" target="_blank">${article.subject}</a></h3>
                    <p>
                    ${article.summary}...
                    </p>
                    <p class="autor">
                        <span class="lm f_l"><a href="/">${article.username}</a></span>
                        <span class="dtime f_l">${article.create_date?substring(0,10)}</span>
                        <span class="viewnum f_r">浏览（${article.access_times}）</span>
                        <span class="pingl f_r">评论（${article.comment_times}）</span>
                    </p>
                </ul>
            </div>

        </#list>
        <#include "page.ftl">
    </#if>
    </div>
</div>