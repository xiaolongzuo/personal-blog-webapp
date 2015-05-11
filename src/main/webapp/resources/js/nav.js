$(document).ready(function(){
    $("#topnav a").each(function(){
        if (window.location.href.indexOf($(this).attr("href")) > 0) {
            $(this).attr("id","topnav_current");
        }
    });
});



