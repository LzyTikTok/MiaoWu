package com.apps.miaowu.service.impl;

import com.alibaba.fastjson.JSON;
import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.FollowMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.dao.extend.UserMapperExtend;
import com.apps.miaowu.service.UserService;
import com.apps.miaowu.utils.token.TokenHelper;
import com.apps.miaowu.utils.token.TokenModel;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMapperExtend userMapperExtend;

    @Resource
    private FollowMapper followMapper;

    @Resource
    private TokenHelper tokenHelper;

    @Resource
    private FollowServiceImpl followService;

    @Override
    public APIResult findAll() {
        UserExample example = new UserExample();
        List<User> results = userMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user successfully", results);
    }

    @Override
    public APIResult updateUserInfo(User user) {
        if (user.getId() != null) {
            userMapper.updateByPrimaryKeySelective(user);
            return APIResult.newResult(ResultCode.SuccessCode, "update successfully", null);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "can't find the user", null);
        }
        // save
    }

    @Override
    public APIResult getInfo(String token) {
        //redis中存在该token
        if (tokenHelper.check(token)) {
            //获取用户id
            TokenModel tokenModel = tokenHelper.get(token);
            Long userId = Long.valueOf(tokenModel.getUserId());

            //获取用户信息
            User user = userMapper.selectByPrimaryKey(userId);

            //获取关注信息和粉丝信息
            APIResult follows = followService.findAllFollowsByUserId(userId);
            APIResult fans = followService.findAllFansByUserId(userId);

            Map<String, Object> res = new HashMap<>();
            res.put("user", user);//用户信息
            res.put("follows", follows.getData());//用户的关注信息
            res.put("fans", fans.getData());//用户的粉丝信息

            return APIResult.newResult(ResultCode.SuccessCode, "Login successfully", res);
        } else { //不存在该token
            return APIResult.newResult(ResultCode.BadRequest, "token out of date", null);
        }
    }

    @Override
    public APIResult findById(Long id) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        List<User> users = userMapper.selectByExample(example);
        users.forEach((item) -> {
            item.setPhone(null);
            item.setPassword(null);
            item.setIdCode(null);
        });

        return APIResult.newResult(ResultCode.SuccessCode, "success", users);
    }

    @Override
    public APIResult findAllUserWithFound() {
        List<UserExtend> results = userMapperExtend.selectUserWithFound();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all with found successfully", results);
    }

    @Override
    public APIResult findUserWithFoundById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithFoundById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find user with found by id successfully", results);
    }

    @Override
    public APIResult findAllUserWithSave() {
        List<UserExtend> results = userMapperExtend.selectUserWithSave();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user with save successfully", results);
    }

    @Override
    public APIResult findUserWithSaveById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithSaveById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find user with save by id successfully", results);
    }

    @Override
    public APIResult cascadeFindAllUser() {
        List<UserExtend> results = userMapperExtend.cascadeFindAllUser();
        return APIResult.newResult(ResultCode.SuccessCode, "Cascade find all user successfully", results);
    }

    @Override
    public APIResult cascadeFindUserById(Long id) {
        List<UserExtend> results = userMapperExtend.cascadeFindUserById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Cascade find user by id successfully", results);
    }

    @Override
    public APIResult deleteUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            userMapper.deleteByPrimaryKey(id);
            return APIResult.newResult(ResultCode.SuccessCode, "Delete user by id successfully", null);
        } else {
            return APIResult.newResult(500, "can't find the uesr", null);
        }
    }

    @Override
    public APIResult addUser(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andIdCodeEqualTo(user.getIdCode());
        List<User> idCodeUsers = userMapper.selectByExample(example);

        UserExample example2 = new UserExample();
        example2.createCriteria().andPhoneEqualTo(user.getPhone());
        List<User> phoneUsers = userMapper.selectByExample(example2);

        if (idCodeUsers.isEmpty() && phoneUsers.isEmpty()) {
            user.setCreateDate(new Date());
            userMapper.insert(user);
            return APIResult.newResult(ResultCode.SuccessCode, "add user successfully", null);
        } else {
            return APIResult.newResult(ResultCode.DATA_ALREADY_EXISTEDINT, "user exist", null);
        }
    }

    @Override
    public APIResult login(String phone, String password) {
        //输入不合法
        if (phone == null || password == null) {
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }

        //查找用户
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);

        //电话号码无对应账号
        if (users.size() == 0) {
            return APIResult.newResult(500, "User not exist", null);
        } else if (users.size() == 1) {
            User user = users.get(0);
            //验证通过
            if (user.getPassword().equals(password)) {
                // 用户名密码验证通过后，生成token
                TokenModel model = tokenHelper.create(user.getId().toString());
                return APIResult.newResult(ResultCode.SuccessCode, "get token successfully", model);
            } else { //密码不正确
                return APIResult.newResult(ResultCode.BadRequest, "Incorrect password", null);
            }
        } else {    //数据过多
            return APIResult.newResult(ResultCode.BadRequest, "too many users", null);
        }
    }


    @Override
    public APIResult logout(String token) {
        if(tokenHelper.delete(token)){
            return APIResult.newResult(ResultCode.SuccessCode,"success", null);
        } else {
            return APIResult.newResult(ResultCode.ServerInnerError, "fail",null);
        }
    }

//    todo 将info和getById整合为一个接口
}
