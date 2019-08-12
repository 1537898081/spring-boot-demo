package ${package.Controller};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@Slf4j
@RestController
@CrossOrigin
@Api(value = "${table.name}CRUD接口")
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceName},${table.entityName}> {
<#else>
public class ${table.controllerName} {
</#if>

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ${table.serviceName} ${table.name}Service;


}

