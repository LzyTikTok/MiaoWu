package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Follow;
import com.apps.miaowu.bean.FollowExample;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.FollowMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.service.FollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowMapper followMapper;

    @Override
    public APIResult addFollow(Long userId, Long fansId) {
        //todo 参照此模板进行其他Service类的异常情况处理
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFansId(fansId);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFansIdEqualTo(fansId).andUserIdEqualTo(userId);
        //找不到user和article
        if(userId == null || fansId == null){
            return APIResult.newResult(ResultCode.BadRequest,"Parameter missing",null);
        }
        //用户不存在
        else if(userMapper.selectByPrimaryKey(userId) == null || userMapper.selectByPrimaryKey(fansId) == null) {
            return APIResult.newResult(ResultCode.BadRequest,"Parameter Error",null);
        }
        //重复关注
        else if(followMapper.selectByExample(followExample) != null){
            return APIResult.newResult(ResultCode.BadRequest,"Already follow",null);
        } else{
            try{
                followMapper.insert(follow);
                return APIResult.newResult(ResultCode.SuccessCode, "Add follow successfully", follow);
            } catch (Exception e){
                return  APIResult.newResult(ResultCode.ServerInnerError,e.toString(),null);
            }
        }
    }

    @Override
    public APIResult deleteFollow(Long userId, Long fansId) {
        //todo userId和fansId为空的判断
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(userId).andFansIdEqualTo(fansId);
        if(followMapper.selectByExample(followExample) != null){
            try {
                followMapper.deleteByExample(followExample);
                return APIResult.newResult(ResultCode.SuccessCode, "Cancel follow successfully", null);
            } catch (Exception e) {
                return APIResult.newResult(ResultCode.ServerInnerError, e.toString(), null);
            }
        } else {
            return APIResult.newResult(ResultCode.BadRequest, "follow not exist", null);
        }
    }

    //todo findAllFollow
}
