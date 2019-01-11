package com.llf.springboot.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llf.springboot.user.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-06
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * @sqlColumn
     * @作者 朱晓宇
     * @描述 测试
     * @时间 2019/1/8 0008 - 13:12
     * @参数 []
     * @返回 java.util.List<com.llf.springboot.user.model.User>
     */
    @Select("select * from user1")
    List<User> getList();
}
