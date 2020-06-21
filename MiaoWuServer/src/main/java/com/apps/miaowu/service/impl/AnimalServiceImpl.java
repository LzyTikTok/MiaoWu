package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import com.apps.miaowu.bean.Found;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.AnimalMapper;
import com.apps.miaowu.dao.FoundMapper;
import com.apps.miaowu.dao.extend.AnimalMapperExtend;
import com.apps.miaowu.service.AnimalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Resource
    private AnimalMapper animalMapper;

    @Resource
    private FoundMapper foundMapper;

    @Resource
    private AnimalMapperExtend animalMapperExtend;

    @Override
    public APIResult findAll() {
        AnimalExample example = new AnimalExample();
        List<Animal> results = animalMapper.selectByExample(example);
        return APIResult.newResult(200, "Find all animal successfully", results);
    }

    @Override
    public APIResult findById(Long id) {
        if(id == null){
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }
        AnimalExample example = new AnimalExample();
        example.createCriteria().andIdEqualTo(id);
        List<Animal> animals = animalMapper.selectByExample(example);
        return APIResult.newResult(200, "Find animal by id successfully", animals);
    }

    @Override
    public APIResult add(Animal animal, Long userId) {
        // Animal find = animalMapper.selectByPrimaryKey(animal.getId());
        // System.out.println(find);
        // //todo 把update改成部分更新字段
        // if(find != null){
        // animalMapper.updateByPrimaryKeySelective(animal);
        // return APIResult.newResult(ResultCode.SuccessCode,"Update
        // successfully",null);
        // }
        // else {
        // animalMapper.insert(animal);
        // return APIResult.newResult(ResultCode.SuccessCode,"Insert
        // successfully",null);
        // }

        animalMapper.insert(animal);
        Found found = new Found();
        found.setAnimalId(animal.getId());
        found.setUserId(userId);
        foundMapper.insert(found);
        return APIResult.newResult(ResultCode.SuccessCode, "Insert animal successfully", null);
    }

    @Override
    public APIResult findAllSaveAnimalByUserId(Long UserId) {
        return null;
    }

    @Override
    public APIResult findFoundAnimalByUserId(Long userId) {
        List<Animal> animals = animalMapperExtend.selectFoundAnimalByUserId(userId);
        if(animals.isEmpty()){
            return APIResult.newResult(ResultCode.BadRequest,"no Animals",null);
        } else {
            return APIResult.newResult(ResultCode.SuccessCode, "success", animals);
        }
    }

    
}
