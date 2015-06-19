	<script type="application/javascript">
		$(document).ready(function(){
            $("#upload_image_a").click(function(){
                $('#upload_image_div').show();
                $('#improve_profile_table').hide();
                $('#update_password_table').hide();
            });
			$("#improve_profile_a").click(function(){
				$('#improve_profile_table').show();
				$('#update_password_table').hide();
				$('#upload_image_div').hide();
			});
			$("#update_password_a").click(function(){
				$('#update_password_table').show();
				$('#improve_profile_table').hide();
                $('#upload_image_div').hide();
			});
			$("input[name=imageFile]").change(function(){
				$("#file_path").val($(this).val());
			});
			$("#province_select").change(function(){
				if (!$(this).val()) {
					$("#city_select").html("<option value=''>请选择</option>");
					return;
				}
				$.ajax({
					url:"${contextPath}/getCities.do?province=" + encodeURI($(this).val()),
					type:"POST",
					success:function(data) {
						var options = "<option value=''>请选择</option>";
						for (var i = 0; i < data.length ; i++) {
                            options = options + "<option value='" + data[i].name + "'>" + data[i].name + "</option>";
						}
						$("#city_select").html(options);
					}
				});
			});
			$("#upload_image_form").ajaxForm({
				beforeSubmit:function(){
					if(!$("input[name=imageFile]").val()) {
						alert("请选择图片");
						return false;
					}
					return true;
				},
				success:function(url){
                    if (url && url == 'format_error') {
                        alert("只能上传png,jpg,gif格式的文件");
                        return;
                    }
					if(url) {
						$.ajax({
							url:"${contextPath}/updateImagePath.do",
							type:"POST",
							data:{"imagePath":url},
							success:function(data) {
								if (data && data == 'success') {
									alert("上传成功");
									$("#image_div").html('<img src="' + url + '" height="100" width="100">');
								}
							}
						});
					} else {
						alert("上传失败");
					}
				}
			});
			$("#improve_profile_form").ajaxForm({
				beforeSubmit:function(){
					if(!$("#province_select").val()) {
						alert("请选择省份");
						return false;
					}
					if(!$("#city_select").val()) {
						alert("请选择城市");
						return false;
					}
					if(!$("#language_select").val()) {
						alert("请选择语言");
						return false;
					}
					return true;
				},
				success:function(data){
					if(data && data == 'success') {
						alert("更新资料成功");	
					} else {
						alert(data);	
					}
				}
			});
			$("#update_password_form").ajaxForm({
				beforeSubmit:function(){
					if(!$("input[name=originPassword]").val()) {
						alert("请填写原密码");
						return false;
					}
					if(!$("input[name=newPassword]").val()) {
						alert("请填写新密码");
						return false;
					}
					if(!$("input[name=repeatNewPassword]").val()) {
						alert("请确认新密码");
						return false;
					}
					if($("input[name=newPassword]").val() != $("input[name=repeatNewPassword]").val()) {
						alert("两次密码不一致");
						return false;
					}
					return true;
				},
				success:function(data){
					if(data && data == 'success') {
						alert("更新密码成功");	
						$("input[name=originPassword]").val('');
						$("input[name=newPassword]").val('');
						$("input[name=repeatNewPassword]").val('');
					} else {
						alert(data);	
					}
				}
			});
		});
	</script>
	<nav class="menu">
		<h3 class="menu-heading">
		  用户中心
		</h3>
		<a id="upload_image_a" class="menu-item" href="javascript:void(0)">上传头像</a>
		<a id="improve_profile_a" class="menu-item" href="javascript:void(0)">完善资料</a>
		<a id="update_password_a" class="menu-item" href="javascript:void(0)">修改密码</a>
    </nav>

	<div id="upload_image_div" class="form_table float_left" >
		<div id="image_div" class="float_left" style="width: 100px;height: 100px;border: 1px solid #d5d5d5;margin: 5px;">
			<#if user.imagePath??>
                <img src="${user.imagePath}" height="100" width="100">
			<#else>
                <img src="" height="100" width="100">
			</#if>
		</div>
        <table class="float_left" style="width: 340px;height: 90px;border: 1px solid #d5d5d5;margin: 5px;">
            <form id="upload_image_form" method="POST" action="${contextPath}/uploadImage.do" enctype="multipart/form-data">
                <tr>
                    <td class="form_input">
                        <a class="file_input_a" href="#">
                            选择图片
                            <input class="file_input" type="file" name="imageFile" />
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="form_input">
                        <input type="text" class="text_input" id="file_path" readonly="readonly" style="width:340px;max-width: 340px;"/>
                    </td>
                </tr>
                <tr>
                    <td class="form_input">
                        <input type="submit" class="form_button" value="上传"/>
                    </td>
                </tr>
            </form>
        </table>
	</div>


	<table id="improve_profile_table" class="form_table float_left" style="display:none">
	<form id="improve_profile_form" method="POST" action="${contextPath}/saveProfile.do">
		<tr>
			<td class="form_info">省份：</td>
			<td class="form_input">
				<select id="province_select" name="province">
					<option value="">请选择</option>
					<#list provinces as province>
						<option value="${province.name}"
						<#if user?? && user.province?? && user.province == province.name>
						selected="selected"
						</#if>
						>
						${province.name}
						</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info">城市：</td>
			<td class="form_input">
				<select id="city_select" name="city">
					<option value="">请选择</option>
					<#if cities??>
						<#list cities as city>
							<option value="${city.name}"
							<#if user?? && user.city?? && user.city == city.name>
							selected="selected"
							</#if>
							>
							${city.name}
							</option>
						</#list>
					</#if>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info">语言：</td>
			<td class="form_input">
				<select id="language_select" name="languageId">
					<option value="">请选择</option>
					<#list languages as language>
						<option value="${language.id}"
						<#if user?? && user.languageId?? && user.languageId == language.id>
						selected="selected"
						</#if>
						>
						${language.name}
						</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info" >&nbsp;</td>
			<td class="form_input">
				<input type="submit" class="form_button" value="保存"/>
			</td>
		</tr>
	</form>
	</table>


	<table id="update_password_table" class="form_table float_left" style="display:none">
	<form id="update_password_form" method="POST" action="${contextPath}/updatePassword.do">
		<tr>
			<td class="form_info">原密码：</td>
			<td class="form_input"><input class="text_input" type="password" name="originPassword"/></td>
		</tr>
		<tr>
			<td class="form_info">新密码：</td>
			<td class="form_input"><input class="text_input"  type="password" name="newPassword"/></td>
		</tr>
		<tr>
			<td class="form_info">确认新密码：</td>
			<td class="form_input"><input class="text_input"  type="password" name="repeatNewPassword"/></td>
		</tr>
		<tr>
			<td class="form_info" >&nbsp;</td>
			<td class="form_input">
				<input type="submit" class="form_button" value="保存"/>
			</td>
		</tr>
	</form>
	</table>
