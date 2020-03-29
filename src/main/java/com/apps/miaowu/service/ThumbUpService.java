package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;

public interface ThumbUpService {
    APIResult thumbUpOrDown(Long articleId, Long userId);
}
