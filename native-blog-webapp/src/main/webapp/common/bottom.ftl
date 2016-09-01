<script type="text/javascript" src="${contextPath}/resources/js/common/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/jquery-ui.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/jquery.form.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/sliders.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/common.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shCore.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushAppleScript.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushAS3.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushBash.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushColdFusion.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushCpp.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushCSharp.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushCss.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushDelphi.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushDiff.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushErlang.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushGroovy.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushJava.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushJScript.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushJavaFX.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushPerl.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushPhp.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushPlain.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushJava.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushPowerShell.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushPython.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushRuby.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushSass.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushScala.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushSql.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushVb.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/shbrush/shBrushXml.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common/forbidden.backspace.js"></script>
<script type="text/javascript">
    SyntaxHighlighter.all();
    var contextPath = "${contextPath}";
</script>
<script type="text/javascript" src="${contextPath}/resources/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/tinymce/tinymce.init.js"></script>
<script type="application/javascript">
    window.onload = function () {
        var tabElement = document.getElementById("tab");
        var msMainElement = document.getElementById("ms-main");
        if (!tabElement || !msMainElement) {
            return;
        }
        var liElement = tabElement.getElementsByTagName("li");
        var divElement = msMainElement.getElementsByTagName("div");

        for (var i = 0; i < liElement.length; i++) {
            liElement[i].index = i;
            liElement[i].onmouseover = function () {
                for (var n = 0; n < liElement.length; n++) liElement[n].className = "";
                this.className = "cur";
                for (var n = 0; n < divElement.length; n++) divElement[n].style.display = "none";
                divElement[this.index].style.display = "block";
            }
        }
    };
    $(document).ready(function(){
        $("#site_search_button").click(function() {
            if (!$("#search_text_input").val().trim()) {
                alert("搜索内容不能为空！");
                return false;
            }
            searchArticles("searchText",$("#search_text_input").val());
        });
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
                        window.location.href=data.url;
                    } else {
                        $("#login_error_td").css("color","red");
                        $("#login_error_td").html(data);
                    }
                }
            });
        });
    });
    <!-- 滚动图片切换 -->
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