package com.apps.miaowu.service;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface ArticleService {
    APIResult findAll();

    APIResult saveOrUpdate(Article article);

    APIResult deleteById(Long id);

    APIResult findAllArticleWithAnimal();

    APIResult findArticleWithAnimalById(Long id);

    APIResult findAllArticleWithLabel();

    APIResult findArticleWithLabelById(Long id);

    APIResult cascadeFindAll();

    APIResult cascadeFindById(Long id);

}
