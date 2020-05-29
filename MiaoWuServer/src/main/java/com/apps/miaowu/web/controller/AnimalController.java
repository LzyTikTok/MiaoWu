package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("animal")
public class AnimalController {

    @Autowired
    AnimalService animalService;

    @GetMapping(value = "findAll")
    APIResult findAllAnimal(){
        return animalService.findAll();
    }

    @GetMapping(value = "findById")
    APIResult findAnimalById(Long id){
        return animalService.findById(id);
    }

    @PostMapping(value = "add")
    APIResult saveOrUpdate(Animal animal, Long userId){
        return animalService.add(animal, userId);
    }

    @GetMapping(value = "findFoundAnimalByUserId")
    APIResult findFoundAnimalByUserId(Long userId){
        return animalService.findFoundAnimalByUserId(userId);
    }

}