	<script type="application/javascript">
		function clear(){
			$("input[type=text]").val("");
			$("input[type=password]").val("");
			$("select").val("");
		}
		$(document).ready(function(){
			$("#improve_profile_a").click(function(){
				$('#improve_profile_table').show();
				$('#update_password_table').hide();
				clear();
			});
			$("#update_password_a").click(function(){
				$('#update_password_table').show();
				$('#improve_profile_table').hide();
				clear();
			});
		});
	</script>
	<nav class="menu">
	<h3 class="menu-heading">
      用户中心
    </h3>
    <a id="improve_profile_a" class="menu-item" href="javascript:void(0)">完善资料</a>
    <a id="update_password_a" class="menu-item" href="javascript:void(0)">修改密码</a>
    </nav>
	<table id="improve_profile_table" class="form_table float_left" >
	<form method="POST" action="${contextPath}/saveProfile.do" enctype="multipart/form-data">
		<tr>
			<td class="form_info" align="right">上传照片：</td>
			<td class="form_input">
          		<a class="file_input_a" href="#">
          			选择图片
				    <input class="file_input" type="file" name="imageFile" />
				</a>
			</td>
		</tr>
		<tr>
			<td class="form_info" align="right">省份：</td>
			<td class="form_input">
				<select name="provice">
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
			<td class="form_info" align="right">城市：</td>
			<td class="form_input">
				<select name="city">
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
			<td class="form_info" align="right">语言：</td>
			<td class="form_input">
				<select name="language">
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
	<form method="POST" action="${contextPath}/updatePassword.do">
		<tr>
			<td class="form_info" align="right">原密码：</td>
			<td class="form_input"><input class="text_input" type="password" name="originPassword"/></td>
		</tr>
		<tr>
			<td class="form_info" align="right">新密码：</td>
			<td class="form_input"><input class="text_input"  type="password" name="newPassword"/></td>
		</tr>
		<tr>
			<td class="form_info" align="right">确认新密码：</td>
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
