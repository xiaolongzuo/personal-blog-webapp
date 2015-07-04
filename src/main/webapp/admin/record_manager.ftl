<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
    <link href="${contextPath}/resources/css/common/list.css" rel="stylesheet"/>
</head>
<body>
<#include "../common/header.ftl">
<article>
    <a href="${contextPath}/admin/record_input.ftl">新增记录</a>
<#if pageRecords??>
    <#list pageRecords as record>
        <div class="blogs">
            <ul style="float: left;">
                <h3><a href="${contextPath}/admin/record_input.ftl?id=${record.id}">${record.title}</a></h3>
                <p>
                ${record.summary}...
                </p>
                <p class="autor">
                    <span class="username_bg_image float_left"><a href="#">${record.username}</a></span>
                    <span class="time_bg_image float_left">${record.create_date?substring(0,10)}</span>
                    <span class="username_bg_image float_left"><a href="${contextPath}/record/record.ftl?id=${record.id}" target="_blank">查看</a></span>
                    <span class="username_bg_image float_left"><a href="${contextPath}/admin/adminRecordDelete.do?id=${record.id}">删除</a></span>
                    <span class="access_times_bg_image float_right">浏览（${record.access_times}）</span>
                    <span class="comment_times_bg_image float_right">赞（${record.good_times}）</span>
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