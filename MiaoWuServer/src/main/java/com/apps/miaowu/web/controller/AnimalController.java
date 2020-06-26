package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("animals")
public class AnimalController {

    @Autowired
    AnimalService animalService;

    @NoneAuth
    @GetMapping(value = "")
    APIResult findAllAnimal(){
        return animalService.findAll();
    }

    @NoneAuth
    @GetMapping(value = "{id}")
    APIResult findAnimalById(@PathVariable  Long id){
        return animalService.findById(id);
    }

    @PostMapping(value = "")
    APIResult add(Animal animal, Long userId){
        return animalService.add(animal, userId);
    }

    @NoneAuth
    @GetMapping(value = "userId={userId}")
    APIResult findFoundAnimalByUserId(Long userId){
        return animalService.findFoundAnimalByUserId(userId);
    }

}
