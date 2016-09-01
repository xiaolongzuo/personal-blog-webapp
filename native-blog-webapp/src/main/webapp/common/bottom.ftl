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
        var oLi = document.getElementById("tab").getElementsByTagName("li");
        var oUl = document.getElementById("ms-main").getElementsByTagName("div");

        for (var i = 0; i < oLi.length; i++) {
            oLi[i].index = i;
            oLi[i].onmouseover = function () {
                for (var n = 0; n < oLi.length; n++) oLi[n].className = "";
                this.className = "cur";
                for (var n = 0; n < oUl.length; n++) oUl[n].style.display = "none";
                oUl[this.index].style.display = "block";
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
</script>