package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;

public interface ClipService {
    APIResult addClipArticle(Long userId, Long articleId);

    APIResult deleteClipArticle(Long userId, Long articleId);

    APIResult findAllClipArticleDesc(Long userId);
}
