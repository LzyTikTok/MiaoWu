package com.apps.miaowu.dao.extend;

import com.apps.miaowu.bean.extend.CommentExtend;

import java.util.List;

public interface CommentMapperExtend {
    List<CommentExtend> findByArticleIdWithUser(Long articleId);
}
