package com.llf.springboot.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:朱晓宇
 * @description:
 * @date:2019/1/8 0008
 */
@Aspect
@Component
public class WebLogAspect {

    //切点入口 Controller包下面所有类的所有方法
    private final String pointcut = "execution(* com.llf.springboot..controller.*.*(..))";

    //ThreadLocal是什么
    //顾名思义，ThreadLocal不是一个线程而是一个线程的本地化对象。
    // 当工作于多线程环境中的对象采用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程分配一个独立的副本。
    // 每个线程都可以独立的改变自己的副本，而不影响其他线程的副本。
    private static final ThreadLocal<Long> timeTreadLocal = new ThreadLocal<Long>();

    // 任务编号
    ThreadLocal<String> uuid = new ThreadLocal<String>();


    //日志打印
    private Logger logger = Logger.getLogger(getClass());


    // 任务开始时间
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();


    private StringBuilder sbLog = new StringBuilder("\n");

    //设置切入点
    @Pointcut(pointcut)
    public void webLog() {
    }

    /**
     * @sqlColumn
     * @作者 朱晓宇
     * @描述 前置通知 打印参数信息以及ip等请求信息
     * @时间 2019/1/8 0008 - 18:57
     * @参数 [joinPoint]
     * @返回 void
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        // 重新清理掉数据
        sbLog = new StringBuilder("\n");
        timeTreadLocal.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        HttpServletRequest request = attributes.getRequest();
        //获取所有请求的参数，封装为map对象
        // Map<String,Object> parameterMap = getParameterMap(request);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取被拦截的方法
        Method method = methodSignature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        sbLog.append("AOP begin ,请求开始方法  : = " + method.getDeclaringClass() + "." + methodName + "()\n");
        sbLog.append("请求url =  = " + request.getRequestURL().toString() + "\n");
        sbLog.append("请求资源的IP : " + request.getRemoteAddr() + "\n");
        // 获取请求方式 例如: POST , GET
        sbLog.append("请求资源方式 : " + request.getMethod() + "\n");
        //获取所有请求参数key和value
        sbLog.append("所有的请求KEY ：" + Arrays.toString(methodSignature.getParameterNames()) + "\n");
        sbLog.append("所有的请求VALUE ：" + Arrays.toString(joinPoint.getArgs())+"\n");
//        logger.info(sbLog.toString());
//        logger.info("--------------------sql如下-------------------------");
    }

    /**
     * @sqlColumn
     * @作者 朱晓宇
     * @描述 切入点入口是Controller包下面所有类的所有方法；再来通过@Around环绕注解方法里面做请求参数和响应信息的记录
     * @时间 2019/1/8 0008 - 18:56
     * @参数 [proceedingJoinPoint]
     * @返回 java.lang.Object
     */
    @Around(value = "webLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        try {
            // 这里可以做逻辑 判断返回集 做统一标准可以使用这个套路 强制让他返回摸个样子
            sbLog.append(String.format("类名：%s\r\n", proceedingJoinPoint.getTarget().getClass().getName()));
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            sbLog.append(String.format("方法：%s\r\n", methodSignature.getMethod().getName()));
            result = proceedingJoinPoint.proceed();
            sbLog.append(String.format("返回：%s\r\n", JSON.toJSON(result)));
        } catch (Exception ex) {
            //方法中的异常不try catch，才会进入改异常
            sbLog.append(String.format("异常：%s", ex.getMessage()));
            throw new RuntimeException(ex);
        } finally {
//            logger.info(sbLog.toString());
        }
        return result;
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * 在com.yl.spring.aop.ArithmeticCalculator接口的每一个实现类的每一个方法执行之后执行一段代码
     * 无论该方法是否出现异常
     */
    @After("webLog()")
    public void afterMethod(JoinPoint joinPoint) {

    }

    /**
     * 后置通知
     *
     * @param ret 返回参数
     */
    @AfterReturning(value = "webLog()", returning = "ret")
    public Object doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        long startTime = timeTreadLocal.get();
        double callTime = (System.currentTimeMillis() - startTime) / 1000.0;
        sbLog.append("调用controller花费时间time = "+callTime+"s\n");
        sbLog.append("请求结束!");
        logger.info(sbLog.toString());
        return ret;
    }


    /**
     * 异常通知
     *
     * @param exception 异常对象
     */
    @AfterThrowing(throwing = "exception", pointcut = "webLog()")
    public void exception(Exception exception) {
        logger.info(sbLog.toString());
    }


    /**
     * 获取所有请求参数，封装为map对象
     *
     * @return
     */
    public Map<String, Object> getParameterMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder stringBuilder = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            String keyValue = key + " : " + value + " ; ";
            stringBuilder.append(keyValue);
            parameterMap.put(key, value);
        }
        return parameterMap;
    }

    public String getReqParameter(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        //StringBuilder stringBuilder = new StringBuilder();
        JSONArray jsonArray = new JSONArray();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            JSONObject json = new JSONObject();
            //String keyValue = key+" : " +value+" ; ";
            json.put(key, value);
            //stringBuilder.append(keyValue);
            jsonArray.add(json);
        }
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("请求参数为：",jsonArray.toString());
        return jsonArray.toString();
    }


}