package org.csu.mypetstore.other;

import org.csu.mypetstore.domain.AppResult;

public class ResultBuilder {
    public static <T> AppResult<T> successNoData(ResultCode code){
        AppResult<T> result = new AppResult<T>();
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        return result;
    }

    public static <T> AppResult<T> successData(ResultCode code, T data){
        AppResult<T> result = new AppResult<T>();
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> AppResult<T> fail(ResultCode code){
        AppResult<T> result = new AppResult<T>();
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        return result;
    }
}
