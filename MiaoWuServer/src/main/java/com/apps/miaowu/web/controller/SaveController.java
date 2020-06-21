package com.apps.miaowu.web.controller;


import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.SaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("saves")
public class SaveController {
    @Resource
    SaveService saveService;

    @PostMapping(value = "")
    public APIResult addOrDelFollow(Long userId, Long animalId){
        return saveService.addSave(userId, animalId);
    }
}
