package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.ArticleExample;
import com.apps.miaowu.bean.ArticleLabel;
import com.apps.miaowu.bean.Label;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.LabelMapper;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleMapper articleMapper;
    
    @Resource
    ArticleMapperExtend articleMapperExtend;

    @Resource
    LabelMapper labelMapper;

    @Override
    public List<Article> findAll() {
        ArticleExample example = new ArticleExample();
        return articleMapper.selectByExample(example);
    }

    @Override
    public String saveOrUpdate(Article article) {
        if(article.getId() != null){
            article.setLastUpdate(new Date());
            articleMapper.updateByPrimaryKey(article);
            return "更新成功";
        }
        else {
            article.setWriteDate(new Date());
            articleMapper.insert(article);
            return "插入成功";
        }
    }

    @Override
    public String deleteById(Article article) {
        if((articleMapper.selectByPrimaryKey(article.getId()) != null)){
            articleMapper.deleteByPrimaryKey(article.getId());
            return "删除成功";
        }
        else {
            return "不存在该用户";
        }
    }

    @Override
    public List<ArticleExtend> findAllArticleWithAnimal() {
        return articleMapperExtend.selectArticleWithAnimal();
    }

    @Override
    public List<ArticleExtend> findArticleWithAnimalById(Long id) {
        return articleMapperExtend.selectArticleWithAnimalById(id);
    }

    @Override
    public List<ArticleExtend> findAllArticleWithLabel() {
        return articleMapperExtend.selectArticleWithLabel();
    }

    @Override
    public List<ArticleExtend> findArticleWithLabelById(Long id) {
        return articleMapperExtend.selectArticleWithLabelById(id);
    }

    @Override
    public List<ArticleExtend> cascadeFindAll() {
        //此处应该不这么写
//        List<ArticleExtend> articleExtends = articleMapperExtend.cascadeFindAll();
//        for(ArticleExtend articleExtend : articleExtends){
//            List<ArticleLabel> articleLabels = articleExtend.getArticleLabels();
//            for(ArticleLabel articleLabel : articleLabels){
//                labelMapper.selectByPrimaryKey(articleLabel.getId());
//            }
//
//        }
        return null;
    }

    @Override
    public List<ArticleExtend> cascadeFindById(Long id) {
        return null;
    }


}
