package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "findAll")
    public APIResult findAll() {
        return userService.findAll();
    }

//    @GetMapping(value = )


    @PostMapping(value = "saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "name", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "idcode", value = "", required = false),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
            @ApiImplicitParam(name = "contry_id", value = "", required = true),
            @ApiImplicitParam(name = "phone", value = "", required = true),
            @ApiImplicitParam(name = "create_date", value = "", required = false),
            @ApiImplicitParam(name = "birthday", value = "", required = false),
            @ApiImplicitParam(name = "password", value = "", required = false)
    })
    public APIResult saveOrUpdate(User user) {
        return userService.saveOrUpdate(user);
    }

    @PostMapping(value = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "name", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "idcode", value = "", required = false),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
            @ApiImplicitParam(name = "contry_id", value = "", required = false),
            @ApiImplicitParam(name = "phone", value = "", required = true),
            @ApiImplicitParam(name = "create_date", value = "", required = false),
            @ApiImplicitParam(name = "birthday", value = "", required = false),
            @ApiImplicitParam(name = "password", value = "", required = true)
    })
    public APIResult login(User user) {
        return userService.login(user);
    }

    @GetMapping(value = "findById")
    public APIResult findById(Long id){
        return userService.findById(id);
    }

    @GetMapping(value = "findAllUserWithFound")
    public APIResult findAllUserWithFound(){
        return userService.findAllUserWithFound();
    }

    @GetMapping(value = "findAllUserWithFoundById")
    public APIResult findAllUserWithFoundById(Long id){
        return userService.findUserWithFoundById(id);
    }

    @GetMapping(value = "findAllUserWithSave")
    public APIResult findAllUserWithSave(){
        return userService.findAllUserWithSave();
    }

    @GetMapping(value = "findUserWithSaveById")
    public APIResult findUserWithSaveById(Long id){
        return userService.findUserWithSaveById(id);
    }

    @GetMapping(value = "cascadeFindAllUser")
    public APIResult cascadeFindAllUser(){
        return userService.cascadeFindAllUser();
    }

    @GetMapping(value = "cascadeFindUserById")
    public APIResult cascadeFindUserById(Long id){
        return userService.cascadeFindUserById(id);
    }

    @DeleteMapping(value = "deleteUserById")
    public APIResult deleteById(long id){return userService.deleteUserById(id);}

    @PostMapping(value = "addClipArticle")
    public APIResult addClipArticle(Long userId, Long articleId){
     return userService.addClipArticle(userId, articleId);
    }

    @PostMapping(value = "deleteClipArticle")
    public APIResult deleteClipArticle(Long userId, Long articleId){
        return userService.deleteClipArticle(userId, articleId);
    }

    @PostMapping(value = "addFollow")
    public APIResult addFollow(Long userId, Long followerId){
        return userService.addFollow(userId, followerId);
    }

    @PostMapping(value = "deleteFollow")
    public APIResult deleteFollow(Long userId, Long followerId){
        return userService.deleteFollow(userId, followerId);
    }


}
