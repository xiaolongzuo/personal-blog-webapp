	<div class="charts_tab" id="lp_right_select">
        <!-- 排行榜切换 -->
        <script>
            window.onload = function () {
                var oLi = document.getElementById("tab").getElementsByTagName("li");
                var oUl = document.getElementById("ms-main").getElementsByTagName("div");

                for (var i = 0; i < oLi.length; i++) {
                    oLi[i].index = i;
                    oLi[i].onmouseover = function () {
                        for (var n = 0; n < oLi.length; n++) oLi[n].className = "";
                        this.className = "cur";
                        for (var n = 0; n < oUl.length; n++) oUl[n].style.display = "none";
                        oUl[this.index].style.display = "block"
                    }
                }
            }
        </script>
        <div class="charts_top">
            <ul class="hd" id="tab">
                <li class="cur"><a href="${accessArticlesUrl}" title="点击查看更多">点击排行</a></li>
                <li><a href="${newArticlesUrl}" title="点击查看更多">最新文章</a></li>
                <li><a href="${recommendArticlesUrl}" title="点击查看更多">站长推荐</a></li>
            </ul>
        </div>
        <div class="ms-main" id="ms-main">
            <div style="display: block;" class="display_none charts_list">
                <ul>
                <#list accessCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}${article.url}" title="${article.subject}">${article.common_subject}</a></li>
                </#list>
                </ul>
            </div>
            <div class="display_none charts_list">
                <ul>
                <#list newCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}${article.url}" title="${article.subject}">${article.common_subject}</a></li>
                </#list>
                </ul>
            </div>
            <div class="display_none charts_list">
                <ul>
                <#list recommendCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}${article.url}" title="${article.subject}">${article.common_subject}</a></li>
                </#list>
                </ul>
            </div>
        </div>
    </div>