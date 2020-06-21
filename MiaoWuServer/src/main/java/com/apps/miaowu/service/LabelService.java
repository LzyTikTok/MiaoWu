package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;

public interface LabelService {

    APIResult addLabels(Long articleId, String[] LabelName);
}
