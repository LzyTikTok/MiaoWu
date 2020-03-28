package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Article;
import com.apps.miaowu.bean.ThumbUp;
import com.apps.miaowu.bean.ThumbUpExample;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.ThumbUpMapper;
import com.apps.miaowu.service.ThumbUpService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ThumbUpServiceImpl implements ThumbUpService {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    ThumbUpMapper thumbUpMapper;

    //不清楚这样写会不会擦除文章原有的信息
    @Override
    public String thumbUpOrDown(Long articleId, Long userId) {
        //点赞
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ThumbUp> thumbUps = thumbUpMapper.selectByExample(example);

        Article article = articleMapper.selectByPrimaryKey(articleId);
        ThumbUp find = new ThumbUp();

        for(ThumbUp thumbUp : thumbUps){
            if (thumbUp.getArticleId().equals(articleId)){
                find = thumbUp;
                break;
            }
        }
        if(find.getId() == null){
            ThumbUp thumbUp = new ThumbUp();
            thumbUp.setArticleId(articleId);
            thumbUp.setUserId(userId);
            thumbUpMapper.insert(thumbUp);
            //部分更新
            if(article == null){
                return "不存在该文章";
            }
            if(article.getThumpUp() == null){
                article.setThumpUp(1L);
            } else {
                article.setThumpUp(article.getThumpUp() + 1L);
            }
            articleMapper.updateByPrimaryKey(article);
            return "点赞成功";
        } else {
            //首先要把thumbUp表中的删除掉，但是上文并没有获取到准确的字段。
            thumbUpMapper.deleteByPrimaryKey(find.getId());
            //此处判断点赞数为不为null，主要是防黑客，系统运行正常下，此时的点赞数必定≥1
            if(article.getThumpUp() != null){
                article.setThumpUp(article.getThumpUp() - 1L);
                articleMapper.updateByPrimaryKey(article);
                return "取消点赞成功";
            } else {
                return "数据库异常，此时的点赞数为0";
            }
        }
    }
}
