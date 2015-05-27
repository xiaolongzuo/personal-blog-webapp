	<div class="follow_me">
		<h3>导航栏</h3>
		<p style="margin:10px 0px 15px 0px;">
		<span>破解JJC阵容请点击<a href="${contextPath}/dota/dota_index.ftl" style="color: blue;text-decoration: underline;">&nbsp;这里&nbsp;</a></span>
		</p>
		<p style="margin:10px 0px 20px 0px;">
		<span>录入对战结果请点击<a href="${contextPath}/dota/match_input.ftl" style="color: blue;text-decoration: underline;">&nbsp;这里&nbsp;</a></span>
		</p>
	</div>
	
	<div class="follow_me">
		<h3>公告栏</h3>
		<p style="margin:10px 0px 15px 0px;">
		<span style="color: red;">温馨提示：由于巅峰与JJC判定胜负的方式不同，为保证结果的正确性，请只录入JJC的对战结果，不要录入巅峰录象的对战结果</span>
		</p>
		
		<p style="margin:10px 0px 0px 0px;">
		当前数据库当中共保存了<span style="color: red;">${totalCount}</span>场对战结果，加油！
		</p>
	</div>

    <!-- 排行榜模块 -->
    <div class="charts_tab" id="lp_right_select">
    	<h3>英雄排行榜</h3>
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
                <li class="cur"><a href="#">热度排行榜</a></li>
                <li><a href="#">胜率排行榜</a></li>
                <li><a href="#">胜场排行榜</a></li>
            </ul>
        </div>
        <div class="ms-main" id="ms-main">
            <div style="display: block;" class="display_none charts_list">
                <ul>
                <#list hotCharts as hero>
                    <#if hero_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="#" >${hero.fullName}（出场次数：${hero.times}）</a></li>
                </#list>
                </ul>
            </div>
            <div class="display_none charts_list">
                <ul>
                <#list winCharts as hero>
                    <#if hero_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="#">${hero.fullName}（胜率：${hero.win}％）</a></li>
                </#list>
                </ul>
            </div>
			<div class="display_none charts_list">
                <ul>
                <#list winTimesCharts as hero>
                    <#if hero_index gt 5>
                        <#break />
                    </#if>
                    <li><a href="#">${hero.fullName}（胜场次数：${hero.winTimes}）</a></li>
                </#list>
                </ul>
            </div>
        </div>
    </div>
    <!--切换卡 moreSelect end -->

    <!-- 链接模块 -->
    <div class="links">
        <h3><span>[<a href="#">申请友情链接</a>]</span>友情链接</h3>
        <ul>
            <li><a href="http://www.cnblogs.com/zuoxiaolong" target="_blank">Zeus博客园</a></li>
        </ul>
    </div>
