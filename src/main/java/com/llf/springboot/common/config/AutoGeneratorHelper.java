package com.llf.springboot.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *  *
 *  * 自动生成映射工具类
 *  *
 *  * @author hubin
 *  *
 *  
 */
public class AutoGeneratorHelper {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    /**
     * <p>
     * 测试 run 执行
     * </p>
     * <p>
     * 更多使用查看 http://mp.baomidou.com
     * </p>
     */
    public static void main(String[] args) {

        //用来获取Mybatis-Plus.properties文件的配置信息
        ResourceBundle rb = ResourceBundle.getBundle("Mybatis-Plus");
        AutoGenerator mpg = new AutoGenerator();


        // 全局配置
        GlobalConfig gc = new GlobalConfig();


        // 全局配置
        // 当前项目
        final String projectPath = System.getProperty("user.dir");
        // 所在路径
        gc.setOutputDir(projectPath + "/src/main/java");
        // 作者
        gc.setAuthor("朱晓宇");
        gc.setSwagger2(true); // 开启swagger2
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 开启 activeRecord 模式
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 使用的数据库
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        // 读取配置文件中的数据库
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("passWord"));
        dsc.setUrl(rb.getString("url"));
        mpg.setDataSource(dsc);


        // 策略配置
        final StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix(new String[] { "bmd_", "mp_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(scanner("表名")); // 需要生成的表
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 字段名生成策略
        // strategy.setFieldNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperServiceImplClass("com.baomidou.springwind.service.support.BaseServiceImpl");
        strategy.setSuperControllerClass("com.llf.springboot.common.config.AbstractController");
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        final String packageName = scanner("包名");
        pc.setParent("com.llf.springboot."+packageName);// 自定义包路径
        pc.setController("controller");// 这里是控制器包名，默认 web
        pc.setEntity("model");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service" + ".impl");
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocityz
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + packageName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("/templates/controller");
        // 配置自定义输出模板
        // templateConfig.setEntity();
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setPackageInfo(pc);
        // 执行生成
        mpg.execute();
    }


}
