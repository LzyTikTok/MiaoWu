package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Comment;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.CommentMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.service.CommentService;
import com.apps.miaowu.utils.MiaoWuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public APIResult addComment(User user, String content) {
        //默认只要没有身份证号码则没有完善个人信息
        //
        //则不能评论
        if (user.getIdCode() == null) {
            return APIResult.newResult(ResultCode.BadRequest, "Parameters are missing", null);
//            return "完善个人信息后才能评论哦~";
        } else {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUserId(user.getId());

//            comment.setDate(LocalDateTime.now());
            comment.setDate(new Date());

            commentMapper.insert(comment);
            return APIResult.newResult(ResultCode.SuccessCode, "Add comment successfully", null);
        }
    }

    @Override
    public APIResult deleteComment(User user, Long commentId) {
        if (commentMapper.selectByPrimaryKey(commentId) != null) {
            commentMapper.deleteByPrimaryKey(commentId);
            return APIResult.newResult(ResultCode.SuccessCode, "Delete successfully", null);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "can't not find the comment", null);
        }
    }

    @Override
    public APIResult updateComment(User user, Comment comment) {
        if (commentMapper.selectByPrimaryKey(comment.getId()) != null) {
            commentMapper.updateByPrimaryKey(comment);
            return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "can't not find the comment", null);
        }
    }
}
