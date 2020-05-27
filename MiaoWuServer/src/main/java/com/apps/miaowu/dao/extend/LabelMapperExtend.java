package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.Label;

import java.util.List;

public interface LabelMapperExtend{

    List<Label> findLabelByArticleId(Long articleId);
}