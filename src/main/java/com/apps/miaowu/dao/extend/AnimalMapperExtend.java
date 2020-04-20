package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;

public interface AnimalMapperExtend {
    APIResult<Animal> selectSaveAnimalByUserId(Long userId);
}
