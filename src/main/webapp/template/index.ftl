<!DOCTYPE html>
<html>
<head>
<#include "head.ftl">
</head>
<body>
<#include "header.ftl">
<article>
    <div class="l_box f_l">
        <div class="banner">
            <div id="slide-holder">
                <!-- 滚动图片 -->
                <div id="slide-runner">
                    <a href="#">
                        <img style="left: -3000px;" id="slide-img-1" src="resources/img/a1.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img style="left: -2000px;" id="slide-img-2" src="resources/img/a2.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img style="left: -1000px;" id="slide-img-3" src="resources/img/a3.jpg" alt=""/>
                    </a>
                    <a href="#">
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
                    <a href="#" >技术</a>
                    <a href="#" >生活</a>
                    <a href="#" >职场</a>
                </span>
                <b>文章</b>推荐
            </h2>
        <#if articles??>
        <#list articles as article>
            <#if article_index gt 10>
                <#break />
            </#if>
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
        </#if>
        </div>
    </div>
    <#include "right.ftl">
</article>
<#include "footer.ftl">
</body>
</html>