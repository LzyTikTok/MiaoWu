package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.extend.UserExtend;

import java.util.List;

public interface UserMapperExtend {
  List<UserExtend> selectUserWithFound();

  List<UserExtend> selectUserWithFoundById(Long id);

  List<UserExtend> selectUserWithSave();

  List<UserExtend> selectUserWithSaveById(Long id);

  List<UserExtend> cascadeFindAllUser();

  List<UserExtend> cascadeFindUserById(Long id);


}