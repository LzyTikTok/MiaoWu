package com.apps.miaowu.service;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;

public interface CommentService {
    APIResult AddComment(User user, Comment comment);

    APIResult DeleteComment(User user, Comment comment);

    APIResult UpdateComment(User user, Comment comment);
}
