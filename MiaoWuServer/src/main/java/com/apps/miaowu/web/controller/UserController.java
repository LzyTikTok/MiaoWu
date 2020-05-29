package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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


    @PostMapping(value = "addUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "name", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "idCode", value = "", required = true),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
//            @ApiImplicitParam(name = "contry_id", value = "", required = true),
            @ApiImplicitParam(name = "phone", value = "", required = true),
            @ApiImplicitParam(name = "create_date", value = "", required = false),
            @ApiImplicitParam(name = "birthday", value = "", required = true),
            @ApiImplicitParam(name = "password", value = "", required = true)
    })
    public APIResult addUser(User user) {
        return userService.addUser(user);
    }

    @PostMapping(value = "updateUserInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "name", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "idCode", value = "", required = false),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
            @ApiImplicitParam(name = "contry_id", value = "", required = true),
            @ApiImplicitParam(name = "phone", value = "", required = true),
            @ApiImplicitParam(name = "create_date", value = "", required = false),
            @ApiImplicitParam(name = "birthday", value = "", required = false),
            @ApiImplicitParam(name = "password", value = "", required = false)
    })
    public APIResult updateUserInfo(User user) {
        return userService.updateUserInfo(user);
    }

    @GetMapping(value = "info")
    @NoneAuth
    public APIResult info(String token) {
        return userService.getInfo(token);
    }

    @PostMapping(value = "login")
    @NoneAuth
    public APIResult login(String phone, String password) {
        return userService.login(phone, password);
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

    @GetMapping(value = "getAllFollowByUserId")
    public APIResult getAllFollowByUserId(Long userId){return userService.findAllFollows(userId);}

    @GetMapping(value = "getAllFansByUserId")
    public APIResult getAllFansByUserId(Long userId){return userService.findAllFans(userId);}

    @DeleteMapping(value = "deleteUserById")
    public APIResult deleteById(long id){return userService.deleteUserById(id);}

    @NoneAuth
    @PostMapping(value = "logout")
    public APIResult logout(String token){ return userService.logout(token);}


    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
//
}
