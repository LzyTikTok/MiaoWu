package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.common.service.MiaoWuService;

public interface FollowService extends MiaoWuService {
    APIResult addOrDelFollow(Long userId, Long followerId);

    APIResult findAllFollowsByUserId(Long userId);

    APIResult findAllFansByUserId(Long id);
}
