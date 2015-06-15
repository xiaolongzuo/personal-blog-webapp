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
	<table class="float_left" style="width:100px;margin:30px 0px 30px 0px;">
		<tr>
			<td style="padding:10px;border-left:1px solid #000000;border-bottom:1px solid #000000">
			&nbsp;&nbsp;<a id="improve_profile_a" href="javascript:void(0)" style="font-size:15px;color:#dacf00">完善资料</a>
			</td>
		</tr>
		<tr>
			<td style="padding:10px;border-left:1px solid #000000;border-bottom:1px solid #000000">
			&nbsp;&nbsp;<a id="update_password_a" href="javascript:void(0)" style="font-size:15px;color:#dacf00">修改密码</a>
			</td>
		</tr>
	<table>
	<table id="improve_profile_table" class="form_table float_right" style="width:400px;">
		<tr>
			<td class="form_info" align="right">上传照片：</td>
			<td class="form_input"><input class="file_input" type="file" name="avatar"/></td>
		</tr>
		<tr>
			<td class="form_info" align="right">省份：</td>
			<td class="form_input">
				<select name="provice">
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info" align="right">城市：</td>
			<td class="form_input">
				<select name="city">
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info" align="right">语言：</td>
			<td class="form_input">
				<select name="provice">
					<option value="">请选择</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
					<option value="C">C</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="form_info" >&nbsp;</td>
			<td class="form_input">
				<input id="save_profile_button" type="button" class="form_button" value="保存"/>
			</td>
		</tr>
	</table>
	<table id="update_password_table" class="form_table float_right" style="width:400px;display:none;">
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
				<input id="update_password_button" type="button" class="button" value="保存"/>
			</td>
		</tr>
	</table>
