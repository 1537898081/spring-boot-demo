//package com.llf.springboot.common.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice//不指定包默认加了@Controller和@RestController都能控制
//public class GlobalExceptionHandler {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @ResponseBody
//    @ExceptionHandler(RuntimeException.class)
//    public Map<String, Object> errorMsg(HttpServletRequest request, Exception ex) {
//        logger.error("错误链接" + request.getRequestURL().toString());
//        Map<String, Object> errorMsgResult = new HashMap<String, Object>();
//        errorMsgResult.put("code", 500);
//        errorMsgResult.put("mag", ex.getMessage() + "异常");
//
//        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
//        return errorMsgResult;
//    }
//}
