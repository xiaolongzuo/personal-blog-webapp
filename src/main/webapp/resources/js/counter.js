$(document).ready(function(){
    $.ajax({
        url:"counter",
        type:"POST",
        data:{"url":window.location.pathname,"column":"access_times"}
    });
    
    $("input[name=column]").click(function(){
    	var checked = $("input[name=column]:checked").val();
    	$.ajax({
        	url:"counter",
        	type:"POST",
            data:{"articleId":$("#articleId").val(),"column":checked}
        });
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