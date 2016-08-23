    <div class="new_comments">
        <h3>最新评论</h3>
        <ul>
            <#list newComments as comment>
                <li>
                    <div class="new_comments_div">
                        <div>
                            <a href="${comment.articleUrl}">${comment_index + 1}.《${comment.articleSubject}》</a>
                        </div>
                        <div class="new_comments_comment_body">
                            ${comment.shortContent}
                        </div>
                        <div>
                            <p class="new_comments_author">----${comment.commenter}</p>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                </li>
            </#list>
        </ul>
    </div>