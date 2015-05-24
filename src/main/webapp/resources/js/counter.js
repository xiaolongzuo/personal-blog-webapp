$(document).ready(function() {
	var articleId = $("#articleId").val();
    $.ajax({
        url:contextPath + "/counter.do",
        type:"POST",
        data:{"articleId":articleId,"column":"access_times"}
    });
    
    $("input[name=column]").click(function(){
    	var checked = $("input[name=column]:checked").val();
    	var result = null;
    	$.ajax({
        	url:contextPath + "/counter.do",
        	async: false,
        	type:"POST",
            data:{"articleId":articleId,"column":checked},
            success:function(data){
            	result = data;
            	if(data && data == 'exists') {
            		alert("您已经评价过啦，亲！");
            	}
            }
        });
    	if(!result || result != 'success') {
    		return;
    	}
    	var max = 10;
    	$("input[name=column]").each(function(){
    		var times = parseInt($("#" + $(this).val() + "_span").html());
    		if($(this).val() == checked ) {
    			times = times + 1;
    			$("#" + $(this).val() + "_span").html(times);
    		}
    		if (times > max) {
                max = times;
            }
    	});
    	$("input[name=column]").each(function(){
    		var times = parseInt($("#" + $(this).val() + "_span").html());
    		$("#" + $(this).val() + "_img").attr("height",times * 50 / max);
    	});
    });
});