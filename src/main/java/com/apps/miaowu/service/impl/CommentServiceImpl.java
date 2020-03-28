package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.CommentMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.service.CommentService;

import javax.annotation.Resource;

public class CommentServiceImpl implements CommentService {
    @Resource
    UserMapper userMapper;

    @Resource
    ArticleMapper articleMapper;

    @Resource
    CommentMapper commentMapper;

    @Override
    public String AddComment(User user, Comment comment) {
        //默认只要没有身份证号码则没有完善个人信息
        //
        //则不能评论
        if(user.getIdcode() == null){
            return "完善个人信息后才能评论哦~";
        }
        else {
            commentMapper.insert(comment);
            return "评论成功~";
        }
    }

    @Override
    public String DeleteComment(User user, Comment comment) {
        if (commentMapper.selectByPrimaryKey(comment.getId()) != null){
            commentMapper.deleteByPrimaryKey(comment.getId());
            return "删除成功";
        }
        else {
            return "未找到该条评论";
        }
    }

    @Override
    public String UpdateComment(User user, Comment comment) {
        if (commentMapper.selectByPrimaryKey(comment.getId()) != null){
            commentMapper.updateByPrimaryKey(comment);
            return "更新成功";
        }
        else {
            return "未找到该条评论";
        }
    }
}
