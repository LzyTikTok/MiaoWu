package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.common.exception.MiaowuException;
import com.apps.miaowu.dao.FollowMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.dao.extend.UserMapperExtend;
import com.apps.miaowu.service.UserService;
import com.apps.miaowu.common.utils.token.TokenHelper;
import com.apps.miaowu.common.utils.token.TokenModel;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
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
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult updateUserInfo(User user) {
        try {
            varifyNullParam(user.getId());
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }

        userMapper.updateByPrimaryKeySelective(user);
        return APIResult.newResult(ResultEnum.SUCCESS, null);
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

            return APIResult.newResult(ResultEnum.SUCCESS, res);
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

        return APIResult.newResult(ResultEnum.SUCCESS, users);
    }

    @Override
    public APIResult findAllUserWithFound() {
        List<UserExtend> results = userMapperExtend.selectUserWithFound();
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findUserWithFoundById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithFoundById(id);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findAllUserWithSave() {
        List<UserExtend> results = userMapperExtend.selectUserWithSave();
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findUserWithSaveById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithSaveById(id);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult cascadeFindAllUser() {
        List<UserExtend> results = userMapperExtend.cascadeFindAllUser();
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult cascadeFindUserById(Long id) {
        List<UserExtend> results = userMapperExtend.cascadeFindUserById(id);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult deleteUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            userMapper.deleteByPrimaryKey(id);
            return APIResult.newResult(ResultEnum.SUCCESS, null);
        } else {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
    }

    @Override
    public APIResult addUser(User user) {
        UserExample isUserExist = new UserExample();
        isUserExist.createCriteria().andIdCodeEqualTo(user.getIdCode())
                .andPhoneEqualTo(user.getPhone());
        List<User> users = userMapper.selectByExample(isUserExist);

        if (users.isEmpty()) {
            user.setCreateDate(new Date());
            userMapper.insert(user);
            return APIResult.newResult(ResultEnum.SUCCESS, null);
        } else {
            return APIResult.newResult(ResultCode.DATA_ALREADY_EXISTEDINT, "user exist", null);
        }
    }

    @Override
    public APIResult login(String phone, String password) {
        //输入不合法
        try{
            varifyNullParam(phone, password);
        } catch (MiaowuException miaowuException){
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);

        }
        //查找用户
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);

        //电话号码无对应账号
        if (users.size() == 0) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        } else if (users.size() == 1) {
            User user = users.get(0);
            //验证通过
            if (user.getPassword().equals(password)) {
                // 用户名密码验证通过后，生成token
                TokenModel model = tokenHelper.create(user.getId().toString());
                return APIResult.newResult(ResultEnum.SUCCESS, model);
            } else { //密码不正确
                return APIResult.newResult(ResultCode.BadRequest, "Incorrect password", null);
            }
        } else {    //数据过多
            return APIResult.newResult(ResultCode.BadRequest, "too many users", null);
        }
    }


    @Override
    public APIResult logout(String token) {
        if (tokenHelper.delete(token)) {
            return APIResult.newResult(ResultEnum.SUCCESS, null);
        } else {
            return APIResult.newResult(ResultCode.ServerInnerError, "fail", null);
        }
    }

//    todo 将info和getById整合为一个接口
}
