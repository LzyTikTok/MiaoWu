package com.apps.miaowu.service;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.common.service.MiaoWuService;

import java.util.List;

public interface AnimalService extends MiaoWuService{

    APIResult findAll();

    APIResult findById(Long id);

    APIResult add(Animal animal, Long userId);

    APIResult update(Animal animal);

    APIResult findAllSaveAnimalByUserId(Long userId);

    APIResult findFoundAnimalByUserId(Long userId);

}
