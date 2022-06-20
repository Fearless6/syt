package com.jd.yygh.common.exception;

import com.jd.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 一个胖胖的瘦子
 * @date 2022/6/19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //如果出现exception的异常，就调用这个方法
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class) //如果出现exception的异常，就调用这个方法
    @ResponseBody
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.fail();
    }
}
