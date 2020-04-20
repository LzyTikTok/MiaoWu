package com.apps.miaowu.web.controller;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("thumbUp")
public class ThumbUpController {

    @Autowired
    ThumbUpService thumbUpService;

    @PostMapping(value = "thumbUpOrDown")
    APIResult thumbUpOrDown(Long articleId, Long user_id){
        return thumbUpService.thumbUpOrDown(articleId,user_id);
    }
}
