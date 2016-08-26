	<div class="tuwen">
        <h3>随机推荐</h3>
        <ul>
        <#list imageArticles as article>
            <#if article_index gt 4>
                <#break />
            </#if>
            <li><a href="${contextPath}${article.url}" title="${article.subject}"><img src="${article.icon}"><b>${article.short_subject}</b></a>

                <p><span class="tulanmu"><a href="#">${article.username}</a></span><span
                        class="tutime">${article.create_date?substring(0,10)}</span>
                </p>
            </li>
        </#list>
        </ul>
    </div>