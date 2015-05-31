$(document).ready(function() {
	var articleId = $("#articleId").val();
    $.ajax({
        url:contextPath + "/counter.do",
        type:"POST",
        data:{"articleId":articleId,"column":"access_times"}
    });
});