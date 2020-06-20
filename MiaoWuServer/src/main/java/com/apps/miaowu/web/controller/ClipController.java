package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("clips")
public class ClipController {

    @Resource
    ClipService ClipService;

    @PostMapping(value = "")
    public APIResult addClipArticle(Long userId, Long articleId){
        return ClipService.addClipArticle(userId, articleId);
    }

    @DeleteMapping(value = "")
    public APIResult deleteClipArticle(Long userId, Long articleId){
        return ClipService.deleteClipArticle(userId, articleId);
    }
}
