<div class="main-div">
    <h1>
        最近问题
    </h1>
<#if questions??>
    <#list questions as question>
        <div class="questions">
            <ul>
                <h3>
                    <a href="${contextPath}${question.url}">${question.title}</a>
                </h3>
                <p>
                ${question.summary}...
                </p>
                <p class="autor">
                    <span class="username_bg_image float_left"><a href="#">${question.username}</a></span>
                    <span class="time_bg_image float_left">${question.create_date}</span>
                </p>
            </ul>
        </div>
    </#list>
	<#include "../common/page.ftl">
</#if>
</div>