    <!-- 关注模块 -->
    <div class="follow_me">
        <h3>关注我</h3>
        <div class="follow_me_info">
            个人的技术交流QQ群：300638185
        </div>
        <div class="follow_me_link">
            <ul>
                <li><a class="txwb" href="http://t.qq.com/zuoxiaolong1988" target="_blank">腾讯微博</a></li>
                <li><a class="rss" href="https://github.com/xiaolongzuo" target="_blank">Github</a></li>
            </ul>
        </div>
    </div>

    <!-- 宣传图片
    <div class="right_image"><img src="${contextPath}/resources/img/right_xuanchuan.jpg"></div>
    -->

    <!-- 排行榜模块 -->
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
                <li class="cur"><a href="/">点击排行</a></li>
                <li><a href="/">最新文章</a></li>
                <li><a href="/">站长推荐</a></li>
            </ul>
        </div>
        <div class="ms-main" id="ms-main">
            <div style="display: block;" class="display_none charts_list">
                <ul>
                <#list accessCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}/html/article_${article.id}.html" target="_blank" title="${article.subject}">${article.subject}</a></li>
                </#list>
                </ul>
            </div>
            <div class="display_none charts_list">
                <ul>
                <#list newCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}/html/article_${article.id}.html" target="_blank" title="${article.subject}">${article.subject}</a></li>
                </#list>
                </ul>
            </div>
            <div class="display_none charts_list">
                <ul>
                <#list recommendCharts as article>
                    <#if article_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="${contextPath}/html/article_${article.id}.html" target="_blank" title="${article.subject}">${article.subject}</a></li>
                </#list>
                </ul>
            </div>
        </div>
    </div>
    <!--切换卡 moreSelect end -->

    <!-- 标签模块 -->
    <div class="tag_div">
        <h3>标签云</h3>
        <ul>
            <li><a href="#">个人博客</a></li>
            <li><a href="#">web开发</a></li>
            <li><a href="#">前端设计</a></li>
            <li><a href="#">Html</a></li>
            <li><a href="#">CSS3</a></li>
            <li><a href="#">百度</a></li>
            <li><a href="#">Javasript</a></li>
        </ul>
    </div>

    <!-- 图文模块 -->
    <div class="tuwen">
        <h3>图文推荐</h3>
        <ul>
        <#list imageArticles as article>
            <#if article_index gt 4>
                <#break />
            </#if>
            <li><a href="${contextPath}/html/article_${article.id}.html" target="_blank" title="${article.subject}"><img src="${article.icon}"><b>${article.shortSubject}</b></a>

                <p><span class="tulanmu"><a href="/">${article.username}</a></span><span
                        class="tutime">${article.create_date?substring(0,10)}</span>
                </p>
            </li>
        </#list>
        </ul>
    </div>

    <!-- 宣传图片
    <div class="right_image"><img src="${contextPath}/resources/img/03.jpg"></div>
    -->

    <!-- 链接模块 -->
    <div class="links">
        <h3><span>[<a href="#">申请友情链接</a>]</span>友情链接</h3>
        <ul>
            <li><a href="http://www.cnblogs.com/zuoxiaolong" target="_blank">Zeus博客园</a></li>
        </ul>
    </div>
