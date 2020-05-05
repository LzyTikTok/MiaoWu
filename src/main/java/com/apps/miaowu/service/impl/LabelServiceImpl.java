package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.ArticleLabel;
import com.apps.miaowu.bean.Label;
import com.apps.miaowu.bean.LabelExample;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ArticleLabelMapper;
import com.apps.miaowu.dao.LabelMapper;
import com.apps.miaowu.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Resource
    LabelMapper labelMapper;

    @Resource
    ArticleLabelMapper articleLabelMapper;

    @Override
    public APIResult addLabels(Long articleId, String[] labelNames) {
        for (String labelName:
                labelNames ) {
            LabelExample labelExample =  new LabelExample();
            labelExample.createCriteria().andNameEqualTo(labelName);
            List<Label> labels = labelMapper.selectByExample(labelExample);
            //无此标签，加入
            if(labels.isEmpty()){
                Label label = new Label();
                label.setName(labelName);
                //新增标签
                labelMapper.insert(label);
                //联表关联
                ArticleLabel articleLabel = new ArticleLabel();
                articleLabel.setArticleId(articleId);
                articleLabel.setLabelId(label.getId());
                articleLabelMapper.insert(articleLabel);
                return APIResult.newResult(ResultCode.SuccessCode,"add Label successfully",null);
            } else {
                ArticleLabel articleLabel = new ArticleLabel();
                articleLabel.setArticleId(articleId);
                articleLabel.setLabelId(labels.get(0).getId());
                articleLabelMapper.insert(articleLabel);
                return APIResult.newResult(ResultCode.SuccessCode,"add Label successfully",null);
            }
        }
        return APIResult.newResult(ResultCode.BadRequest,"labelNames is empty",null);

    }

}
