package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import com.apps.miaowu.bean.Found;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.dao.AnimalMapper;
import com.apps.miaowu.dao.FoundMapper;
import com.apps.miaowu.dao.extend.AnimalMapperExtend;
import com.apps.miaowu.service.AnimalService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Resource
    private AnimalMapper animalMapper;

    @Resource
    private FoundMapper foundMapper;

    @Resource
    private AnimalMapperExtend animalMapperExtend;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public APIResult findAll() {
        AnimalExample example = new AnimalExample();
        List<Animal> results = animalMapper.selectByExample(example);
        return APIResult.newResult(200, "Find all animal successfully", results);
    }

    @Override
    public APIResult findById(Long id) {
        if (id == null) {
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }
        AnimalExample example = new AnimalExample();
        example.createCriteria().andIdEqualTo(id);
        List<Animal> animals = animalMapper.selectByExample(example);
        animals.forEach(animal -> {
            Date birthday;
            if ((birthday = animal.getBirthday()) != null) {
                Calendar birthCal = Calendar.getInstance();
                birthCal.setTime(birthday);
                Calendar nowCal = Calendar.getInstance();
                nowCal.setTime(new Date());
                int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
                if(age > animal.getAge()){
                    animal.setAge(age);
                    rabbitTemplate.convertAndSend("update_queue", animal);
                }
            }
        });
        return APIResult.newResult(200, "Find animal by id successfully", animals);
    }

    @Override
    public APIResult add(Animal animal, Long userId) {
        // //todo 把update改成部分更新字段

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
        if (animals.isEmpty()) {
            return APIResult.newResult(ResultCode.BadRequest, "no Animals", null);
        } else {
            return APIResult.newResult(ResultCode.SuccessCode, "success", animals);
        }
    }

    @Override
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("update_queue"))
    public APIResult update(Animal animal) {
        if (animal.getId() != null) {
            animalMapper.updateByPrimaryKeySelective(animal);
            return APIResult.newResult(ResultEnum.SUCCESS, null);
        } else {
            return APIResult.newResult(ResultEnum.BAD_REQUEST, null);
        }
    }
}
