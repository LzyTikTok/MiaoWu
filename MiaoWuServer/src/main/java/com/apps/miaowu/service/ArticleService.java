package com.apps.miaowu.service;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.ArticleWithBLOBs;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ArticleService {
    APIResult findAll();

    APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId);

    APIResult findArticleByAuthorIdOrderByUpdateDesc(Long authorId);

    APIResult cascadeFindById(Long articleId);

    APIResult saveOrUpdate(ArticleWithBLOBs article);

    APIResult addArticle(ArticleWithBLOBs article);

    APIResult updateArticle(ArticleWithBLOBs article);

    APIResult deleteById(Long id);

    APIResult findAllArticleWithAnimal();

    APIResult findArticleWithAnimalById(Long id);

    APIResult findArticleWithTitleFuzzily(String title);

    APIResult findAllArticleWithLabel();

    APIResult findArticleWithLabelById(Long id);

    APIResult findArticleWithCommentById(Long articleId);

    APIResult findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId);

    APIResult cascadeFindAll();

    APIResult thumbUpOrDown(Long articleId, Long userId);

    APIResult findFollowsArticleByUserIdOrderByUpdateDesc(Long UserId);
    
    APIResult uploadImg(HttpServletRequest req, MultipartFile image);

}
