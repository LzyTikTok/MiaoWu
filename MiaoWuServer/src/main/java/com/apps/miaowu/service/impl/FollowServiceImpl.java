package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.Follow;
import com.apps.miaowu.bean.FollowExample;
import com.apps.miaowu.bean.FollowExample.Criteria;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.dao.FollowMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.service.FollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowMapper followMapper;

    //关注用户
    @Override
    public APIResult addOrDelFollow(Long userId, Long fansId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFansId(fansId);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFansIdEqualTo(fansId).andUserIdEqualTo(userId);
        //找不到user和article
        if(userId == null || fansId == null){
            return APIResult.newResult(ResultCode.BadRequest,"invalid params",null);
        }
        //用户不存在
        else if(userMapper.selectByPrimaryKey(userId) == null || userMapper.selectByPrimaryKey(fansId) == null) {
            return APIResult.newResult(ResultCode.BadRequest,"Parameter Error",null);
        }
        //重复关注
        else if(!followMapper.selectByExample(followExample).isEmpty()){
            //取消关注 todo 未测试
            try {
                followMapper.deleteByExample(followExample);
                return APIResult.newResult(ResultEnum.SUCCESS, null);
            } catch (Exception e) {
                return APIResult.newResult(ResultCode.ServerInnerError, e.toString(), null);
            }
        } else{
            try{
                followMapper.insert(follow);
                return APIResult.newResult(ResultEnum.SUCCESS, follow);
            } catch (Exception e){
                return  APIResult.newResult(ResultCode.ServerInnerError,e.toString(),null);
            }
        }
    }

    @Override
    public APIResult findAllFollowsByUserId(Long userId) {
        FollowExample example = new FollowExample();
        // 找到自己关注的所有user
        example.createCriteria().andFansIdEqualTo(userId);
        List<Follow> follows = followMapper.selectByExample(example);
        ArrayList<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            users.add(userMapper.selectByPrimaryKey(follow.getUserId()));
        }
        if (users.isEmpty()) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
        return APIResult.newResult(ResultEnum.SUCCESS, users);
    }

    @Override
    public APIResult findAllFansByUserId(Long userId) {
        FollowExample example = new FollowExample();
        // 找到自己的所有粉丝
        example.createCriteria().andUserIdEqualTo(userId);
        List<Follow> follows = followMapper.selectByExample(example);
        ArrayList<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            users.add(userMapper.selectByPrimaryKey(follow.getFansId()));
        }
        if (users.isEmpty()) {
            return APIResult.newResult(ResultEnum.NO_CONTENT, null);
        }
        return APIResult.newResult(ResultEnum.SUCCESS, users);
    }
}
