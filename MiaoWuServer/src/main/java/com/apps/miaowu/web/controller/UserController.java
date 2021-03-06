package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "")
    public APIResult findAll() {
        return userService.findAll();
    }

    @NoneAuth
    @PostMapping(value = "")
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

    @PutMapping(value = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "name", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "idCode", value = "", required = false),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
            @ApiImplicitParam(name = "country_id", value = "", required = true),
            @ApiImplicitParam(name = "phone", value = "", required = true),
            @ApiImplicitParam(name = "create_date", value = "", required = false),
            @ApiImplicitParam(name = "birthday", value = "", required = false),
            @ApiImplicitParam(name = "password", value = "", required = false)
    })
    public APIResult updateUserInfo(User user) {
        return userService.updateUserInfo(user);
    }

    @NoneAuth
    @ApiOperation(value = "获取用户全部信息",notes = "其中的token必须为对应用户id的token")
    @GetMapping(value = "info")
    public APIResult info(String token) {
        return userService.getInfo(token);
    }

    @NoneAuth
    @PostMapping(value = "login")
    public APIResult login(String phone, String password) {
        return userService.login(phone, password);
    }

    @NoneAuth
    @ApiOperation(value = "获取用户部分信息")
    @GetMapping(value = "{id}")
    public APIResult findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping(value = "{id}")
    public APIResult deleteById(@PathVariable long id){return userService.deleteUserById(id);}

    @NoneAuth
    @PostMapping(value = "logout")
    public APIResult logout(String token){ return userService.logout(token);}

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
//
}
