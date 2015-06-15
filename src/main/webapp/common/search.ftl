    <div class="site_search_div">
        <script type="application/javascript">
            $(document).ready(function(){
                $("#site_search_button").click(function() {
                    if (!$("#search_text_input").val().trim()) {
                        alert("搜索内容不能为空！");
                        return false;
                    }
                    searchArticles("searchText",$("#search_text_input").val());
                });
            });
        </script>
        <h3>站内搜索</h3>
        <div id="site_search_input_div">
            <input class="text_input" type="text" name="searchText" id="search_text_input" value="${searchText?default('')}"/>
            <input style="padding:0px 10px;" type="button" class="form_button" value="搜 索" id="site_search_button">
        </div>
    </div>
