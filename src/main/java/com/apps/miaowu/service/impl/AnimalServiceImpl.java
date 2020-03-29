package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
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
    public APIResult saveOrUpdate(Animal animal) {
        Animal find  = animalMapper.selectByPrimaryKey(animal.getId());
        System.out.println(find);
        //todo 把update改成部分更新字段
        if(find != null){
            animalMapper.updateByPrimaryKeySelective(animal);
            return APIResult.newResult(ResultCode.SuccessCode,"Update successfully",null);
        }
        else {
            animalMapper.insert(animal);
            return APIResult.newResult(ResultCode.BadRequest,"Insert successfully",null);
        }
    }
}
