package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "addComment")
    public APIResult addComment(Long userId, Long articleId, String comment){
        return commentService.addComment(userId, articleId, comment);
    }

    @PostMapping(value = "deleteComment")
    public APIResult deleteComment(User user, Long commentId){
        return commentService.deleteComment(user, commentId);
    }

    @PostMapping(value = "updateComment")
    public APIResult updateComment(User user, Comment comment){
        return commentService.updateComment(user, comment);
    }
}