<div id="pagerDiv">
	共${pager.total?default(0)}个结果&nbsp;&nbsp;&nbsp;共${pager.page?default(1)}页&nbsp;&nbsp;&nbsp;当前第${pager.current?default(1)}页&nbsp;&nbsp;&nbsp;
	<#if pager.current gt 1>
		<a href="${contextPath}${firstPageUrl}">第一页</a>
		<a href="${contextPath}${prePageUrl}">上一页</a>
	</#if>
	<#if pager.current lt pager.page?default(1)>
		<a href="${contextPath}${nextPageUrl}">下一页</a>
		<a href="${contextPath}${lastPageUrl}">最后一页</a>
	</#if>
</div>