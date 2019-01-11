//package com.llf.springboot.common.aspect;
//
//
//import com.rxjy.common.utils.R;
//import com.rxjy.constructionContract.myutils.UUIDUtils;
//import net.sf.json.JSONObject;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 日志切面
// *
// * @author 张绍军
// * @date 2018-12-29
// */
//@Aspect
//@Component
//public class WebLogAspect2 {
//
//    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
//
//    // 基本数据类型
//    private static String[] types = {"java.lang.Integer", "java.lang.Double",
//            "java.lang.Float", "java.lang.Long", "java.lang.Short",
//            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
//            "java.lang.String", "int", "double", "long", "short", "byte",
//            "boolean", "char", "float"};
//
//    // 任务开始时间
//    ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//    // 任务编号
//    ThreadLocal<String> uuid = new ThreadLocal<>();
//
//    // 方法对象
//    ThreadLocal<MethodSignature> methodSignatureThreadLocal = new ThreadLocal<>();
//
//    /**
//     * 定义切入点，切入点为com.rxjy..controller下的所有函数
//     */
//    @Pointcut("execution(* com.rxjy..controller..*.*(..))")
//    public void webLog() {
//    }
//
//    /**
//     * 前置通知：在连接点之前执行的通知
//     *
//     * @param joinPoint 方法对象
//     */
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//
//        // 获取method对象
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//
//        // 设置方法对象
//        methodSignatureThreadLocal.set(methodSignature);
//
//        // 判断是否使用了Logger注解
//        if (methodSignature.getMethod().isAnnotationPresent(Logger.class)) {
//
//            // 设置开始时间和任务编号,设置方法对象. 用于执行完毕打印日志
//            startTime.set(System.currentTimeMillis());
//            uuid.set(UUIDUtils.getUUID());
//
//            // 准备打印日志
//            StringBuffer stringBuffer = new StringBuffer();
//
//            // 获取注解信息
//            Logger logger = methodSignature.getMethod().getAnnotation(Logger.class);
//            stringBuffer.append(methodSignature.getMethod().getName() + "\t");
//            stringBuffer.append(logger.value());
//            stringBuffer.append("开始=======>参数为{");
//
//            // 获取参数名
//            String[] fieldNames = methodSignature.getParameterNames();
//
//            // 获取参数值
//            Object[] args = joinPoint.getArgs();
//
//            for (int i = 0; i < args.length; i++) {
//                if (null != args[i]) {
//                    // json格式拼接
//                    if (0 != i) {
//                        stringBuffer.append(",");
//                    }
//                    // 通过反射获取参数值
//                    stringBuffer.append("\"" + fieldNames[i] + "\":" + getFieldsValue(args[i]));
//                }
//            }
//
//            // 日志打印对象
//            org.slf4j.Logger slf4j = LoggerFactory.getLogger(methodSignature.getMethod().getDeclaringClass());
//
//            slf4j.info(new String(stringBuffer.append("}").append("\t(^_^)\t任务编号:" + uuid.get())));
//
//        }
//
//    }
//
//    /**
//     * 后置通知
//     *
//     * @param ret 返回参数
//     */
//    @AfterReturning(value = "webLog()",returning = "ret")
//    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        // 判断返回的是R
//        if (ret instanceof R) {
//
//            StringBuffer stringBuffer = new StringBuffer();
//            StringBuffer sb = new StringBuffer();
//            stringBuffer.append("任务编号:" + uuid.get() + " 执行");
//
//            // 判断使用了内部返回码还是底层返回码
//            JSONObject jsonObject = JSONObject.fromObject(ret);
//            int resultCode = jsonObject.has(R.STATUS_CODE) ?
//                    jsonObject.getInt(R.STATUS_CODE) : jsonObject.getInt(R.BASE_STATUS_CODE);
//
//            // 是否使用了Logger注解
//            boolean useLogger = null != methodSignatureThreadLocal.get()
//                    && methodSignatureThreadLocal.get().getMethod().isAnnotationPresent(Logger.class);
//
//            // 如果返回不成功的信息
//            if (R.STATUS_CODE_SUCCESS != resultCode && R.STATUS_CODE_SUCCESS_SEVEN != resultCode) {
//
//                //如果没有使用Logger注解
//                if (!useLogger) {
//                    org.slf4j.Logger slf4j = LoggerFactory.getLogger(
//                            methodSignatureThreadLocal.get().getMethod().getDeclaringClass());
//
//                    // 获取参数名
//                    String[] fieldNames = methodSignatureThreadLocal.get().getParameterNames();
//
//                    // 获取参数值
//                    Object[] args = joinPoint.getArgs();
//
//                    for (int i = 0; i < args.length; i++) {
//                        sb.append("参数i"+":" + fieldNames[i] + "=" + args[i] + "。");
//                    }
//
////                    slf4j.error(methodSignatureThreadLocal.get().getMethod().getName() + "任务执行失败,返回的信息是" + ret
////                            + "IP地址:" + request.getRemoteAddr() + sb );
//                    return;
//                }
//
//                stringBuffer.append("失败,返回的信息是" + ret);
//                LOGGER.error(new String(stringBuffer));
//                return;
//            }
//
//            // 如果使用了Logger注解,即使成功也打印日志
//            if (useLogger) {
//                stringBuffer.append("成功,共消耗" + (System.currentTimeMillis() - startTime.get()) + "毫秒\n");
//                LOGGER.info(new String(stringBuffer));
//            }
//        }
//    }
//
//    /**
//     * 异常通知
//     *
//     * @param exception 异常对象
//     */
//    @AfterThrowing(throwing = "exception", pointcut = "webLog()")
//    public void exception(Exception exception) {
//
//        if (null != methodSignatureThreadLocal.get()) {
//            org.slf4j.Logger slf4j = LoggerFactory.getLogger(
//                    methodSignatureThreadLocal.get().getMethod().getDeclaringClass());
//
//            slf4j.error(methodSignatureThreadLocal.get().getMethod().getName() + "任务执行出现异常,异常信息是" + exception.getMessage());
//        }
//    }
//
//
//    /**
//     * 解析实体类，获取实体类中的属性
//     *
//     * @param object 方法对象
//     * @return json格式的对象
//     */
//    private String getFieldsValue(Object object) {
//
//        // 如果是基本数据类型 则返回""
//        String typeName = object.getClass().getTypeName();
//        for (String string : types) {
//            if (string.equals(typeName)) {
//                return "\"" + object.toString() + "\"";
//            }
//        }
//
//        // 准备拼装json
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("{");
//
//        //如果是Map类型
//        if (object instanceof Map) {
//            Map<String, Object> map = (Map) object;
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                stringBuilder.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
//            }
//            return stringBuilder.append("}").toString();
//        }
//
//
//        // 通过反射获取所有的字段，getFileds()获取public的修饰的字段
//        // getDeclaredFields获取private protected public修饰的字段
//        Field[] fields = object.getClass().getDeclaredFields();
//
//        try {
//            for (Field field : fields) {
//                //在反射时能访问私有变量
//                field.setAccessible(true);
//                for (String str : types) {
//                    // 这边会有问题，如果实体类里面继续包含实体类，这边就没法获取。
//                    // 其实，我们可以通递归的方式去处理实体类包含实体类的问题,不过暂不考虑.
//                    if (field.getType().getName().equals(str)) {
//                        stringBuilder.append(field.getName() + " : " + field.get(object) + ", ");
//                    }
//                }
//            }
//        } catch (IllegalAccessException illegalAccessException) {
//            illegalAccessException.printStackTrace();
//        }
//
//        stringBuilder.append("}");
//        return stringBuilder.toString();
//    }
//
//    public Map getPointcutArgs(JoinPoint joinPoint){
//        if (joinPoint !=null){
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = attributes.getRequest();
//            Map params = new HashMap();
//            // 获取请求的Url
//            params.put("url", request.getRequestURI());
//            // 获取请求方式 例如: POST , GET
//            params.put("method", request.getMethod());
//            // 获取请求的IP地址
//            params.put("ip", request.getRemoteAddr());
//            // 获取类名
//            params.put("className", joinPoint.getSignature().getDeclaringTypeName());
//            // 获取类方法
//            params.put("classMethod", joinPoint.getSignature().getName());
//            // 获取请求参数
//            params.put("args", joinPoint.getArgs());
//
//            return params;
//        }
//        return null;
//    }
//
//
//    /**
//     * Around通知
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("webLog()")
//    public Object checkPermissionAround(ProceedingJoinPoint joinPoint ){
//        try
//            {
//                Object[] args = joinPoint.getArgs();
//                Object result = joinPoint.proceed(args);
//                return result;
//
//            }catch(Throwable e){
//                System.out.println(e.toString());
//            }
//        return null;
//
//    }
//
//
//
//
//}
