<!DOCTYPE html>
<html>
<head lang="zh_CN">
    <#include "../common/meta.ftl">
	<#include "../common/head.ftl">
	<style type="text/css">
		body {overflow: hidden;}
	</style>
	<script type="application/javascript">
		$(document).ready(function(){
			$("input[name=imageFile]").change(function(){
				$("#file_path").val($(this).val());
			});
			$("#upload_image_form").ajaxForm({
				beforeSubmit:function(){
					if(!$("input[name=imageFile]").val()) {
						window.parent.alert("请选择图片");
						return false;
					}
					return true;
				},
				success:function(url){
					if (url && url == 'format_error') {
						alert("只能上传png,jpg,gif格式的文件");
						return;
					}
					if (url) {
						top.tinymce.activeEditor.insertContent("<img src='" + url + "'/>");
						top.tinymce.activeEditor.windowManager.close();
					}
				}
			});
		});
	</script>
    <title></title>
</head>
<body>
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
</body>
</html>