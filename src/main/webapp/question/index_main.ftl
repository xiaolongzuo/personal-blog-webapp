<div class="main-div">
    <h2>
        <b>相关</b>问题
    </h2>
<#if questions??>
    <#list questions as question>
        <div class="blogs">
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
	<#include "../common/page.ftl">
</#if>
</div>