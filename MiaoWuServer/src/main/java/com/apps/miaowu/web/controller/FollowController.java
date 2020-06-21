package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.FollowService;
import com.apps.miaowu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("follows")
public class FollowController {

    @Resource
    FollowService followService;

    @GetMapping(value = "{userId}")
    public APIResult getFollowByUserId(@PathVariable Long userId){
        return followService.findAllFollowsByUserId(userId);
    }

    @GetMapping(value = "/fans/{userId}")
    public APIResult getFansByUserId(@PathVariable Long userId){
        return followService.findAllFansByUserId(userId);
    }

    @PostMapping(value = "")
    public APIResult addOrDelFollow(Long userId, Long fansId){
        return followService.addOrDelFollow(userId, fansId);
    }

}
