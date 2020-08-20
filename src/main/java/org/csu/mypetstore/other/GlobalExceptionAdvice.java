package org.csu.mypetstore.other;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.csu.mypetstore.domain.AppResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.sql.SQLException;

/***
*  @Author: zzy
*  @Date: 2020-06-21
*  @Description: 全局异常处理
***/

@ControllerAdvice
//@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public AppResult<Null> handleException(Exception e){
        AppResult<Null> appResult = new AppResult<>();
        if(e instanceof IllegalArgumentException || e instanceof JWTVerificationException)
            appResult = ResultBuilder.fail(ResultCode.TokenEx);
        else if(e instanceof RuntimeException)
        {
            if(e instanceof IndexOutOfBoundsException)
                appResult = ResultBuilder.fail(ResultCode.ArrayIndexOutOfBoundEx);
            else if (e instanceof NullPointerException)
                appResult = ResultBuilder.fail(ResultCode.NullPointerEx);
            else if(e instanceof ClassCastException)
                appResult = ResultBuilder.fail(ResultCode.ClassCastEx);
        }
        else if(e instanceof IOException)
            appResult = ResultBuilder.fail(ResultCode.IOEx);
        else if (e instanceof SQLException)
            appResult = ResultBuilder.fail(ResultCode.SQLEx);
        else
            appResult = ResultBuilder.fail(ResultCode.UnknownEx);
        return appResult;
    }
}
