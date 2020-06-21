package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;
import org.springframework.stereotype.Service;

@Service
public interface SaveService {
    APIResult addSave(Long userId, Long animalId);

    APIResult deleteSave(Long userId);
}