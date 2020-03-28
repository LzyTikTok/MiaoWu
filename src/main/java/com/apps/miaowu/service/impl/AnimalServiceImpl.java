package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import com.apps.miaowu.dao.AnimalMapper;
import com.apps.miaowu.service.AnimalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Resource
    private AnimalMapper animalMapper;

    @Override
    public List<Animal> findAll() {
        AnimalExample example = new AnimalExample();
        return animalMapper.selectByExample(example);
    }

    @Override
    public List<Animal> findById(Long id) {
        AnimalExample example = new AnimalExample();
        example.createCriteria().andIdEqualTo(id);
        List<Animal> animals = animalMapper.selectByExample(example);
        return animals;
    }

    @Override
    public String saveOrUpdate(Animal animal) {
        if(animal.getId() != null){
            animalMapper.updateByPrimaryKey(animal);
            return "更新成功";
        }
        else {
            animalMapper.insert(animal);
            return "插入成功";
        }
    }
}
