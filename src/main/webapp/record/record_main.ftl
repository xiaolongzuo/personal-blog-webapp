    <!-- 主题内容模块 -->
    <div class="index_about">
        <h2 class="c_titile">${record.title}</h2>

        <p class="box_c"><span class="d_time">记录时间：${record.create_date}</span><span>作者：<a
                href="#">${record.username}</a></span><span>阅读（${record.access_times}
            ）</span><span><a id="good_a" href="javascript:void(0)">赞一下</a>（<span id="good_times_span">${record.good_times}</span>）</span></p>
        <ul class="infos">
        ${record.record} <br/>
        </ul>
    </div>
