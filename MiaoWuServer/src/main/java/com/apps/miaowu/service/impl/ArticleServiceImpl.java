package com.apps.miaowu.service.impl;

import com.alibaba.fastjson.JSON;
import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.ArticleExtend;
import com.apps.miaowu.bean.extend.CommentExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.common.exception.MiaowuException;
import com.apps.miaowu.constant.NormalConstant;
import com.apps.miaowu.dao.*;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.dao.extend.CommentMapperExtend;
import com.apps.miaowu.dao.extend.LabelMapperExtend;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.common.utils.token.TokenHelper;
import com.apps.miaowu.common.utils.token.TokenModel;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Resource
    ArticleMapperExtend articleMapperExtend;

    @Resource
    ThumbUpMapper thumbUpMapper;

    @Resource
    LabelMapperExtend labelMapperExtend;

    @Resource
    CommentMapperExtend commentMapperExtend;


    @Resource
    UserMapper userMapper;

    @Resource
    private TokenHelper tokenHelper;

    @Resource
    private RestHighLevelClient client;

    @Override
    public APIResult findAll() {
        ArticleExample example = new ArticleExample();
        List<Article> results = articleMapper.selectByExample(example);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(Long userId) {
        List<ArticleExtend> articles = articleMapperExtend
                .selectClipArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);
        if (articles.size() == 0) {
            return APIResult.newResult(ResultEnum.BAD_REQUEST, null);
        } else {
            return APIResult.newResult(ResultEnum.SUCCESS, articles);
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
            return APIResult.newResult(ResultEnum.SUCCESS, null);
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "article not exited", null);
        }
    }

    @Override
    public APIResult findAllArticleWithAnimal() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimal();
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }


    @Override
    public APIResult<List<ArticleExtend>> findArticleWithAnimalById(Long id) {
        //参数空值校验
        try {
            varifyNullParam(id);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        List<ArticleExtend> results = articleMapperExtend.selectArticleWithAnimalById(id);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findAllArticleWithLabel() {
        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabel();
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findArticleWithLabelById(Long id) {
        //参数空值校验
        try {
            varifyNullParam(id);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        List<ArticleExtend> results = articleMapperExtend.selectArticleWithLabelById(id);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findArticleWithCommentById(Long articleId) {
        //todo 未完成
        return APIResult.newResult(ResultEnum.NO_CONTENT, null);
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
        return APIResult.newResult(ResultEnum.NO_CONTENT, null);

    }

    @Override
    public APIResult cascadeFindById(Long id) {
        //参数空值校验
        try {
            varifyNullParam(id);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        HashMap res = new HashMap<String, Object>();
        //文章信息
        Article article = articleMapper.selectByPrimaryKey(id);
        res.put("article", article);

        //文章空值校验
        try {
            varifyNullParam(article);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }

        //评论信息
        List<CommentExtend> commentExtends = commentMapperExtend.findByArticleIdWithUser(id);
        res.put("comments", commentExtends);

        //标签信息
        List<Label> labels = labelMapperExtend.findLabelByArticleId(id);
        res.put("labels", labels);

        //用户信息
        User user = userMapper.selectByPrimaryKey(article.getAuthorId());
        user.setPassword(null);
        user.setIdCode(null);
        user.setPhone(null);
        res.put("author", user);

        return APIResult.newResult(ResultEnum.SUCCESS, res);

    }

    @Override
    public APIResult findArticleByKeyAndValueFuzzily(String key, String value) {
        SearchRequest searchRequest = new SearchRequest("article");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = client.search(searchRequest);
            return APIResult.newResult(ResultEnum.SUCCESS, JSON.toJSONString(response.getHits()));
//            String id = JsonUtils.getArrayColumns(response.toString(), "hits.hits._source", "id");
//            System.out.println(id);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public APIResult thumbUpOrDown(Long articleId, Long userId) {
        //参数空值校验
        try {
            varifyNullParam(articleId, userId);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
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

            //参数空值校验
            try {
                varifyNullParam(article);
            } catch (MiaowuException miaowuException) {
                return APIResult.newResult(ResultEnum.NO_CONTENT, null);
            }

            //初始化文章点赞
            if (article.getThumbUp() == null) {
                article.setThumbUp(1L);
            } else {
                article.setThumbUp(article.getThumbUp() + 1L);
            }
            //更新文章表
            articleMapper.updateByPrimaryKey(article);
            return APIResult.newResult(ResultEnum.SUCCESS, article.getThumbUp() + 1L);
        } else { //用户已点赞过该文章，取消点赞
            thumbUpMapper.deleteByPrimaryKey(find.getId());

            // 此处判断点赞数为不为null，主要是防黑客，系统运行正常下，此时的点赞数必定≥1
            if (article.getThumbUp() != null) {
                //点赞数-1
                article.setThumbUp(article.getThumbUp() - 1L);
                articleMapper.updateByPrimaryKey(article);
                return APIResult.newResult(ResultEnum.SUCCESS,article.getThumbUp() - 1L);
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
        return APIResult.newResult(ResultEnum.SUCCESS, article.getId());
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
        if (!Long.valueOf(userId).equals(article.getAuthorId())) {
            return APIResult.newResult(ResultEnum.UNAUTH, null);
        }

        //参数空值校验
        try {
            varifyNullParam(article);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        article.setLastUpdate(new Date());
        // article.setLastUpdate(LocalDateTime.now());
        articleMapper.updateByPrimaryKeySelective(article);
        return APIResult.newResult(ResultEnum.SUCCESS, null);
    }

    @Override
    public APIResult findAllWithClipByUserIdOrderByUpdateDesc(Long userId) {
        //参数空值校验
        try {
            varifyNullParam(userId);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        ArticleExample example = new ArticleExample();
        List<ArticleExtend> results = articleMapperExtend.selectAllByUserIdOrderByUpdateDesc(userId);
        return APIResult.newResult(ResultEnum.SUCCESS, results);
    }

    @Override
    public APIResult findArticleByAuthorIdOrderByUpdateDesc(Long authorId) {
        //参数空值校验
        try {
            varifyNullParam(authorId);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        ArticleExample example = new ArticleExample();
        example.createCriteria().andAuthorIdEqualTo(authorId);
        List<Article> lists = articleMapper.selectByExample(example);
        if (!lists.isEmpty()) {
            return APIResult.newResult(ResultEnum.SUCCESS, lists);
        } else {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
    }

    // todo 未测试
    @Override
    public APIResult findFollowsArticleByUserIdOrderByUpdateDesc(Long userId) {
        //参数空值校验
        try {
            varifyNullParam(userId);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }

        User user = userMapper.selectByPrimaryKey(userId);
        List<ArticleExtend> articleExtends = articleMapperExtend
                .selectFollowsArticleWithAuthorNameByUserIdOrderByUpdateDesc(userId);

        //参数空值校验
        try {
            varifyNullParam(user, articleExtends);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }

        return APIResult.newResult(ResultEnum.SUCCESS, articleExtends);
    }

    @Override
    public APIResult findById(Long articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if (article != null) {
            return APIResult.newResult(ResultEnum.SUCCESS, article);
        } else {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
    }

    @Override
    public APIResult findArticleWithLabelByPage(Integer count, Integer page) {
        //参数空值校验
        try {
            varifyNullParam(count,page);
        } catch (MiaowuException miaowuException) {
            return APIResult.newResult(ResultEnum.ILLEGAL_PARAM, null);
        }
        Integer start = count * (page - 1);
        Integer end = count * (page);
        List<ArticleExtend> articleExtends = articleMapperExtend.selectArticleWithLabelByPage(start, end);
        return APIResult.newResult(ResultEnum.SUCCESS, articleExtends);
    }
}
