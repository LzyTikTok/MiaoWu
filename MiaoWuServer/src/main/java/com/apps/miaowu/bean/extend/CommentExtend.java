package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;

public class CommentExtend extends Comment {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
