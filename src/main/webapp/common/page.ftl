<div id="pagerDiv">
	共${pager.total?default(0)}篇文章&nbsp;&nbsp;&nbsp;共${pager.page?default(1)}页&nbsp;&nbsp;&nbsp;当前第${pager.current?default(1)}页&nbsp;&nbsp;&nbsp;
	<#if pager.current gt 1>
		<a href="${contextPath}${firstArticleListUrl}">第一页</a>
		<a href="${contextPath}${preArticleListUrl}">上一页</a>
	</#if>
	<#if pager.current lt pager.page?default(1)>
		<a href="${contextPath}${nextArticleListUrl}">下一页</a>
		<a href="${contextPath}${lastArticleListUrl}">最后一页</a>
	</#if>
</div>