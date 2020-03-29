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
    public List<Article> findAll() {
        ArticleExample example = new ArticleExample();
        //todo 返回APIResult
        return articleMapper.selectByExample(example);
    }

    @Override
    public APIResult saveOrUpdate(Article article) {
        if(article.getId() != null){
            article.setLastUpdate(new Date());
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode,"Update successfully",null);
        }
        else {
            article.setWriteDate(new Date());
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
