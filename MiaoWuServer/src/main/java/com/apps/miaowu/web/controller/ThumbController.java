package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value="thumbs")
public class ThumbController {

    @Resource
    ArticleService articleService;

    @PostMapping(value = "")
    APIResult thumbUpOrDown(Long articleId, Long userId) {
        return articleService.thumbUpOrDown(articleId, userId);
    }
}
