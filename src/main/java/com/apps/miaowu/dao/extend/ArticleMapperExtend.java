package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.extend.ArticleExtend;

import java.util.List;

public interface ArticleMapperExtend {
    List<ArticleExtend> selectArticleWithAnimal();

    List<ArticleExtend> selectArticleWithAnimalById(Long id);

    List<ArticleExtend> selectArticleWithLabel();

    List<ArticleExtend> selectArticleWithLabelById(Long id);

    List<ArticleExtend> cascadeFindAll();

    List<ArticleExtend> cascadeFindById(Long id);

    List<Article> selectClipArticleByUserIdDesc(Long userId);

    List<ArticleExtend> selectCommentArticleByArticleId(Long articleId);

}
