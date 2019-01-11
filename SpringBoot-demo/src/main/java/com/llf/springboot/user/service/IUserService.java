package com.llf.springboot.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llf.springboot.user.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-06
 */
public interface IUserService extends IService<User> {

    /**
     * @sqlColumn
     * @作者 朱晓宇
     * @描述 测试
     * @时间 2019/1/8 0008 - 13:12
     * @参数 []
     * @返回 java.util.List<com.llf.springboot.user.model.User>
     */
    List<User> getList();
}
