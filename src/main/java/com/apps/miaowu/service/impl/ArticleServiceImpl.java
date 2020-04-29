package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.LabelMapper;
import com.apps.miaowu.dao.ThumbUpMapper;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.service.ArticleService;
import io.swagger.annotations.Example;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger.web.ApiResourceController;

import javax.annotation.Resource;
import javax.xml.transform.Result;
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

    @Resource
    ThumbUpMapper thumbUpMapper;

    @Override
    public APIResult findAll() {
        ArticleExample example = new ArticleExample();
        List<Article> results = articleMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode,"Find all article successfully",results);
    }

    @Override
    public APIResult findClipArticleByUserIdDesc(Long userId) {
        List<Article> articles = articleMapperExtend.selectClipArticleByUserIdDesc(userId);
        if(articles.size() == 0){
            return APIResult.newResult(ResultCode.ServerInnerError,"no Clip Article",null);
        } else {
            return APIResult.newResult(ResultCode.SuccessCode,"success",articles);
        }
    }

    @Override
    public APIResult saveOrUpdate(ArticleWithBLOBs article) {
        if(article.getId() != null){
            article.setLastUpdate(new Date());
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode,"Update successfully",null);
        }
        else {
            article.setWriteDate(new Date());
            article.setLastUpdate(new Date());
            //todo 处理blob
            articleMapper.insert(article);
            return APIResult.newResult(ResultCode.SuccessCode,"insert successfully",null);
        }
    }

    @Override
    public APIResult deleteById(Long id) {
        //联表删除相关的赞表
        if((articleMapper.selectByPrimaryKey(id) != null)){
            articleMapper.deleteByPrimaryKey(id);
            ThumbUpExample thumbUpExample = new ThumbUpExample();
            thumbUpExample.createCriteria().andArticleIdEqualTo(id);
            thumbUpMapper.deleteByExample(thumbUpExample);
            return APIResult.newResult(200,"Delete successfully",null);
        }
        else {
            return APIResult.newResult(400,"article not exited",null);
        }
    }

    @Override
    public APIResult findAllArticleWithAnimal() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimal();
        return APIResult.newResult(200,"Find all article with animal successfully", results);
    }

    @Override
    public APIResult<List<ArticleExtend>> findArticleWithAnimalById(Long id) {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimalById(id);
        return APIResult.newResult(200,"Find article with animal by id successfully", results);
    }

    @Override
    public APIResult findAllArticleWithLabel() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabel();
        return APIResult.newResult(200,"Find all article with label successfully", results);
    }

    @Override
    public APIResult findArticleWithLabelById(Long id) {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabelById(id);
        return APIResult.newResult(200,"Find article by id with label successfully", results);
    }

    @Override
    public APIResult findArticleWithCommentById(Long articleId) {
        return null;
    }

    @Override
    public APIResult cascadeFindAll() {
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
    public APIResult cascadeFindById(Long id) {
        return null;
    }

    @Override
    public APIResult findArticleWithTitleFuzzily(String title) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andTitleLike(title);
        List<Article> articles = articleMapper.selectByExample(example);
        if(!articles.isEmpty()){
            return APIResult.newResult(ResultCode.SuccessCode,"success" ,articles);
        } else {
            return APIResult.newResult(ResultCode.BadRequest,"can't find the article" ,null);
        }

    }
}
