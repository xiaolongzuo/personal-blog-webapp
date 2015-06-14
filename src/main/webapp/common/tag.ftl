	<div class="tag_div">
        <h3>标签云</h3>
        <ul>
        	<#list hotTags as tag>
                <form id="" action="${contextPath}/blog/article_list.ftl" type="POST">
                    <input type="hidden" name="tag" value="${tag.tag_name}">
                </form>
                <li><a onclick="javascript:searchArticles('tag','${tag.tag_name}')" href="javascript:void(0)" title="${tag.tag_name}">${tag.tag_name}</a></li>
            </#list>
        </ul>
    </div>