package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clip")
public class ClipController {

    @Autowired
    ClipService ClipService;

    @PostMapping(value = "addClipArticle")
    public APIResult addClipArticle(Long userId, Long articleId){
        return ClipService.addClipArticle(userId, articleId);
    }

    @PostMapping(value = "deleteClipArticle")
    public APIResult deleteClipArticle(Long userId, Long articleId){
        return ClipService.deleteClipArticle(userId, articleId);
    }
}
