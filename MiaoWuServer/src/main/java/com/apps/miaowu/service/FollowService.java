package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;

public interface FollowService {
    APIResult addOrDelFollow(Long userId, Long followerId);

    APIResult findAllFollowsByUserId(Long userId);

    APIResult findAllFansByUserId(Long id);
}
