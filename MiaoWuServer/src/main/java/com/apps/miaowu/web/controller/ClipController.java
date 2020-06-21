package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("clips")
public class ClipController {

    @Resource
    ClipService ClipService;

    @Resource
    ArticleService articleService;

    @NoneAuth
    @GetMapping(value = "{userId}")
    public APIResult getClipArticle(Long userId){
        return articleService.findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);
    }

    @PostMapping(value = "")
    public APIResult addClipArticle(Long userId, Long articleId){
        return ClipService.addClipArticle(userId, articleId);
    }

    @DeleteMapping(value = "")
    public APIResult deleteClipArticle(Long userId, Long articleId){
        return ClipService.deleteClipArticle(userId, articleId);
    }
}
