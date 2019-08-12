/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.flying.cattle.mdg.aid
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.llf.springboot.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * app分页组件
 * @author 朱晓宇
 * @version: V1.0
 */
@Data
public class PageParam<T>  implements Serializable{
	
	private static final long serialVersionUID = -7248374800878487522L;
	/**
     * <p>当前页</p>
     */
    @ApiModelProperty(value = "当前页 ")
    private int pageNum=1;
    /**
     * <p>每页记录数</p>
     */
    @ApiModelProperty(value = "每页记录数 ")
    private int pageSize=10;

    /**
     * <p>分页外的其它参数</p>
     */
    @ApiModelProperty(value = "分页外的其它参数 ")
    private T param;
    
}
