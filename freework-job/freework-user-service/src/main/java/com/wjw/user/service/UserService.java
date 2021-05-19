package com.wjw.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.common.utils.JwtUtils;
import com.wjw.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjw.user.entity.vo.LoginVo;
import com.wjw.user.entity.vo.registerVO;
import com.wjw.user.mapper.UserMapper;
import com.wjw.user.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjw
 * @since 2021-05-19
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String login(LoginVo loginVo) {
        String mobile = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new FreeworkException(ErrCodeEnum.NONELOGININFO);
        }

        //获取用户
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", mobile));
        if(null == user) {
            throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
        }

        //校验密码
        if(!MD5.encrypt(password).equals(user.getPassword())) {
            throw new FreeworkException(ErrCodeEnum.LOGINERR);
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(user.getId().toString(), user.getNickname());
        return token;
    }

    public void register(registerVO registerVo) {
        //获取注册信息，进行校验
        String phone = registerVo.getPhone();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new FreeworkException(ErrCodeEnum.NONELOGININFO);
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(mobleCode)) {
            throw new FreeworkException(ErrCodeEnum.CODEERR);
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<User>().eq("phone", phone));
        if(count.intValue() > 0) {
            throw new FreeworkException(ErrCodeEnum.USEREXISTED);
        }

        //添加注册信息到数据库
        User user = new User();
        BeanUtils.copyProperties(registerVo, user);
        user.setPassword(MD5.encrypt(registerVo.getPassword()));
        userMapper.insert(user);
    }


}
