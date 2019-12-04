package com.dbsun.aop.follow;

import com.dbsun.base.BaseController;
import com.dbsun.common.ServerResponse;
import com.dbsun.entity.system.PageData;
import com.dbsun.util.Tools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class FollowRecAop extends BaseController {

    /**
     * 切面：方法执行后执行该方法
     * @param pjp
     * @param iFollowRec
     * @throws Exception
     */
//    @After(value ="@annotation(iFollowRec)")
    @AfterReturning(returning="sp", pointcut="@annotation(iFollowRec)")
    public void systemNotice(JoinPoint pjp, IFollowRec iFollowRec, ServerResponse sp)throws Exception {

        System.out.println("aop...............................................");
        PageData pd = null;
        Object[] args = pjp.getArgs();

        System.out.println("获取到的返回值：" + sp);
        // 当返回值为20成功之后才能做后面的事情
        if(Tools.isObjEmpty(sp) || sp.getStatus() != 0){
            return;
        }

        for (Object arg : args) {
            if (arg instanceof PageData) {
                // 第一个PageDate包含YBCS001、USER_ID、YBC001
                if(pd == null){
                    pd = (PageData) arg;
                    System.out.println("拦截到的参数为：" + pd);
                    break;
                }
            }
        }

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Method method = methodSignature.getMethod();
        System.out.println("---------------参数列表开始-------------------------");
        for (int i =0 ,len=parameterNames.length;i < len ;i++){
            System.out.println("参数名："+ parameterNames[i] + " = " +args[i]);
        }
        System.out.println("---------------参数列表结束-------------------------");
        IFollowRec redis=(IFollowRec)method.getAnnotation(IFollowRec.class);
        System.out.println("自定义注解 key:" + redis.followType());
        System.out.println("自定义注解 keyField:" + redis.followContent());

        PageData userPd = this.getUserPd();

        pd.put("XYCF004_TP", redis.followType());
        pd.put("XYCF003", redis.followContent());

        // 业务逻辑

        return;
    }
}
