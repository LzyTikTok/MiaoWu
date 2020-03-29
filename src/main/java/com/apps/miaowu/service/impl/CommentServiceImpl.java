package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
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
    public APIResult AddComment(User user, Comment comment) {
        //默认只要没有身份证号码则没有完善个人信息
        //
        //则不能评论
        if(user.getIdcode() == null){
            return APIResult.newResult(ResultCode.BadRequest,"Parameters are missing",null);
//            return "完善个人信息后才能评论哦~";
        }
        else {
            commentMapper.insert(comment);
            return APIResult.newResult(ResultCode.SuccessCode,"Add comment successfully",null);
//            return "评论成功~";
        }
    }

    @Override
    public APIResult DeleteComment(User user, Comment comment) {
        if (commentMapper.selectByPrimaryKey(comment.getId()) != null){
            commentMapper.deleteByPrimaryKey(comment.getId());
            return APIResult.newResult(ResultCode.SuccessCode,"Delete successfully",null);
        }
        else {
            return APIResult.newResult(ResultCode.BadRequest,"can't not find the comment",null);
        }
    }

    @Override
    public APIResult UpdateComment(User user, Comment comment) {
        if (commentMapper.selectByPrimaryKey(comment.getId()) != null){
            commentMapper.updateByPrimaryKey(comment);
            return APIResult.newResult(ResultCode.SuccessCode,"Update successfully",null);
        }
        else {
            return APIResult.newResult(ResultCode.BadRequest,"can't not find the comment",null);
        }
    }
}
