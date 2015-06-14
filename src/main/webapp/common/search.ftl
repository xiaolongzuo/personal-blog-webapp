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
        <h3>搜一搜</h3>
        <div id="site_search_input_div">
            <input type="text" name="searchText" id="search_text_input" value="${searchText?default('')}"/>
            <input type="button" class="button" value="站内搜索" id="site_search_button">
        </div>
    </div>
