package com.apps.miaowu.service;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface AnimalService {
    APIResult findAll();

    APIResult findById(Long id);

    APIResult add(Animal animal, Long userId);

    APIResult findAllSaveAnimalByUserId(Long UserId);

    APIResult findFoundAnimalByUserId(Long userId);

}
