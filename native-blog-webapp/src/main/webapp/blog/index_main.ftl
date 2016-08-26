<div class="main-div">
    <h1>
        最新文章
    </h1>
<#if articles??>
    <#list articles as article>
        <#if article_index gt 5>
            <#break />
        </#if>
        <div class="blogs">
            <figure><img src="${article.icon}" title="niubi-job——一个分布式的任务调度框架"></figure>
            <ul>
                <h3><a href="${contextPath}${article.url}">${article.subject}</a></h3>
                <p>
                ${article.summary}...
                </p>
                <p class="autor">
                    <span class="username_bg_image float_left"><a href="#">${article.username}</a></span>
                    <span class="time_bg_image float_left">${article.create_date?substring(0,10)}</span>
                    <span class="access_times_bg_image float_right">浏览（${article.access_times}）</span>
                    <span class="comment_times_bg_image float_right">评论（${article.comment_times}）</span>
                </p>
            </ul>
        </div>
    </#list>
</#if>
</div>