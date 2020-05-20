package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.extend.CommentExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.*;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.dao.extend.CommentMapperExtend;
import com.apps.miaowu.dao.extend.LabelMapperExtend;
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

    @Resource
    ThumbUpMapper thumbUpMapper;

    @Resource
    LabelMapperExtend labelMapperExtend;

    @Resource
    CommentMapper commentMapper;

    @Resource
    CommentMapperExtend commentMapperExtend;

    @Resource
    FollowMapper followMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public APIResult findAll() {
        ArticleExample example = new ArticleExample();
        List<Article> results = articleMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article successfully", results);
    }

    @Override
    public APIResult findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId) {
        List<ArticleExtend> articles = articleMapperExtend
                .selectClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);
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
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
        } else {
            article.setWriteDate(new Date());
            article.setLastUpdate(new Date());
            // todo 处理blob
            articleMapper.insert(article);
            return APIResult.newResult(ResultCode.SuccessCode, "insert successfully", null);
        }
    }

    @Override
    public APIResult deleteById(Long id) {
        // 联表删除相关的赞表
        if ((articleMapper.selectByPrimaryKey(id) != null)) {
            articleMapper.deleteByPrimaryKey(id);
            ThumbUpExample thumbUpExample = new ThumbUpExample();
            thumbUpExample.createCriteria().andArticleIdEqualTo(id);
            thumbUpMapper.deleteByExample(thumbUpExample);
            return APIResult.newResult(ResultCode.SuccessCode, "Delete successfully", null);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "article not exited", null);
        }
    }

    @Override
    public APIResult findAllArticleWithAnimal() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimal();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article with animal successfully", results);
    }

    @Override
    public APIResult<List<ArticleExtend>> findArticleWithAnimalById(Long id) {
        if (id == null) {
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimalById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find article with animal by id successfully", results);
    }

    @Override
    public APIResult findAllArticleWithLabel() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabel();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article with label successfully", results);
    }

    @Override
    public APIResult findArticleWithLabelById(Long id) {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabelById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find article by id with label successfully", results);
    }

    @Override
    public APIResult findArticleWithCommentById(Long articleId) {
        return null;
    }

    @Override
    public APIResult cascadeFindAll() {
        // 此处应该不这么写
        // List<ArticleExtend> articleExtends = articleMapperExtend.cascadeFindAll();
        // for(ArticleExtend articleExtend : articleExtends){
        // List<ArticleLabel> articleLabels = articleExtend.getArticleLabels();
        // for(ArticleLabel articleLabel : articleLabels){
        // labelMapper.selectByPrimaryKey(articleLabel.getId());
        // }
        //
        // }
        return null;
    }

    @Override
    public APIResult cascadeFindById(Long id) {
        try {
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andArticleIdEqualTo(id);
            List<Comment> comments = commentMapper.selectByExample(commentExample);

            ArticleExtend articleExtend = articleMapperExtend.selectArticleById(id);

            if (!comments.isEmpty()) {
                List<CommentExtend> commentExtends = commentMapperExtend.findByArticleIdWithUser(id);
                articleExtend.setCommentExtends(commentExtends);
            }

            List<Label> labels = labelMapperExtend.findLabelByArticleId(id);

            articleExtend.setLabels(labels);
            User user = userMapper.selectByPrimaryKey(articleExtend.getAuthorId());
            articleExtend.setUser(user);

            return APIResult.newResult(ResultCode.SuccessCode, "success", articleExtend);
        } catch (Exception e) {
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
        if(articleId == null || userId == null){
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }
        // 不清楚这样写会不会擦除文章原有的信息
        // 点赞
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
            // 部分更新
            if (article == null) {
                return APIResult.newResult(ResultCode.BadRequest, "article not exist", null);
            }
            if (article.getThumbUp() == null) {
                article.setThumbUp(1L);
            } else {
                article.setThumbUp(article.getThumbUp() + 1L);
            }
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode, "ThumbUp successfully", null);
        } else {
            // 首先要把thumbUp表中的删除掉，但是上文并没有获取到准确的字段。
            thumbUpMapper.deleteByPrimaryKey(find.getId());
            // 此处判断点赞数为不为null，主要是防黑客，系统运行正常下，此时的点赞数必定≥1
            if (article.getThumbUp() != null) {
                article.setThumbUp(article.getThumbUp() - 1L);
                articleMapper.updateByPrimaryKey(article);
                return APIResult.newResult(ResultCode.CancelSuccessCode, "ThumbDown successfully", null);
            } else {
                return APIResult.newResult(ResultCode.ServerInnerError, "Server Inner Error, thumb up number is zero",
                        null);
            }
        }
    }

    @Override
    public APIResult addArticle(ArticleWithBLOBs article) {
        if(article == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        // ArticleWithBLOBs articleWithBLOBs =
        // articleMapper.selectByPrimaryKey(article.getId());
        // if(articleWithBLOBs != null){
        article.setWriteDate(new Date());
        article.setLastUpdate(new Date());
        article.setThumbUp(0L);
        articleMapper.insert(article);
        // todo 处理blob
        return APIResult.newResult(ResultCode.SuccessCode, "insert successfully", article.getId());
        // }
        // else{
        // return APIResult.newResult(ResultCode.BadRequest,"article exists",null);
        // }
    }

    @Override
    public APIResult updateArticle(ArticleWithBLOBs article) {
        if(article == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        article.setLastUpdate(new Date());
        // article.setLastUpdate(LocalDateTime.now());
        articleMapper.updateByPrimaryKeySelective(article);
        return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
    }

    @Override
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId) {
        if(userId == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        ArticleExample example = new ArticleExample();
        List<ArticleExtend> results = articleMapperExtend.selectAllByUserIdOrderByUpdateDesc(userId);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all article successfully", results);
    }

    @Override
    public APIResult findArticleByAuthorIdOrderByUpdateDesc(Long authorId) {
        if(authorId == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        ArticleExample example = new ArticleExample();
        example.createCriteria().andAuthorIdEqualTo(authorId);
        List<Article> lists = articleMapper.selectByExample(example);
        if (!lists.isEmpty()) {
            return APIResult.newResult(ResultCode.SuccessCode, "success", lists);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "no articles", null);
        }
    }

    // todo 未测试
    @Override
    public APIResult findFollowsArticleByUserIdOrderByUpdateDesc(Long userId) {
        if(userId == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        if (userMapper.selectByPrimaryKey(userId) == null) {
            return APIResult.newResult(ResultCode.BadRequest, "user not exist", null);
        }
        List<ArticleExtend> articleExtends = articleMapperExtend
                .selectFollowsArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);
        if (articleExtends.isEmpty()) {
            return APIResult.newResult(ResultCode.BadRequest, "no articles", null);
        }
        return APIResult.newResult(ResultCode.SuccessCode, "success", articleExtends);
    }
}
