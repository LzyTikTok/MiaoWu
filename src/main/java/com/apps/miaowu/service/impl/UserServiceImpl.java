package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.UserExample;
import com.apps.miaowu.bean.extend.UserExtend;
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

    @Override
    public List<User> findAll() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    @Override
    public String saveOrUpdate(User user) {
        //update
        //此处有错，应该连接数据库判断是否有字段存在，而不是直接getid
        if(user.getId() != null){
            Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$");
            Matcher m = p.matcher(user.getPhone());
            if(!m.matches()){
                return "密码必须在8位以上且至少包含密码和字母";
            }
            //用户修改信息，此时进行密码的判断
            userMapper.updateByPrimaryKey(user);

        }
        //save
        else{
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
            if(!m.matches()){
                return "手机号码不合法";
            }
            user.setCreateDate(new Date());
            userMapper.insert(user);
        }
        return "更新成功";
    }

    @Override
    public String login(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(user.getPhone());
        List<User> users = userMapper.selectByExample(example);
        System.out.println(users.get(0).getPhone() + " " + users.get(0).getPassword());
        if(users.size() == 0){
            return "不存在该用户";
        }
        else if(!user.getPassword().equals(users.get(0).getPassword())){
            return "密码错误";
        }
        else return "登录成功";
    }

    @Override
    public List<User> findById(Long id) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public List<UserExtend> findAllUserWithFound() {
        return userMapperExtend.selectUserWithFound();
    }

    @Override
    public List<UserExtend> findUserWithFoundById(Long id) {
        return userMapperExtend.selectUserWithFoundById(id);
    }

    @Override
    public List<UserExtend> findAllUserWithSave() {
        return userMapperExtend.selectUserWithSave();
    }

    @Override
    public List<UserExtend> findUserWithSaveById(Long id) {
        return userMapperExtend.selectUserWithSaveById(id);
    }

    @Override
    public List<UserExtend> cascadeFindAllUser() { return userMapperExtend.cascadeFindAllUser(); }

    @Override
    public List<UserExtend> cascadeFindUserById(Long id) {
        return userMapperExtend.cascadeFindUserById(id);
    }
}
