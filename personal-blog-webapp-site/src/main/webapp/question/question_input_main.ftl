	<script type="application/javascript">
		tinymceInit({width:600,height:300});
		$(document).ready(function(){
            $("#submitButton").click(function(){
                if (!$("input[name=title]").val()) {
                    alert("标题不能为空");
                    return false;
                }
                if ($("input[name=title]").val().length > 30) {
                    alert("标题最长为30个字符");
                    return false;
                }
                var description = tinymce.activeEditor.getContent();
                if (!description) {
                    description = '';
                }
                if (description && description.length > 1500) {
                    alert("标题最长为1500个字符");
                    return false;
                }
                $.ajax({
                    url: "${contextPath}/ask.do",
                    type: "POST",
                    data: {title:$("input[name=title]").val(),description:description},
                    success: function(data) {
                        if (data && data == 'success') {
                            alert("提问成功");
                            window.location.href = "${contextPath}/question/question_index.ftl";
                        } else {
                            alert("提问失败，请联系站长");
						}
                    }
                });
            });
		});
	</script>
	<table>
		<tr>
			<td class="form_info">问题：</td>
			<td>
				<input class="text_input" type="text" name="title" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<td valign="top" class="form_info">描述：</td>
			<td>
				<textarea class="html_editor" name="description"></textarea>
			</td>
		</tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <input class="button" style="margin:10px 0px;" id="submitButton" type="button" value="提问"/>
            </td>
        </tr>
	</table>