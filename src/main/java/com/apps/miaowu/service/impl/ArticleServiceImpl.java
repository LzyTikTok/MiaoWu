package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.extend.CommentExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.CommentMapper;
import com.apps.miaowu.dao.LabelMapper;
import com.apps.miaowu.dao.ThumbUpMapper;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.dao.extend.CommentMapperExtend;
import com.apps.miaowu.dao.extend.LabelMapperExtend;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.web.controller.ArticleController;
import io.swagger.annotations.Example;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger.web.ApiResourceController;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.time.LocalDateTime;
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

    @Resource
    LabelMapperExtend labelMapperExtend;

    @Resource
    CommentMapper commentMapper;

    @Resource
    CommentMapperExtend commentMapperExtend;

    @Override
    public APIResult findAll() {
        ArticleExample example = new ArticleExample();
        List<Article> results = articleMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article successfully", results);
    }

    @Override
    public APIResult findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId) {
        List<ArticleExtend> articles = articleMapperExtend.selectClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);
        if (articles.size() == 0) {
            return APIResult.newResult(ResultCode.BadRequest, "no Clip Article", null);
        } else {
            return APIResult.newResult(ResultCode.SuccessCode, "success", articles);
        }
    }

    @Override
    public APIResult saveOrUpdate(ArticleWithBLOBs article) {
        if (article.getId() != null) {
            article.setLastUpdate(new Date());
//            article.setLastUpdate(LocalDateTime.now());
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
        } else {
//            article.setWriteDate(LocalDateTime.now());
//            article.setLastUpdate(LocalDateTime.now());
            article.setWriteDate(new Date());
            article.setLastUpdate(new Date());
            //todo 处理blob
            articleMapper.insert(article);
            return APIResult.newResult(ResultCode.SuccessCode, "insert successfully", null);
        }
    }

    @Override
    public APIResult deleteById(Long id) {
        //联表删除相关的赞表
        if ((articleMapper.selectByPrimaryKey(id) != null)) {
            articleMapper.deleteByPrimaryKey(id);
            ThumbUpExample thumbUpExample = new ThumbUpExample();
            thumbUpExample.createCriteria().andArticleIdEqualTo(id);
            thumbUpMapper.deleteByExample(thumbUpExample);
            return APIResult.newResult(200, "Delete successfully", null);
        } else {
            return APIResult.newResult(400, "article not exited", null);
        }
    }

    @Override
    public APIResult findAllArticleWithAnimal() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimal();
        return APIResult.newResult(200, "Find all article with animal successfully", results);
    }

    @Override
    public APIResult<List<ArticleExtend>> findArticleWithAnimalById(Long id) {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimalById(id);
        return APIResult.newResult(200, "Find article with animal by id successfully", results);
    }

    @Override
    public APIResult findAllArticleWithLabel() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabel();
        return APIResult.newResult(200, "Find all article with label successfully", results);
    }

    @Override
    public APIResult findArticleWithLabelById(Long id) {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabelById(id);
        return APIResult.newResult(200, "Find article by id with label successfully", results);
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
        try{
//            ArticleWithBLOBs articleWithBLOBs = articleMapper.selectByPrimaryKey(id);
            Comment comment = commentMapper.selectByPrimaryKey(1L);
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andArticleIdEqualTo(id);
            List<Comment> comments = commentMapper.selectByExample(commentExample);

            ArticleExtend articleExtend = articleMapperExtend.selectArticleById(id);

            if(!comments.isEmpty()){
                List<CommentExtend> commentExtends = commentMapperExtend.findByArticleIdWithUser(id);
                articleExtend.setCommentExtends(commentExtends);
            }

            List<Label> labels = labelMapperExtend.findLabelByArticleId(id);

            articleExtend.setLabels(labels);
//         ArticleWithBLOBs articleWithBLOBs = articleMapper.selectByPrimaryKey(id);
            return APIResult.newResult(ResultCode.SuccessCode, "success", articleExtend);
        } catch (Exception e){
            System.out.println(e);
            return APIResult.newResult(ResultCode.BadRequest, "can't find the article", null);
        }
    }

    @Override
    public APIResult findArticleWithTitleFuzzily(String title) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andTitleLike(title);
        List<Article> articles = articleMapper.selectByExample(example);
        if (!articles.isEmpty()) {
            return APIResult.newResult(ResultCode.SuccessCode, "success", articles);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "can't find the article", null);
        }

    }

    @Override
    public APIResult thumbUpOrDown(Long articleId, Long userId) {
        //不清楚这样写会不会擦除文章原有的信息
        //点赞
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        Article article = articleMapper.selectByPrimaryKey(articleId);
        ThumbUp find = new ThumbUp();

        for (ThumbUp thumbUp : thumbUps) {
            if (thumbUp.getArticleId().equals(articleId)) {
                find = thumbUp;
                break;
            }
        }
        if (find.getId() == null) {
            ThumbUp thumbUp = new ThumbUp();
            thumbUp.setArticleId(articleId);
            thumbUp.setUserId(userId);
            thumbUpMapper.insert(thumbUp);
            //部分更新
            if (article == null) {
                return APIResult.newResult(ResultCode.BadRequest, "article not exist", null);
//                return "不存在该文章";
            }
            if (article.getThumbUp() == null) {
                article.setThumbUp(1L);
            } else {
                article.setThumbUp(article.getThumbUp() + 1L);
            }
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode, "ThumbUp successfully", null);
//            return "点赞成功";
        } else {
            //首先要把thumbUp表中的删除掉，但是上文并没有获取到准确的字段。
            thumbUpMapper.deleteByPrimaryKey(find.getId());
            //此处判断点赞数为不为null，主要是防黑客，系统运行正常下，此时的点赞数必定≥1
            if (article.getThumbUp() != null) {
                article.setThumbUp(article.getThumbUp() - 1L);
                articleMapper.updateByPrimaryKey(article);
                return APIResult.newResult(ResultCode.ThumbDownCode, "ThumbDown successfully", null);
//                return "取消点赞成功";
            } else {
                return APIResult.newResult(ResultCode.ServerInnerError, "Server Inner Error, thumb up number is zero", null);
//                return "数据库异常，此时的点赞数为0";
            }
        }
    }

    @Override
    public APIResult addArticle(ArticleWithBLOBs article) {
//        ArticleWithBLOBs articleWithBLOBs = articleMapper.selectByPrimaryKey(article.getId());
//        if(articleWithBLOBs != null){
        article.setWriteDate(new Date());
        article.setLastUpdate(new Date());
        article.setThumbUp(0L);
        articleMapper.insert(article);
        //todo 处理blob
        return APIResult.newResult(ResultCode.SuccessCode, "insert successfully", article.getId());
//        }
//        else{
//            return APIResult.newResult(ResultCode.BadRequest,"article exists",null);
//        }
    }

    @Override
    public APIResult updateArticle(ArticleWithBLOBs article) {
        article.setLastUpdate(new Date());
//            article.setLastUpdate(LocalDateTime.now());
        articleMapper.updateByPrimaryKeySelective(article);
        return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
    }

    @Override
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId) {
        ArticleExample example = new ArticleExample();
        List<ArticleExtend> results = articleMapperExtend.selectAllByUserIdOrderByUpdateDesc(userId);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article successfully", results);
    }

    @Override
    public APIResult findArticleByAuthorIdOrderByUpdateDesc(Long authorId) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andAuthorIdEqualTo(authorId);
        List<Article> lists = articleMapper.selectByExample(example);
        if(!lists.isEmpty()){
            return APIResult.newResult(ResultCode.SuccessCode, "success", lists);
        } else{
            return APIResult.newResult(ResultCode.BadRequest, "no artilces", null);
        }
    }
}
