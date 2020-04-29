package com.apps.miaowu.service;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.ArticleWithBLOBs;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface ArticleService {
    APIResult findAll();

    APIResult saveOrUpdate(ArticleWithBLOBs article);

    APIResult deleteById(Long id);

    APIResult findAllArticleWithAnimal();

    APIResult findArticleWithAnimalById(Long id);

    APIResult findArticleWithTitleFuzzily(String title);

    APIResult findAllArticleWithLabel();

    APIResult findArticleWithLabelById(Long id);

    APIResult findArticleWithCommentById(Long articleId);

    APIResult findClipArticleByUserIdDesc(Long userId);

    APIResult cascadeFindAll();

    APIResult cascadeFindById(Long id);

}
