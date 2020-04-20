package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.SaveService;
import org.springframework.stereotype.Service;

@Service
public class saveServiceImpl implements SaveService {

    @Override
    public APIResult addSave(Long userId, Long animalId) {
        return null;
    }

    @Override
    public APIResult deleteSave(Long userId, Long animalId) {
        return null;
    }

    @Override
    public APIResult findAllSaveByUserId(Long userId) {
        return null;
    }
}