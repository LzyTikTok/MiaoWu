package com.apps.miaowu.service;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;

public interface CommentService {
    APIResult addComment(Long userId, Long articleId, String content);

    APIResult deleteComment(User user, Long commentId);

    APIResult updateComment(User user, Comment comment);
}
