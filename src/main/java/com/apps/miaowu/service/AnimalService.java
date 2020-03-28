package com.apps.miaowu.service;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();

    List<Animal>findById(Long id);

    String saveOrUpdate(Animal animal);

}
