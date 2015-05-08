$(document).ready(function(){
    $.ajax({
        url:"counter",
        type:"POST",
        data:{"url":window.location.pathname,"column":"access_times"}
    });
});