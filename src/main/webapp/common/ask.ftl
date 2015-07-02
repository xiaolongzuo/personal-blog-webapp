    <script type="application/javascript">
    $(document).ready(function(){
    	$("#ask_button").click(function() {
            if (isLogin()) {
                window.location.href="${contextPath}/question/question_input.ftl";
            } else {
                alert("请先在右侧登录以后才能提问");
            }
	    });
        $("#answer_search_button").click(function(){
            var searchText = $("#search_text_input").val();
            if (!searchText || !$.trim(searchText)) {
                alert("请输入搜索内容");
                return false;
            }
            var url = contextPath + '/question/question_list.ftl?current=1';
            url = url + '&searchText=' + encodeURI(searchText);
            window.location.href=url;
        });
    });
	</script>
    <div class="answer_search_div">
        <h3>搜索答案</h3>
        <div id="ask_input_div">
            <input type="button" class="form_button" value="我 要 提 问" id="ask_button" style="padding:0px 0px;width:200px;height:30px;line-height:30px;margin:10px 0px 0px 10px;" >
        </div>
        <div id="answer_search_input_div">
            <input class="text_input" type="text" name="searchText" id="search_text_input" value="${searchText?default('')}"/>
            <input style="padding:0px 10px;" type="button" class="form_button" value="搜索答案" id="answer_search_button">
        </div>
    </div>
