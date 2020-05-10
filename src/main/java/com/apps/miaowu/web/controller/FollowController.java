package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("follow")
public class FollowController {

    @Autowired
    FollowService followService;

    @PostMapping(value = "addOrDelFollow")
    public APIResult addOrDelFollow(Long userId, Long fansId){
        return followService.addOrDelFollow(userId, fansId);
    }

}
