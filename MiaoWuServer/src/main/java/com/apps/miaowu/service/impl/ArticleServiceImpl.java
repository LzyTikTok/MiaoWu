package com.apps.miaowu.service.impl;

import com.alibaba.fastjson.JSON;
import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.extend.CommentExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.constant.NormalConstant;
import com.apps.miaowu.dao.*;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.dao.extend.CommentMapperExtend;
import com.apps.miaowu.dao.extend.LabelMapperExtend;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.utils.token.TokenHelper;
import com.apps.miaowu.utils.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private TokenHelper tokenHelper;

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
        if(id == null){
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }

        HashMap res = new HashMap<String, Object>();
        try {
            //文章信息
            Article article = articleMapper.selectByPrimaryKey(id);
            res.put("article",article);

            //评论信息
            List<CommentExtend> commentExtends = commentMapperExtend.findByArticleIdWithUser(id);
            res.put("comments",commentExtends);

            //标签信息
            List<Label> labels = labelMapperExtend.findLabelByArticleId(id);
            res.put("labels", labels);

            //用户信息
            User user = userMapper.selectByPrimaryKey(article.getAuthorId());
            user.setPassword(null);
            user.setIdCode(null);
            user.setPhone(null);
            res.put("author", user);

//            String jsonString = JSON.toJSONString(res);

            return APIResult.newResult(ResultCode.SuccessCode, "success", res);
        } catch (Exception e) {
            System.out.println(e);
            return APIResult.newResult(ResultCode.BadRequest, "can't find the article", null);
        }
    }

    @Override
    public APIResult findArticleWithTitleFuzzily(String title) {
        ArticleExample example = new ArticleExample();
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(title);
        sb.append("%");
        title = sb.toString();
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
        //请求不合法
        if (articleId == null || userId == null) {
            return APIResult.newResult(ResultCode.BadRequest, "params invalid", null);
        }

        //获取用户所有的点赞信息
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        //获取文章信息
        Article article = articleMapper.selectByPrimaryKey(articleId);
        ThumbUp find = new ThumbUp();

        //查找该用户是否已经点赞过文章
        for (ThumbUp thumbUp : thumbUps) {
            if (thumbUp.getArticleId().equals(articleId)) {
                find = thumbUp;
                break;
            }
        }

        //用户未点赞过该文章
        if (find.getId() == null) {
            //插入点赞信息
            ThumbUp thumbUp = new ThumbUp();
            thumbUp.setArticleId(articleId);
            thumbUp.setUserId(userId);
            thumbUpMapper.insert(thumbUp);

            if (article == null) {
                return APIResult.newResult(ResultCode.BadRequest, "article not exist", null);
            }

            //初始化文章点赞
            if (article.getThumbUp() == null) {
                article.setThumbUp(1L);
            } else {
                article.setThumbUp(article.getThumbUp() + 1L);
            }
            //更新文章表
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultCode.SuccessCode, "ThumbUp successfully", null);
        } else { //用户已点赞过该文章，取消点赞
            thumbUpMapper.deleteByPrimaryKey(find.getId());
            // 此处判断点赞数为不为null，主要是防黑客，系统运行正常下，此时的点赞数必定≥1
            if (article.getThumbUp() != null) {
                //点赞数-1
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
        if (article == null) {
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        // ArticleWithBLOBs articleWithBLOBs =
        // articleMapper.selectByPrimaryKey(article.getId());
        // if(articleWithBLOBs != null){
        article.setWriteDate(new Date());
        article.setLastUpdate(new Date());
        article.setThumbUp(0L);
        articleMapper.insert(article);
        return APIResult.newResult(ResultCode.SuccessCode, "insert successfully", article.getId());
        // }
        // else{
        // return APIResult.newResult(ResultCode.BadRequest,"article exists",null);
        // }
    }

    @Override
    public APIResult updateArticle(HttpServletRequest request, ArticleWithBLOBs article) {
        // token验证
        String authStr = request.getHeader(NormalConstant.AUTHORIZATION);
        TokenModel model = tokenHelper.get(authStr);
        String userId = model.getUserId();
        if (Long.valueOf(userId) != article.getAuthorId()) {
            return APIResult.newResult(ResultCode.Unauthorized, "Unauthorized", null);
        }
        if (article == null) {
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        article.setLastUpdate(new Date());
        // article.setLastUpdate(LocalDateTime.now());
        articleMapper.updateByPrimaryKeySelective(article);
        return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
    }

    @Override
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId) {
        if (userId == null) {
            return APIResult.newResult(ResultCode.BadRequest, "invalid params", null);
        }
        ArticleExample example = new ArticleExample();
        List<ArticleExtend> results = articleMapperExtend.selectAllByUserIdOrderByUpdateDesc(userId);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all clip article successfully", results);
    }

    @Override
    public APIResult findArticleByAuthorIdOrderByUpdateDesc(Long authorId) {
        if (authorId == null) {
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
        if (userId == null) {
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

    @Override
    public APIResult findById(Long articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if (article != null) {
            return APIResult.newResult(ResultCode.SuccessCode, "success", article);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "no Article", null);
        }
    }

    @Override
    public APIResult findArticleWithLabelByPage(Integer count, Integer page) {
        Integer start = count * (page - 1);
        Integer end = count * (page);
        List<ArticleExtend> articleExtends = articleMapperExtend.selectArticleWithLabelByPage(start, end);
        return APIResult.newResult(ResultCode.SuccessCode, "success", articleExtends);
    }
}
