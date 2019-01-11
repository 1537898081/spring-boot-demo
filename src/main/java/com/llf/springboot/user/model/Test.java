package com.llf.springboot.user.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 朱晓宇
 * @since 2019-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

    /**
     * ces1
     */
    private String q;

    /**
     * ces2
     */
    private String b;


    @Override
    protected Serializable pkVal() {
        return this.q;
    }

}
