package com.llf.springboot.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("用户")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键",example="2")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称",example="测试22222")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",example="13800138000")
    private String phone;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
