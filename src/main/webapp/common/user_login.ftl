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
                	<a href="${contextPath}/common/user_center.ftl" class="button">用户中心</a>
                    <a id="logout_button" href="#" class="button">注销</a>
                </div>
            </div>
            <#else>
            <table id="login_table" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td style="font-size: 12px;">用户名：</td>
                    <td><input class="text_input" type="text" name="username"/></td>
                    <td><a id="login_register_button" href="#" class="button">登录&nbsp;|&nbsp;注册</a></td>
                </tr>
                <tr>
                    <td style="font-size: 12px;">密  码：</td>
                    <td><input class="text_input" type="password" name="password"/></td>
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