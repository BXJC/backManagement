package org.csu.mypetstore.other;

import org.csu.mypetstore.domain.AppResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public AppResult<Null> handleException(Exception e){
        AppResult<Null> appResult = new AppResult<>();
        if(e instanceof RuntimeException)
        {
            if(e instanceof IndexOutOfBoundsException)
                appResult = ResultBuilder.fail(ResultCode.ArrayIndexOutOfBoundEx);
            else if (e instanceof NullPointerException)
                appResult = ResultBuilder.fail(ResultCode.NullPointerEx);
            else if(e instanceof ClassCastException)
                appResult = ResultBuilder.fail(ResultCode.ClassCastEx);
            else
                appResult = ResultBuilder.fail(ResultCode.UnknownEx);
        }
        else if(e instanceof IOException)
            appResult = ResultBuilder.fail(ResultCode.IOEx);
        else if (e instanceof SQLException)
            appResult = ResultBuilder.fail(ResultCode.SQLEx);
        return appResult;
    }
}
