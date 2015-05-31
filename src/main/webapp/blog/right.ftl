    <!-- 用户中心模块 -->
    <div class="user_profile">
        <script type="application/javascript">
            $(document).ready(function(){
            	$("#qq_login_button").click(function(){
            		window.location.href="${contextPath}/redirect.do";
            	});
            	$("#logout_button").click(function(){
            		$.ajax({
            			url:"${contextPath}/logout.do",
						type:"POST",
						success:function(data) {
							if(data && data == 'success'){
								window.location.href="${contextPath}";
							}
						}
            		});
            	});
            	$("#login_register_button").click(function(){
            		$.ajax({
            			url:"${contextPath}/login.do",
						type:"POST",
						data:{"username":$("input[name=username]").val(),"password":$("input[name=password]").val()},
						success:function(data){
							if(data && data.success){
								window.location.href="${contextPath}" + data.url;
							} else {
                                $("#login_error_td").css("color","red");
								$("#login_error_td").html(data);
							}
						}
            		});
            	});
            });
        </script>
        <h3>用户中心</h3>
        <#if nickName??>
            <div id="user_info_div">
                <#if avatarUrl??>
                <div id="avatar_div" class="float_left">
                    <img id="avatar_img" src="${avatarUrl}">
                </div>
                </#if>
                <div id="welcome_div" class="float_left">
                <span id="nick_name_span" class="welcome_font">${nickName}</span><span class="welcome_font">，欢迎你！</span>
                </div>
                <div id="logout_div" class="float_right">
                    <a id="logout_button" href="#" class="button">注销</a>
                </div>
            </div>
            <#else>
            <table id="login_table" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td style="font-size: 12px;">用户名：</td>
                    <td><input type="text" name="username"/></td>
                    <td><a id="login_register_button" href="#" class="button">登录&nbsp;|&nbsp;注册</a></td>
                </tr>
                <tr>
                    <td style="font-size: 12px;">密  码：</td>
                    <td><input type="password" name="password"/></td>
                    <td>
                        <!--
                        <a href="#" id="qq_login_button"><img width="50" height="20" src="${contextPath}/resources/img/qq_login.png" title="使用QQ登录"></a>
                        -->
                        &nbsp;
                    </td>
                </tr>
				<tr>
                    <td>&nbsp;</td>
                    <td colspan="2" id="login_error_td" style="font-size:11px;">用户名支持字母，数字，下划线和中文</td>
                </tr>
                </tbody>
            </table>
        </#if>
    </div>

    <!-- 关注模块 -->
    <div class="follow_me">
        <h3>关注我</h3>
        <div class="follow_me_info">
            技术交流QQ群：300638185 
        </div>
        <div class="follow_me_link">
            <ul>
                <li><a class="txwb" href="http://t.qq.com/zuoxiaolong1988" target="_blank">腾讯微博</a></li>
                <li><a class="rss" href="${contextPath}/blog/feed.xml" target="_blank">RSS订阅</a></li>
                <li><a class="github" href="http://github.com/xiaolongzuo" target="_blank">Github</a></li>
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
    <!--切换卡 moreSelect end -->

    <!-- 标签模块 -->
    <div class="tag_div">
        <h3>标签云</h3>
        <ul>
        	<#list hotTags as tag>
                <li><a href="${contextPath}/blog/article_list.ftl?tag=${tag.tag_name}" title="${tag.tag_name}">${tag.tag_name}</a></li>
            </#list>
        </ul>
    </div>

    <!-- 图文模块 -->
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

    <!-- 宣传图片
    <div class="right_image"><img src="${contextPath}/resources/img/03.jpg"></div>
    -->

    <!-- 链接模块 -->
    <div class="links">
        <h3><span>[<a href="#">申请友情链接</a>]</span>友情链接</h3>
        <ul>
            <li><a href="http://www.cnblogs.com" target="_blank">博客园</a></li>
            <li><a href="http://www.csdn.net" target="_blank">CSDN</a></li>
            <li><a href="http://blog.chinaunix.net" target="_blank">chinaunix</a></li>
            <li><a href="http://blog.51cto.com" target="_blank">51CTO</a></li>
            <li><a href="http://www.iteye.com" target="_blank">iteye</a> </li>
        </ul>
    </div>