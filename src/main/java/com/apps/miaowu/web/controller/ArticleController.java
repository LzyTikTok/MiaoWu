package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping(value = "saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "content", value = "", required = false),
            //身份证号码可以先不需要，注册的门槛尽量放低
            @ApiImplicitParam(name = "write_date", value = "", required = false),
//          思考 归属地的问题 可不可以通过号码直接判断
            //前端传送数据过来的时候 直接加上国际区号。
            @ApiImplicitParam(name = "last_update", value = "", required = false),
            @ApiImplicitParam(name = "thumb_up", value = "", required = false),
            @ApiImplicitParam(name = "animal_id", value = "", required = false),
            //1是空间，2是救助文？？？
            @ApiImplicitParam(name = "type", value = "", required = true),
    })
    public String saveOrupdate(Article article){
        return articleService.saveOrUpdate(article);
    }

    @GetMapping(value = "findAll")
    public List<Article> findAll(){
        return articleService.findAll();
    }

    @GetMapping(value = "findAllArticleWithAnimal")
    public List<ArticleExtend> findAllArticleWithAnimal(){
        return articleService.findAllArticleWithAnimal();
    }

    @GetMapping(value = "findArticleWithAnimalById")
    public List<ArticleExtend> findArticleWithAnimalById(Long id){
        return articleService.findArticleWithAnimalById(id);
    }

    @GetMapping(value = "findAllArticleWithLabel")
    public List<ArticleExtend> findAllArticleWithLabel(){
        return articleService.findAllArticleWithLabel();
    }

    @GetMapping(value = "findArticleWithLabelById")
    public List<ArticleExtend> findArticleWithLabelById(Long id){
        return articleService.findArticleWithLabelById(id);
    }

    @DeleteMapping(value = "deleteArticleById")
    public APIResult deleteArticleById(Long id){return articleService.deleteById(id);}
}
