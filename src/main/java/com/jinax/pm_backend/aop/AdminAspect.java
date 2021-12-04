package com.jinax.pm_backend.aop;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Aspect
@Component
public class AdminAspect {

    private UserRepository userRepository;
    @Autowired
    public AdminAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 定义切点
     * adminController类里面的所有方法都需要管理员权限
     */
    @Pointcut("execution(public * com.jinax.pm_backend.Controller.AdminController.*(..))")
    public void permissionType() {
    }

    /**
     * 带有@Admin注解的方法都需要有管理员权限
     */
    @Pointcut("@annotation(com.jinax.pm_backend.annotation.Admin)")
    private void permissionMethod() {
    }

    /**
     * 权限环绕通知
     */
    @ResponseBody
    @Around("permissionType()||permissionMethod()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取函数参数
        Object[] args = joinPoint.getArgs();
        Integer userId = Integer.parseInt(args[0].toString());
        User user = userRepository.getOne(userId);
        if (user.getRole() == 0) {
            return joinPoint.proceed();
        } else {
            return CommonResult.failResult(null, "您没有管理员权限");
        }
    }
}
