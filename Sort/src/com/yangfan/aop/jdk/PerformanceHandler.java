package com.yangfan.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PerformanceHandler implements InvocationHandler{
    private Object target;
    public PerformanceHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("开始删除："+target.getClass().getName()+method.getName());
        Object object = method.invoke(target,args);
        System.out.println("删除结束");
        return object;
    }
}
