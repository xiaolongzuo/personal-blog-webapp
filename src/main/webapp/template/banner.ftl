<div class="banner">
    <div id="slide-holder">
        <!-- 滚动图片 -->
        <div id="slide-runner">
            <a href="#">
                <img style="left: -3000px;" id="slide-img-1" src="resources/img/a1.jpg" alt=""/>
            </a>
            <a href="#">
                <img style="left: -2000px;" id="slide-img-2" src="resources/img/a2.jpg" alt=""/>
            </a>
            <a href="#">
                <img style="left: -1000px;" id="slide-img-3" src="resources/img/a3.jpg" alt=""/>
            </a>
            <a href="#">
                <img style="left: 0px;" id="slide-img-4" src="resources/img/a4.jpg" alt=""/>
            </a>

            <div id="slide-controls">
                <p id="slide-client" class="text"><strong></strong><span></span></p>

                <p id="slide-desc" class="text"></p>

                <p id="slide-nav"></p>
            </div>
        </div>
    </div>
    <!-- 滚动图片切换 -->
    <script>
        if (!window.slider) {
            var slider = {};
        }
        slider.data = [
            {
                "id": "slide-img-1",
                "client": "",
                "desc": ""
            },
            {
                "id": "slide-img-2",
                "client": "",
                "desc": ""
            },
            {
                "id": "slide-img-3",
                "client": "",
                "desc": ""
            },
            {
                "id": "slide-img-4",
                "client": "",
                "desc": ""
            }
        ];
    </script>
</div>