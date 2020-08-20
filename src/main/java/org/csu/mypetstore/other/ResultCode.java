package org.csu.mypetstore.other;
/***
*  @Author: zzy
*  @date: 2020-06-21
*  @description: 自定义返回码
***/
public enum ResultCode {
    OK(200,"请求成功"),
    Created(202,"请求成功且服务器已创建资源"),
    Handled(204,"请求成功，无返回信息"),
    BadRequest(400,"语义有误或请求参数有误"),
    Unauthorized(402,"用户未授权"),
    NotFound(404,"请求的资源不可用"),
    TimeOut(408,"请求超时"),
    InternalServerError(500,"服务器内部错误"),
    ServiceUnavailable(503,"服务器过载或正在维护"),
    HTTPVersionNotSupported(505,"服务器不支持该HTTP版本"),

    UsernameOrPasswordEror(10001,"用户名或密码错误"),
    UsernameNotFound(10002,"用户名不存在"),
    PhoneNotFound(10003,"手机号不存在"),
    UserInfoNotComplete(10004,"用户信息不完整"),
    VerifyCodeNotSend(10005,"验证码发送失败"),
    NoInfoFind(10006,"没有查询到相关信息"),
    ArrayIndexOutOfBoundEx(10007,"数组越界错误"),
    NullPointerEx(10008,"空指针错误"),
    ClassCastEx(10009,"类型强转错误"),
    IOEx(10010,"IO错误"),
    SQLEx(10011,"数据库操作错误"),
    UnknownEx(10012,"未知错误"),
    TokenEx(10013,"token无效");

    private int code;
    private String message;

    ResultCode(Integer code ,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
