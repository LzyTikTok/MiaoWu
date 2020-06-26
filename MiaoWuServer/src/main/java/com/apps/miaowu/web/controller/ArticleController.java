package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.ArticleWithBLOBs;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @Resource
    protected HttpServletRequest request;

    @PostMapping(value = "")
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
    public APIResult addArticle(ArticleWithBLOBs article) {
        return articleService.addArticle(article);
    }

    @PutMapping(value = "")
    public APIResult updateArticle(ArticleWithBLOBs article) {
        return articleService.updateArticle(request, article);
    }

    @NoneAuth
    @GetMapping(value = "")
    public APIResult findAll() {
        return articleService.findAll();
    }

    @NoneAuth
    @GetMapping(value = "page={page}&count={count}")
    public APIResult findArticleWithLabelByPage(@PathVariable String page, @PathVariable String count){
        return articleService.findArticleWithLabelByPage(Integer.valueOf(page), Integer.valueOf(count));
    }

    @NoneAuth
    @GetMapping(value = "with=clips?userId={userId}")
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(@PathVariable Long userId) {
        return articleService.findAllWithClipByUserIdOrderByUpdateDesc(userId);
    }

    @DeleteMapping(value = "{id}")
    public APIResult deleteById(Long id) {
        return articleService.deleteById(id);
    }

    @NoneAuth
    @GetMapping(value = "like={title}")
    public APIResult findArticleWithTitleFuzzily(@PathVariable String title) {
        return articleService.findArticleWithTitleFuzzily(title);
    }

    @NoneAuth
    @GetMapping(value = "{id}")
    APIResult cascadeFindById(@PathVariable Long id) {
        return articleService.cascadeFindById(id);
    }

    @NoneAuth
    @GetMapping(value = "authorId={authorId}")
    APIResult findByAuthorIdOrderByUpdateDesc(@PathVariable long authorId) {
        return articleService.findArticleByAuthorIdOrderByUpdateDesc(authorId);
    }

    @NoneAuth
    @GetMapping(value = "fansId={fansId}")
    APIResult findFollowsArticleByUserIdOrderByUpdateDesc(@PathVariable Long fansId) {
        return articleService.findFollowsArticleByUserIdOrderByUpdateDesc(fansId);
    }
//    todo 上传图片
//    @PostMapping(value = "uploadImg")
//    APIResult uploadImg(MultipartFile image) {
//        return null;
////        return articleService.uploadImg(request,image);
//    }

}
