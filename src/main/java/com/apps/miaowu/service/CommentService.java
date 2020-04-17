package com.apps.miaowu.service;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;

public interface CommentService {
    APIResult addComment(User user, Comment comment);

    APIResult deleteComment(User user, Comment comment);

    APIResult updateComment(User user, Comment comment);
}
