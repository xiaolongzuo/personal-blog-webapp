<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>Zeus个人博客</title>
    <meta name="keywords" content="个人博客,Zeus">
    <meta name="description" content="左潇龙,Zeus,个人博客">
    <link href="resources/css/base.css" rel="stylesheet">
    <link href="resources/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="resources/js/jquery.js"></script>
    <script type="text/javascript" src="resources/js/sliders.js"></script>
    <!-- 返回顶部调用 begin -->
    <script type="text/javascript" src="resources/js/jquery.htm"></script>
    <script type="text/javascript" src="resources/js/js.htm"></script>
    <!-- 返回顶部调用 end-->
</head>
<body>
<header>
    <div class="logo f_l"><a href="/"><img src="resources/img/logo.jpg"></a></div>
    <nav id="topnav" class="f_r">
        <ul>
            <a id="topnav_current" href="/" target="_blank">首页</a>
            <a href="#" target="_blank">关于我</a>
            <a href="#" target="_blank">文章</a>
            <a href="#" target="_blank">心情</a>
            <a href="#" target="_blank">相册</a>
            <a href="#" target="_blank">留言</a>
        </ul>
        <script src="resources/js/nav.js"></script>
    </nav>
</header>
<article>
    <div class="l_box f_l">
        <div class="banner">
            <div id="slide-holder">
                <!-- 滚动图片 -->
                <div id="slide-runner">
                    <a href="#" target="_blank">
                        <img style="left: -3000px;" id="slide-img-1" src="resources/img/a1.jpg" alt=""/>
                    </a>
                    <a href="#" target="_blank">
                        <img style="left: -2000px;" id="slide-img-2" src="resources/img/a2.jpg" alt=""/>
                    </a>
                    <a href="#" target="_blank">
                        <img style="left: -1000px;" id="slide-img-3" src="resources/img/a3.jpg" alt=""/>
                    </a>
                    <a href="#" target="_blank">
                        <img style="left: 0px;" id="slide-img-4" src="resources/img/a4.jpg" alt=""/>
                    </a>

                    <div id="slide-controls">
                        <p id="slide-client" class="text"><strong></strong><span></span></p>

                        <p id="slide-desc" class="text"></p>

                        <p id="slide-nav"></p>
                    </div>
                </div>
            </div>
            <!-- 滚动图片切换 -->
            <script>
                if (!window.slider) {
                    var slider = {};
                }

                slider.data = [
                    {
                        "id": "slide-img-1",
                        "client": "",
                        "desc": ""
                    },
                    {
                        "id": "slide-img-2",
                        "client": "",
                        "desc": ""
                    },
                    {
                        "id": "slide-img-3",
                        "client": "",
                        "desc": ""
                    },
                    {
                        "id": "slide-img-4",
                        "client": "",
                        "desc": ""
                    }
                ];

            </script>
        </div>

        <!-- 主题内容模块 -->
        <div class="topnews">
            <h2>
                <span>
                    <a href="#" target="_blank">技术</a>
                    <a href="#" target="_blank">生活</a>
                    <a href="#" target="_blank">职场</a>
                </span>
                <b>文章</b>推荐
            </h2>
        <#list articles as article>
            <div class="blogs">
                <figure><img src="${article.icon}"></figure>
                <ul>
                    <h3><a href="article_${article.id}.html" target="_blank">${article.subject}</a></h3>
                    <p>
                    ${article.summary}...
                    </p>
                    <p class="autor">
                        <span class="lm f_l"><a href="/">${article.username}</a></span>
                        <span class="dtime f_l">${article.create_date}</span>
                        <span class="viewnum f_r">浏览（${article.access_times}）</span>
                        <span class="pingl f_r">评论（${article.comment_times}）</span>
                    </p>
                </ul>
            </div>
        </#list>
        </div>
    </div>

    <div class="r_box f_r">
        <!-- 关注模块 -->
        <div class="tit01">
            <h3>关注我</h3>

            <div class="gzwm">
                <ul>
                    <li><a class="xlwb" href="#" target="_blank">新浪微博</a></li>
                    <li><a class="txwb" href="#" target="_blank">腾讯微博</a></li>
                    <li><a class="rss" href="#" target="_blank">RSS</a>
                    </li>
                    <li><a class="wx" href="#" target="_blank">邮箱</a></li>
                </ul>
            </div>
        </div>

        <!-- 排行榜模块 -->
        <div class="ad300x100"><img src="resources/img/ad300x100.jpg"></div>
        <div class="moreSelect" id="lp_right_select">
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
            <div class="ms-top">
                <ul class="hd" id="tab">
                    <li class="cur"><a href="/">点击排行</a></li>
                    <li><a href="/">最新文章</a></li>
                    <li><a href="/">站长推荐</a></li>
                </ul>
            </div>
            <div class="ms-main" id="ms-main">
                <div style="display: block;" class="bd bd-news">
                    <ul>
                    <#list accessCharts as article>
                        <li><a href="acticle_${article.id}.html" target="_blank">${article.subject}</a></li>
                    </#list>
                    </ul>
                </div>
                <div class="bd bd-news">
                    <ul>
                    <#list newCharts as article>
                        <li><a href="acticle_${article.id}.html" target="_blank">${article.subject}</a></li>
                    </#list>
                    </ul>
                </div>
                <div class="bd bd-news">
                    <ul>
                    <#list recommendCharts as article>
                        <li><a href="acticle_${article.id}.html" target="_blank">${article.subject}</a></li>
                    </#list>
                    </ul>
                </div>
            </div>
        </div>
        <!--切换卡 moreSelect end -->

        <!-- 标签模块 -->
        <div class="cloud">
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
                <li><a href="article_${article.id}.html" target="_blank"><img src="${article.icon}"><b>${article.subject}</b></a>

                    <p><span class="tulanmu"><a href="/">${article.username}</a></span><span
                            class="tutime">${article.create_date}</span>
                    </p>
                </li>
            </#list>
            </ul>
        </div>

        <!-- 链接模块 -->
        <div class="ad"><img src="resources/img/03.jpg"></div>
        <div class="links">
            <h3><span>[<a href="#">申请友情链接</a>]</span>友情链接</h3>
            <ul>
                <li><a href="http://www.cnblogs.com/zuoxiaolong">Zeus博客园</a></li>
            </ul>
        </div>
    </div>
</article>
<footer>
    <p class="ft-copyright">Zeus博客</p>
    <div id="tbox"><a id="togbook" href="#"></a> <a id="gotop" href="javascript:void(0)"></a></div>
</footer>
</body>
</html>