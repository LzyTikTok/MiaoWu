package com.apps.miaowu.service;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();

    APIResult saveOrUpdate(Article article);

    APIResult deleteById(Long id);

    List<ArticleExtend> findAllArticleWithAnimal();

    List<ArticleExtend> findArticleWithAnimalById(Long id);

    List<ArticleExtend> findAllArticleWithLabel();

    List<ArticleExtend> findArticleWithLabelById(Long id);

    List<ArticleExtend> cascadeFindAll();

    List<ArticleExtend> cascadeFindById(Long id);

}
