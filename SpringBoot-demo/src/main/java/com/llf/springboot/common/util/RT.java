package com.llf.springboot.common.util;

import com.baomidou.mybatisplus.extension.api.R;

/**
 * @创建人 蒋庆文
 * @创建时间 2018/2/28 10:01
 * @功能描述 返回值封装
 * @规定要求 StatusCode 0成功  -1失败
 * StatusMsg  返回信息（请求成功或失败）
 * Body 返回的数据，可是数组，可是字典
 */
public class RT extends R {

    public static final String STATUS_CODE = "StatusCode";
    public static final String STATUS_MSG = "StatusMsg";
    public static final String BODY = "Body";

    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_ERROR = 0;



}


