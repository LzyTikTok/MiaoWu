package com.apps.miaowu.web.controller;

import com.apps.miaowu.bean.ArticleWithBLOBs;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping(value = "saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "title", value = "", required = true),
            @ApiImplicitParam(name = "author_id", value = "", required = true),
            @ApiImplicitParam(name = "content", value = "", required = false),
            @ApiImplicitParam(name = "write_date", value = "", required = false),
            @ApiImplicitParam(name = "last_update", value = "", required = false),
            @ApiImplicitParam(name = "thumb_up", value = "", required = false),
            @ApiImplicitParam(name = "animal_id", value = "", required = false),
            //1是空间，2是救助文？？？
            //todo 救助文与空间文分开两个接口去添加？
            @ApiImplicitParam(name = "type", value = "", required = true),
    })
    public APIResult saveOrUpdate(ArticleWithBLOBs article){
        return articleService.saveOrUpdate(article);
    }

    @PostMapping(value = "addArticle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = false),
            @ApiImplicitParam(name = "title", value = "", required = true),
            @ApiImplicitParam(name = "authorId", value = "", required = true),
            @ApiImplicitParam(name = "content", value = "", required = false),
            @ApiImplicitParam(name = "writeDate", value = "", required = false),
            @ApiImplicitParam(name = "lastUpdate", value = "", required = false),
            @ApiImplicitParam(name = "thumbUp", value = "0", required = false),
            @ApiImplicitParam(name = "animalId", value = "", required = false),
            //1是空间，2是救助文
            @ApiImplicitParam(name = "type", value = "", required = true),
    })
    public APIResult addArticle(ArticleWithBLOBs article){
        return articleService.addArticle(article);
    }

    @PostMapping(value = "updateArticle")
    public APIResult updateArticle(ArticleWithBLOBs article) { return articleService.updateArticle(article);}

    @GetMapping(value = "findAll")
    public APIResult findAll(){
        return articleService.findAll();
    }

    @GetMapping(value = "findAllWithClipByUserIdOrderByUpdateDesc")
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId) {return articleService.findAllWithClipByUserIdOrderByUpdateDesc(userId);}

    @GetMapping(value = "findAllArticleWithAnimal")
    public APIResult findAllArticleWithAnimal(){
        return articleService.findAllArticleWithAnimal();
    }

    @GetMapping(value = "findArticleWithAnimalById")
    public APIResult findArticleWithAnimalById(Long id){
        return articleService.findArticleWithAnimalById(id);
    }

    @GetMapping(value = "findAllArticleWithLabel")
    public APIResult findAllArticleWithLabel(){
        return articleService.findAllArticleWithLabel();
    }

    @GetMapping(value = "findArticleWithLabelById")
    public APIResult findArticleWithLabelById(Long id){
        return articleService.findArticleWithLabelById(id);
    }

    @DeleteMapping(value = "deleteArticleById")
    public APIResult deleteArticleById(Long id){return articleService.deleteById(id);}

    @GetMapping(value = "findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc")
    public APIResult findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId){return articleService.findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);}

//todo 未测试
    @GetMapping(value = "findArticleWithTitleFuzzily")
    public APIResult findArticleWithTitleFuzzily(String title){return articleService.findArticleWithTitleFuzzily(title);}

    @PostMapping(value = "thumbUpOrDown")
    APIResult thumbUpOrDown(Long articleId, Long userId){
        return articleService.thumbUpOrDown(articleId,userId);
    }

    @GetMapping(value = "cascadeFindById")
    APIResult cascadeFindById(Long articleId) {return articleService.cascadeFindById(articleId);}


    @GetMapping(value = "findByAuthorIdOrderByUpdateDesc")
    APIResult findByAuthorIdOrderByUpdateDesc(long authorId){return articleService.findArticleByAuthorIdOrderByUpdateDesc(authorId);}
}
