<!DOCTYPE html>
<html>
<head>
<#include "../common/meta.ftl">
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/list.css" rel="stylesheet"/>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<a href="${contextPath}/admin/article_input.ftl">新增文章</a>
	<#if pageArticles??>
        <#list pageArticles as article>
            <div class="blogs">
                <figure><img src="${article.icon}"></figure>
                <ul>
                    <h3><a href="${contextPath}/admin/article_input.ftl?id=${article.id}">${article.subject}</a></h3>
                    <p>
                    ${article.summary}...
                    </p>
                    <p class="autor">
                        <span class="username_bg_image float_left"><a href="#">${article.username}</a></span>
                        <span class="time_bg_image float_left">${article.create_date?substring(0,10)}</span>
						<span class="username_bg_image float_left"><a href="${contextPath}/blog/article.ftl?id=${article.id}" target="_blank">查看</a></span>
						<span class="username_bg_image float_left"><a href="${contextPath}/admin/adminArticleDelete.do?id=${article.id}">删除</a></span>
                        <span class="access_times_bg_image float_right">浏览（${article.access_times}）</span>
                        <span class="comment_times_bg_image float_right">评论（${article.comment_times}）</span>
                    </p>
                </ul>
            </div>
        </#list>
        <#include "../common/page.ftl">
    </#if>
</article>
<#include "../common/footer.ftl">
</body>
</html>