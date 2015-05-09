<div id="pagerDiv">
	共${pager.total?default(0)}篇文章&nbsp;&nbsp;&nbsp;共${pager.page?default(1)}页&nbsp;&nbsp;&nbsp;当前第${pager.current?default(1)}页&nbsp;&nbsp;&nbsp;
	<#if pager.current gt 1>
		<a href="article_list_1.html">第一页</a>
		<a href="article_list_${pager.current-1}.html">上一页</a>
	</#if>
	<#if pager.current lt pager.page?default(1)>
		<a href="article_list_${pager.current+1}.html">下一页</a>
		<a href="article_list_${pager.page?default(1)}.html">最后一页</a>
	</#if>
</div>