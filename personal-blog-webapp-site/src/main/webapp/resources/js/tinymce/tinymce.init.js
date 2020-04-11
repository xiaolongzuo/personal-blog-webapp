function tinymceInit(settings) {
	$(document).ready(function() {
		var defaultSettings = {width:600,height:400,content:'',skin:'lightgray'};
		$.extend(defaultSettings,settings);
	    tinymce.init({
	        selector: "textarea.html_editor",
	        language: "zh_CN",
	        menubar : false,
	        skin: defaultSettings.skin,
	        width: defaultSettings.width,
	        height: defaultSettings.height,
	        toolbar_items_size:'small',
	        setup: function(editor) {
	            editor.addButton('upload',
	            {
	                icon: 'print',
					title: '上传本地图片',
	                onclick: function() {
	                    editor.windowManager.open({
	                        title: "上传本地图片",
	                        url: contextPath + "/html/upload_image.html",
	                        width: 400,
	                        height: 150
	                    });
	                }
	            });
	            editor.addButton('insertcode',
				{
					icon: 'paste',
					title: '插入代码',
					onclick: function() {
						editor.windowManager.open({
							title: "插入代码",
							url: contextPath + "/html/insert_code.html",
							width: 800,
							height: 400
						});
					}
				});
				editor.on('init', function(e) {
		            editor.dom.addStyle('pre {border:1px solid #aaa;padding:5px;line-height:15px;background-color:#FFD700;} ');
		            editor.dom.addStyle('blockquote {border: 1px solid #aaa;padding: 0px;} ');
		            if (defaultSettings.content) {
		            	editor.setContent(defaultSettings.content);
		            }
		        });
	        },
	        plugins: [
	            "advlist autolink lists link image charmap print preview anchor textcolor",
	            "searchreplace visualblocks code fullscreen",
	            "insertdatetime media table contextmenu paste emoticons"
	        ],
	        toolbar: "undo redo | styleselect bold italic forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | code preview fullscreen | link upload image insertcode table blockquote media emoticons"
	    });
	});
}