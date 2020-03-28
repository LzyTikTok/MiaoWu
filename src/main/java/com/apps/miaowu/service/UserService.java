package com.apps.miaowu.service;

import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.extend.UserExtend;

import java.util.List;

public interface UserService {
    List<User> findAll();

    String saveOrUpdate(User user);

    String login(User user);

    List<User> findById(Long id);

    List<UserExtend> findAllUserWithFound();

    List<UserExtend> findUserWithFoundById(Long id);

    List<UserExtend> findAllUserWithSave();

    List<UserExtend> findUserWithSaveById(Long id);

    List<UserExtend> cascadeFindAllUser();

    List<UserExtend> cascadeFindUserById(Long id);

}
