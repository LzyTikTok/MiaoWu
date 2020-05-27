package com.apps.miaowu.service;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import io.swagger.annotations.Api;

import java.util.List;

public interface UserService {
    APIResult findAll();

    APIResult updateUserInfo(User user);

    APIResult addUser(User user);

    APIResult getInfo(String token);

    APIResult findById(Long id);

    APIResult findAllUserWithFound();

    APIResult findUserWithFoundById(Long id);

    APIResult findAllUserWithSave();

    APIResult findUserWithSaveById(Long id);

    APIResult cascadeFindAllUser();

    APIResult cascadeFindUserById(Long id);

    APIResult deleteUserById(Long id);

    APIResult findAllFans(Long id);

    APIResult findAllFollows(Long id);

    APIResult login(String phone, String password);

}
