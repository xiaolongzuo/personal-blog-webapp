<div class="topnews">
    <h2>
                <span>
                    <a href="#" >技术</a>
                    <a href="#" >生活</a>
                    <a href="#" >职场</a>
                </span>
        <b>文章</b>推荐
    </h2>
<#if articles??>
    <#list articles as article>
        <#if article_index gt 4>
            <#break />
        </#if>
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
</#if>
</div>