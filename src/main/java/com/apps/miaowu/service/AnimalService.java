package com.apps.miaowu.service;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();

    List<Animal>findById(Long id);

    APIResult saveOrUpdate(Animal animal);

}
