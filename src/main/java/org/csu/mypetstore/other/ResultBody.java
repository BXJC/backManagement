package org.csu.mypetstore.other;

import lombok.Data;

import java.util.List;
@Data
public class ResultBody<T> {
    //返回数据代码 0 表示成功  1 表示失败
    private int code;
    //返回失败时的提示信息
    private String msg;
    //记录总数
    private int count;
    //记录集合
    private List<T> data;

    public static  <T> ResultBody  returnSuc(List<T> data){
        ResultBody resultBody=new ResultBody();
        resultBody.setCode(0);
        resultBody.setMsg("");
        resultBody.setData(data);
        resultBody.setCount(data.size());
        return  resultBody;
    }

    public static  <T> ResultBody  returnFail(String errorMsg){
        ResultBody resultBody=new ResultBody();
        resultBody.setCode(1);
        resultBody.setMsg(errorMsg);
        return  resultBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}