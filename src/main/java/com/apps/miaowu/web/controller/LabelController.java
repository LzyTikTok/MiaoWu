package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.ArticleWithBLOBs;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.dao.extend.LabelMapperExtend;
import com.apps.miaowu.service.LabelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("Label")
public class LabelController{

    @Resource
    LabelService labelService;


    @PostMapping("addLabel")
    public APIResult addLabel(Long articleId, String[] labelNames){
        return labelService.addLabels(articleId,labelNames);
    }
}
