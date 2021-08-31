package com.njtech.blog.handle;
import com.njtech.blog.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 对加了controller注解的方法进行拦截处理 AOP
@ControllerAdvice
public class AllExceptionHandler {

    // 进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回json数据
    public ResultVO doException(Exception ex){
        // 打印到堆栈
        ex.printStackTrace();
        return  ResultVO.fail(-999,"系统异常");
    }

}
