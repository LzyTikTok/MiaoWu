package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Save;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.SaveMapper;
import com.apps.miaowu.service.SaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class saveServiceImpl implements SaveService {

    @Resource
    SaveMapper saveMapper;

    @Override
    public APIResult addSave(Long userId, Long animalId) {
        Save save = new Save();
        save.setAnimalId(animalId);
        save.setUserId(userId);
//        save.setSaveDate(LocalDateTime.now());
        save.setSaveDate(new Date());

        saveMapper.insert(save);
        return APIResult.newResult(ResultCode.SuccessCode, "success", null);
//        return null;
    }

    @Override
    public APIResult deleteSave(Long userId) {
        Save save = saveMapper.selectByPrimaryKey(userId);
        if (save != null) {
            try {
                saveMapper.deleteByPrimaryKey(userId);
                return APIResult.newResult(ResultCode.SuccessCode, "delete save record successfully", null);
            } catch (Exception e) {
                System.out.println(e);
                return APIResult.newResult(ResultCode.BadRequest, "error", null);
            }
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "record not exist", null);
        }
    }
}