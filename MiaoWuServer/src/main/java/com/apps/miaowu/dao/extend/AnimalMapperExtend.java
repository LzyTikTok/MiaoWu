package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface AnimalMapperExtend {
    List<Animal> selectSaveAnimalByUserId(Long userId);

    List<Animal> selectFoundAnimalByUserId(Long userId);
}
