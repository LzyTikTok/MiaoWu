package com.apps.miaowu.service;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;

public interface CommentService {
    String AddComment(User user, Comment comment);

    String DeleteComment(User user, Comment comment);

    String UpdateComment(User user, Comment comment);
}
