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

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (tokenHelper.check(token)) {
            TokenModel tokenModel = tokenHelper.get(token);
            Long userId = Long.valueOf(tokenModel.getUserId());

            User user = userMapper.selectByPrimaryKey(userId);
            Map<String, Object> res = new HashMap<>();

            APIResult follows = this.findAllFollows(userId);
            APIResult fans = this.findAllFans(userId);
            res.put("user", user);
            res.put("follows", follows.getData());
            res.put("fans", fans.getData());

            String resString = JSON.toJSONString(res);
            return APIResult.newResult(ResultCode.SuccessCode, "Login successfully", resString);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "token out of date", null);
        }
    }

    @Override
    public APIResult findById(Long id) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        List<User> users = userMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user successfully", users);
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

    // todo 以下两个接口未测试
    @Override
    public APIResult findAllFans(Long userId) {
        FollowExample example = new FollowExample();
        // 找到自己的所有粉丝
        example.createCriteria().andUserIdEqualTo(userId);
        List<Follow> follows = followMapper.selectByExample(example);
        ArrayList<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            users.add(userMapper.selectByPrimaryKey(follow.getUserId()));
        }
        if (users.isEmpty()) {
            return APIResult.newResult(ResultCode.BadRequest, "can't find the fans", null);
        }
        return APIResult.newResult(ResultCode.SuccessCode, "success", users);
    }

    @Override
    public APIResult findAllFollows(Long userId) {
        FollowExample example = new FollowExample();
        // 找到自己关注的所有user
        example.createCriteria().andFansIdEqualTo(userId);
        List<Follow> follows = followMapper.selectByExample(example);
        ArrayList<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            users.add(userMapper.selectByPrimaryKey(follow.getUserId()));
        }
        if (users.isEmpty()) {
            return APIResult.newResult(ResultCode.BadRequest, "can't find the follows", null);
        }
        return APIResult.newResult(ResultCode.SuccessCode, "success", users);
    }

    // todo logout

    @Override
    public APIResult addUser(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andIdCodeEqualTo(user.getIdCode());
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty()) {
            // user.setCreateDate(LocalDateTime.now());
            user.setCreateDate(new Date());
            userMapper.insert(user);
            return APIResult.newResult(ResultCode.SuccessCode, "add user successfully", null);
        } else {
            return APIResult.newResult(ResultCode.DATA_ALREADY_EXISTEDINT, "user exist", null);
        }
    }

    @Override
    public APIResult login(String phone, String password) {
        if (phone == null || password == null) {
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return APIResult.newResult(500, "User not exist", null);
        } else if (users.size() == 1) {
            User user = users.get(0);
            if (user.getPassword().equals(password)) {
                // 用户名密码验证通过后，生成token
                TokenModel model = tokenHelper.create(user.getId().toString());
//            System.out.println(model);
                String jsonString = JSON.toJSONString(model);
                return APIResult.newResult(ResultCode.SuccessCode, "get token successfully", model);
            } else {
                return APIResult.newResult(ResultCode.BadRequest, "Incorrect password", null);
            }
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "too many users", null);
        }
    }


}
