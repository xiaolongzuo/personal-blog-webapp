	<script type="text/javascript">
	$(document).ready(function(){
	    tinymce.init({
	        selector: "textarea",
	        language: "zh_CN",
	        width: 600,
	        height: 400,
	        setup: function(editor) {
	            editor.addButton('example',
	            {
	                title: 'My title',
	                onclick: function() {
	                    editor.windowManager.open({
	                        title: "上传/插入图片", 
	                        url: "${contextPath}/resources/js/tinymce/file/upload_image.html",
	                        width: 400,
	                        height: 150
	                    });
	                }
	            });
	        },
	        plugins: [
	            "advlist autolink lists link image charmap print preview anchor",
	            "searchreplace visualblocks code fullscreen",
	            "insertdatetime media table contextmenu paste"
	        ],
	        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | code | example"
	    });
	});
	</script>
	<table>
		<tr>
			<td>标题：</td>
			<td>
				<input class="text_input" type="text" name="title" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<td>描述：</td>
			<td>
				<textarea name="description"></textarea>
			</td>
		</tr>
	</table>