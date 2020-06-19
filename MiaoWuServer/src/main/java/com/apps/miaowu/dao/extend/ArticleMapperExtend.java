package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.extend.ArticleExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapperExtend {

    Integer selectLastUpdate();

    List<ArticleExtend> selectAllByUserIdOrderByUpdateDesc(Long userId);

    List<ArticleExtend> selectArticleWithAnimal();

    List<ArticleExtend> selectArticleWithAnimalById(Long id);

    List<ArticleExtend> selectArticleWithLabel();

    List<ArticleExtend> selectArticleWithLabelById(Long id);

    List<ArticleExtend> cascadeFindAll();

    List<ArticleExtend> cascadeFindById(Long id);

    List<ArticleExtend> selectClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId);

    List<ArticleExtend> selectCommentArticleByArticleId(Long articleId);

    List<ArticleExtend> selectArticleWithLabelByPage(@Param("start")Integer start, @Param("end") Integer end);

    List<ArticleExtend> selectFollowsArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId);

    ArticleExtend selectArticleById(Long id);

}
