package com.llf.springboot.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llf.springboot.poi.ExcelExport;
import com.llf.springboot.poi.MyExcelExportUtil;
import com.llf.springboot.user.model.User;
import com.llf.springboot.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
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


    //REST全称是Representational State Transfer，中文意思是表述（编者注：通常译为表征）性状态转移。
    //它首次出现在2000年Roy Fielding的博士论文中，Roy Fielding是HTTP规范的主要编写者之一。
    //他在论文中提到："我这篇文章的写作目的，就是想在符合架构原理的前提下，理解和评估以网络为基础的应用软件的架构设计，
    //得到一个功能强、性能好、适宜通信的架构。 REST指的是一组架构约束条件和原则。"
    //如果一个架构符合REST的约束条件和原则，我们就称它为RESTful架构。

    //REST本身并没有创造新的技术、组件或服务，而隐藏在RESTful背后的理念就是使用Web的现有特征和能力，
    //更好地使用现有Web标准中的一些准则和约束。虽然REST本身受Web技术的影响很深，
    //但是理论上REST架构风格并不是绑定在HTTP上，只不过目前HTTP是唯一与REST相关的实例。
    //所以我们这里描述的REST也是通过HTTP实现的REST。

    //restFul是符合rest架构风格的网络API接口,完全承认Http是用于标识资源。
    //restFul URL是面向资源的，可以唯一标识和定位资源。
    //对于该URL标识的资源做何种操作是由Http方法决定的。
    //rest请求方法有4种，包括get,post,put,delete.分别对应获取资源，添加资源，更新资源及删除资源.


    // 采用restfull 风格
    @ApiOperation("自定义sql--报错")
    @GetMapping
    public R get() {
        return R.ok(userService.getList());
    }

    // 采用restfull 风格
    @ApiOperation("测试异常报错")
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        int i = 5 / 0;
        return R.ok(userService.getMap(new QueryWrapper<User>().eq("id", id)));
    }

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示多少行"),
    })
    @GetMapping("/{pageIndex}/{pageSize}")
    public R page(@PathVariable Integer pageIndex, @PathVariable Integer pageSize) {
        return R.ok(userService.page(new Page<User>(pageIndex, pageSize), new QueryWrapper<User>()));
    }

    @ApiOperation("保存")
    @PostMapping("/{User}")
    public R save(@RequestBody User user) {
        return R.ok(userService.save(user));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public R save(@PathVariable String id) {
        return R.ok(userService.removeById(id));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{name}/{ces}")
    public R save(@PathVariable String name, String ces) {
        return R.ok(userService.remove(new QueryWrapper<User>().eq("name", name)));
    }


    @RequestMapping("/exportStudent")
    public void exportStudent(HttpServletResponse response) {
        try {
            List<User> sutdentList = userService.list();
            MyExcelExportUtil.exportExcel(sutdentList, User.class, "学生基本信息", "新生入学信息", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel所有
     *
     * @param response
     */
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response, Long companyId) {
        List<User> epcSystemDictionaryList = userService.list();
        List<User> list2 = userService.list();
        for (int i = 0; i < 90000; i++) {
            for (User user : epcSystemDictionaryList) {
                list2.add(user);
            }
        }

        ExcelExport excelExport = new ExcelExport(response, "数据字典总数据", "sheet1");
//        excelExport.writeExcel(new String[]{"id", "name", "phone"}
//                , new String[]{"主键", "名称", "手机号"}
//                , new int[]{30, 30, 30}, epcSystemDictionaryList);
        excelExport.writeExcelC(list2, User.class);
    }
}
