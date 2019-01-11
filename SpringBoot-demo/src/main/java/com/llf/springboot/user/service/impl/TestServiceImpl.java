package com.llf.springboot.user.service.impl;

import com.llf.springboot.user.model.Test;
import com.llf.springboot.user.dao.TestMapper;
import com.llf.springboot.user.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-10
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
