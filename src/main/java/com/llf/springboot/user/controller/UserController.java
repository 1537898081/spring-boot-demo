package com.llf.springboot.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llf.springboot.user.model.User;
import com.llf.springboot.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-06
 */
@RestController
@RequestMapping("/user")
@Api(description = "测试")
public class UserController {

    @Autowired
    private IUserService userService;


    // 采用restfull 风格
    @ApiOperation("自定义sql--报错")
    @GetMapping
    public R get(){
        return R.ok(userService.getList());
    }

    // 采用restfull 风格
    @ApiOperation("测试异常报错")
    @GetMapping("/{id}")
    public R getById(@PathVariable  String id){
        int i = 5/0;
        return R.ok(userService.getMap(new QueryWrapper<User>().eq("id",id)));
    }

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "第几页"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示多少行"),
    })
    @GetMapping("/{pageIndex}/{pageSize}")
    public R page(@PathVariable Integer pageIndex, @PathVariable Integer pageSize){
        return R.ok(userService.page(new Page<User>(pageIndex,pageSize),new QueryWrapper<User>()));
    }

    @ApiOperation("保存")
    @PostMapping("/{User}")
    public R save(@RequestBody User user){
        return R.ok(userService.save(user));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public R save(@PathVariable String id){
        return R.ok(userService.removeById(id));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{name}/{ces}")
    public R save(@PathVariable String name,String ces){
        return R.ok(userService.remove(new QueryWrapper<User>().eq("name",name)));
    }


}
