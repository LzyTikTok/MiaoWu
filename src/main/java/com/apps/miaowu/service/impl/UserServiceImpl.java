package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.*;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.dao.ClipMapper;
import com.apps.miaowu.dao.FollowMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.dao.extend.UserMapperExtend;
import com.apps.miaowu.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMapperExtend userMapperExtend;

    @Resource
    private ClipMapper clipMapper;

    @Resource
    private FollowMapper followMapper;

    @Override
    public APIResult findAll() {
        UserExample example = new UserExample();
        List<User> results = userMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user successfully", results);
    }

    @Override
    public APIResult saveOrUpdate(User user) {
        //todo 检查是否逻辑有错
        //update
        //此处有错，应该连接数据库判断是否有字段存在，而不是直接getid
        if (user.getId() != null) {
            Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$");
            Matcher m = p.matcher(user.getPhone());
            if (!m.matches()) {
                //密码必须在8位以上且至少包含密码和字母
                return APIResult.newResult(400, "Illegal password", null);
            }
            //用户修改信息，此时进行密码的判断
            userMapper.updateByPrimaryKey(user);
        }
        //save
        else {
            //        中国电信号段 133、149、153、173、177、180、181、189、199
//        中国联通号段 130、131、132、145、155、156、166、175、176、185、186
//        中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
//        其他号段
//        14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
//        虚拟运营商
//        电信：1700、1701、1702
//        移动：1703、1705、1706
//        联通：1704、1707、1708、1709、171
//        卫星通信：1349
//————————————————
//        版权声明：本文为CSDN博主「yuongxi」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/m18860232520/article/details/79396889
            Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$");
            Matcher m = p.matcher(user.getPhone());
            if (!m.matches()) {
                return APIResult.newResult(400, "Phonenumber illegal ", null);
            }
            user.setCreateDate(new Date());
            userMapper.insert(user);
        }
        return APIResult.newResult(ResultCode.SuccessCode, "Update successfully", null);
    }

    @Override
    public APIResult login(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(user.getPhone());
        List<User> users = userMapper.selectByExample(example);
        System.out.println(users.get(0).getPhone() + " " + users.get(0).getPassword());
        if (users.size() == 0) {
            return APIResult.newResult(500, "User not exist", null);
        } else if (!user.getPassword().equals(users.get(0).getPassword())) {
            return APIResult.newResult(400, "Incorrect password", null);
        } else return APIResult.newResult(ResultCode.SuccessCode, "Login successfully", null);

    }

    @Override
    public APIResult findById(Long id) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        List<User> users = userMapper.selectByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user successfully", null);
    }

    @Override
    public APIResult findAllUserWithFound() {
        List<UserExtend> results = userMapperExtend.selectUserWithFound();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all with found successfully", results);
    }

    @Override
    public APIResult findUserWithFoundById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithFoundById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find user with found by id successfully", results);
    }

    @Override
    public APIResult findAllUserWithSave() {
        List<UserExtend> results = userMapperExtend.selectUserWithSave();
        return APIResult.newResult(ResultCode.SuccessCode, "Find all user with save successfully", results);
    }

    @Override
    public APIResult findUserWithSaveById(Long id) {
        List<UserExtend> results = userMapperExtend.selectUserWithSaveById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Find user with save by id successfully", results);
    }

    @Override
    public APIResult cascadeFindAllUser() {
        List<UserExtend> results = userMapperExtend.cascadeFindAllUser();
        return APIResult.newResult(ResultCode.SuccessCode, "Cascade find all user successfully", results);
    }

    @Override
    public APIResult cascadeFindUserById(Long id) {
        List<UserExtend> results = userMapperExtend.cascadeFindUserById(id);
        return APIResult.newResult(ResultCode.SuccessCode, "Cascade find user by id successfully", results);
    }

    @Override
    public APIResult deleteUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            userMapper.deleteByPrimaryKey(id);
            return APIResult.newResult(ResultCode.SuccessCode, "Delete user by id successfully", null);
        } else {
            return APIResult.newResult(500, "can't find the uesr", null);
        }
    }

    @Override
    public APIResult addClipArticle(Long userId, Long articleId) {
        // todo 一些意外情况的处理
        Clip clip = new Clip();
        clip.setArticleId(articleId);
        clip.setUserId(userId);
        clipMapper.insert(clip);
        return APIResult.newResult(ResultCode.SuccessCode, "Clip article successfully", clip);
    }
    //todo 查询所有收藏的文章


    @Override
    public APIResult deleteClipArticle(Long userId, Long articleId) {
        // todo 一些意外情况的处理
        ClipExample example = new ClipExample();
        example.createCriteria().andArticleIdEqualTo(articleId).andUserIdEqualTo(userId);
        clipMapper.deleteByExample(example);
        return APIResult.newResult(ResultCode.SuccessCode, "Delete clip article successfully", null);
    }

    @Override
    public APIResult addFollow(Long userId, Long fansId) {
        //todo 参照此模板进行其他Service类的异常情况处理
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFansId(fansId);
        //找不到user和article
        if(userId == null || fansId == null){
            return APIResult.newResult(ResultCode.BadRequest,"Parameter missing",null);
        }
        if(userMapper.selectByPrimaryKey(userId) == null || userMapper.selectByPrimaryKey(fansId) == null) {
            return APIResult.newResult(ResultCode.BadRequest,"Parameter Error",null);
        }
        try{
            followMapper.insert(follow);
            return APIResult.newResult(ResultCode.SuccessCode, "Add follow successfully", follow);
        } catch (Exception e){
            return  APIResult.newResult(ResultCode.ServerInnerError,e.toString(),null);
        }
    }

    @Override
    public APIResult deleteFollow(Long userId, Long fansId) {
        FollowExample followExample = new FollowExample();
        try {
            followExample.createCriteria().andUserIdEqualTo(userId).andFansIdEqualTo(fansId);
            return APIResult.newResult(ResultCode.SuccessCode, "Cancel follow successfully", null);
        } catch (Exception e) {
            return APIResult.newResult(ResultCode.ServerInnerError, e.toString(), null);
        }
    }
}
