package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Clip;
import com.apps.miaowu.bean.ClipExample;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.ClipMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.service.ClipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClipServiceImpl implements ClipService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    ClipMapper clipMapper;

    @Override
    public APIResult addClipArticle(Long userId, Long articleId) {
        // todo 一些意外情况的处理
        Clip clip = new Clip();
        clip.setArticleId(articleId);
        clip.setUserId(userId);
        clipMapper.insert(clip);
        return APIResult.newResult(ResultCode.SuccessCode, "Clip article successfully", clip);
    }


    @Override
    public APIResult deleteClipArticle(Long userId, Long articleId) {
        // todo 一些意外情况的处理
        ClipExample example = new ClipExample();
        example.createCriteria().andArticleIdEqualTo(articleId).andUserIdEqualTo(userId);
        clipMapper.deleteByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Delete clip article successfully", null);
    }

    //todo 查询所有收藏的文章
    //todo 实现查找
    @Override
    public APIResult findAllClipArticleDesc(Long userId) {
        return null;
    }
    
}