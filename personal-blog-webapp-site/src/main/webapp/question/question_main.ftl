    <!-- 主题内容模块 -->
    <div class="index_about">
        <h2 class="c_titile">${question.title}</h2>

        <p class="box_c"><span class="d_time">提问时间：${question.create_date}</span><span>提问者：<a
                href="#">${question.username}</a></span><span>阅读（${question.access_times}
            ）</span><span>回答数（${question.answer_number}）</span></p>
        <ul class="infos">
        ${question.description} <br/>
        </ul>
        <div id="comments_pager_top"></div>
        <!-- 答案列表 -->
        <div class="feedback_area_title">答案列表（共<span id="comment_size"><#if answers??>${answers?size}<#else>0</#if></span>条答案）</div>
        <div class="feedbackNoItems"></div>
        <div class="feedbackItem" id="comment_list">
        <#if answers?? && answers?size gt 0 >
            <#list answers as answer>
			<div id="comment_div_${answer.id}" class="feedbackItem" <#if answer.is_solution??>style="background-color: #EEE0E5;"</#if>>
                <div class="feedbackListSubtitle">
                	<div class="feedbackManage">
                        <span class="comment_actions">
                            <a comment_id="${answer.id}" class="reply_button" href="javascript:void(0)">回复</a>
                            <a comment_id="${answer.id}" class="quite_button" href="javascript:void(0)">引用</a>
                        </span>
                        <#if answer.is_solution??>
                            <div class="float_right" style="height:30px;line-height:30px;color: black; font-family: 微软雅黑;font-size: 18px;">
                                提问者采纳<img class="float_right" src="${contextPath}/resources/img/common/solution.png" width="30" height="30">
                            </div>
                        </#if>
					</div>
                    <a href="javascript:void(0)" class="layer">#${answer_index + 1}楼</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    时间：<span class="comment_date">${answer.answer_date}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    来源：<a id="commenter_a_${answer.id}" href="javascript:void(0)">${answer.answerer}</a>
                </div>
                <div class="feedbackCon">
                    <div id="comment_content_${answer.id}" class="blog_comment_body">${answer.answer}</div>
					<div class="comment_vote">
                        <#if answer.show_solution_button??>
                            <a class="comment_solution_button" comment_id="${answer.id}" href="javascript:void(0)">采纳为答案</a>
                        </#if>
                        <a class="comment_remark_button" comment_id="${answer.id}" column="good_times" href="javascript:void(0)">支持(<span id="comment_good_span_${answer.id}">${answer.good_times?default(0)}</span>)</a>
						<a class="comment_remark_button" comment_id="${answer.id}" column="bad_times" href="javascript:void(0)">反对(<span id="comment_bad_span_${answer.id}">${answer.bad_times?default(0)}</span>)</a>
					</div>
                </div>
            </div>
            </#list>
        <#else>
            暂无答案
        </#if>
        </div>
		<script type="application/javascript">
			tinymceInit({width:700,height:150,skin:'comment'});
		</script>
        <!-- 提交评论 -->
        <div id="comment_container">
            <div id="comment_title">我要回答</div>
            <div class="commentbox_main">
            	<div id="reply_div" class="clear"></div>
				<input type="hidden" name="referenceCommentId" id="reference_comment_id_input"/>
				<input type="hidden" name="referenceCommenter" id="reference_commenter_input"/>
                <textarea name="content" id="comment_textarea" class="html_editor"></textarea>
            </div>
            <p id="commentbox_opt">
                <input id="submit_comment_button" class="comment_btn" value="提交答案" type="button">
            </p>
        </div>
    </div>
