<div id="pagerDiv">
	共${pager.total?default(0)}篇文章&nbsp;&nbsp;&nbsp;共${pager.page?default(1)}页&nbsp;&nbsp;&nbsp;当前第${pager.current?default(1)}页&nbsp;&nbsp;&nbsp;
	<#if pager.current gt 1>
		<a href="${contextPath}/blog/article_list.ftl?current=1">第一页</a>
		<a href="${contextPath}/html/article_list.ftl?current=${pager.current-1}">上一页</a>
	</#if>
	<#if pager.current lt pager.page?default(1)>
		<a href="${contextPath}/html/article_list.ftl?current=${pager.current+1}">下一页</a>
		<a href="${contextPath}/html/article_list.ftl?current=${pager.page?default(1)}">最后一页</a>
	</#if>
</div>