package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;

public interface FollowService {
    APIResult addFollow(Long userId, Long followerId);

    APIResult deleteFollow(Long userId, Long followerId);
}
