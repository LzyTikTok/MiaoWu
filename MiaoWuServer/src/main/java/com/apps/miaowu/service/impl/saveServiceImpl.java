package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Save;
import com.apps.miaowu.bean.SaveExample;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.dao.SaveMapper;
import com.apps.miaowu.service.SaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class saveServiceImpl implements SaveService {

    @Resource
    SaveMapper saveMapper;

    @Override
    public APIResult addSave(Long userId, Long animalId) {
        if(userId == null || animalId == null){
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }
        SaveExample example = new SaveExample();
        example.createCriteria().andUserIdEqualTo(userId).andAnimalIdEqualTo(animalId);
        List<Save> saves = saveMapper.selectByExample(example);
        if(!saves.isEmpty()){
            return APIResult.newResult(ResultCode.DATA_ALREADY_EXISTEDINT, "DATA_ALREADY_EXIST", null);
        }

        Save save = new Save();
        save.setAnimalId(animalId);
        save.setUserId(userId);
//        save.setSaveDate(LocalDateTime.now());
        save.setSaveDate(new Date());

        saveMapper.insert(save);
        return APIResult.newResult(ResultEnum.SUCCESS, null);
//        return null;
    }

    @Override
    public APIResult deleteSave(Long userId) {
        Save save = saveMapper.selectByPrimaryKey(userId);
        if (save != null) {
            try {
                saveMapper.deleteByPrimaryKey(userId);
                return APIResult.newResult(ResultEnum.SUCCESS, null);
            } catch (Exception e) {
                System.out.println(e);
                return APIResult.newResult(ResultCode.BadRequest, "error", null);
            }
        } else {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
    }
}