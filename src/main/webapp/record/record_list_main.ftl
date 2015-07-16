<div class="main-div">
    <h1>
        推荐记录
    </h1>
<#if records??>
    <#list records as record>
        <div class="questions">
            <ul>
                <h3>
                    <a href="${contextPath}${record.url}">${record.title}</a>
                </h3>
                <p>
                ${record.summary}...
                </p>
                <p class="autor">
                    <span class="username_bg_image float_left"><a href="#">${record.username}</a></span>
                    <span class="time_bg_image float_left">${record.create_date}</span>
                </p>
            </ul>
        </div>
    </#list>
	<#include "../common/page.ftl">
</#if>
</div>