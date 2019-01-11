package com.llf.springboot.user.service.impl;

import com.llf.springboot.user.model.User;
import com.llf.springboot.user.dao.UserMapper;
import com.llf.springboot.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @sqlColumn
     * @作者 朱晓宇
     * @描述 测试
     * @时间 2019/1/8 0008 - 13:12
     * @参数 []
     * @返回 java.util.List<com.llf.springboot.user.model.User>
     */
    @Override
    public List<User> getList() {
        return userMapper.getList();
    }
}
