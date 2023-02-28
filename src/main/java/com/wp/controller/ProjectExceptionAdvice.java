//package com.wp.controller;
//
//
//import com.wp.exception.BusinessException;
//import com.wp.exception.SystemException;
//import com.wp.util.Code;
//import com.wp.util.Result;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ProjectExceptionAdvice {
//    @ExceptionHandler(Exception.class)
//    public Result doExpetion(Exception ex){
//        //记录日志(错误堆栈)
//        //发送消息给运维
//        //发送邮件给开发人员，exception对象发送给开发人员
//        System.out.println("其他异常被捕获");
//        return new Result(Code.ERR, null, "系统繁忙,请稍后再试");    }
//
//    @ExceptionHandler(SystemException.class)
//    public Result doSystemException(SystemException exception) {
//        //记录日志(错误堆栈)
//        //发送消息给运维
//        //发送邮件给开发人员，exception对象发送给开发人员
//        System.out.println("系统异常被捕获");
//        return new Result(exception.getCode(), null, exception.getMessage());
//    }
//
//    @ExceptionHandler(BusinessException.class)
//    public Result doSystemException(BusinessException exception) {
//        System.out.println("业务异常被捕获");
//        return new Result(exception.getCode(), null, exception.getMessage());
//    }
//
//
//}
