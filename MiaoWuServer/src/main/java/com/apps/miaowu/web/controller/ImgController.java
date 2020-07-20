package com.apps.miaowu.web.controller;

import com.apps.miaowu.annotation.NoneAuth;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.service.ImgService;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("imgs")

public class ImgController {

    @Resource
    ImgService imgService;

    @NoneAuth
    @PutMapping(value = "")
    APIResult uploadImg(HttpServletRequest req, MultipartFile image) {
        return imgService.addImg(req, image);
    }

    @NoneAuth
    @DeleteMapping(value = "url")
    APIResult delImg(@PathVariable String url) {
        return imgService.delImg(url);
    }

}
