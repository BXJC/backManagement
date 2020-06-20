package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.AppResult;
import org.csu.mypetstore.other.*;
import org.springframework.util.DigestUtils;
import com.aliyuncs.exceptions.ClientException;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//注解，说明这是一个Controller类
@RestController
//接收所有的/account下的url的请求
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {
    //注解；将项目中的AccountService对象自动注入accountService中
    @Autowired
    AccountService accountService;

    @PassToken
    @PostMapping(value = "/login",produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public AppResult<String> Login(@RequestParam("username") String username, @RequestParam("password") String  password) {
        AppResult<String> appResult = new AppResult<>();
        Account account = accountService.getAccount (username,DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println ("接受到请求");
        System.out.println (account);
        if(account == null)
        {
            appResult = ResultBuilder.fail(ResultCode.UsernameOrPasswordEror);
        }
        else
        {
            String token = JwtUtil.createJWT(username);
            appResult = ResultBuilder.successData(ResultCode.OK,token);
        }
        return appResult;
    }

    @UserLoginToken
    @GetMapping(value = "/username/{username}",produces="application/Json;charset=UTF-8")
    public AppResult<Account> getAccountByUsername(@PathVariable("username")String username){
        AppResult<Account> appResult = new AppResult<>();
        Account account = accountService.getAccountByUsername (username);
        if(account == null)
        {
            appResult = ResultBuilder.fail(ResultCode.UsernameNotFound);
        }
        else
        {
            appResult = ResultBuilder.successData(ResultCode.OK,account);
        }
        return appResult;
    }

    @PassToken
    @GetMapping(value = "/phone/{phone}", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public AppResult<Account> getAccountByPhone(@PathVariable("phone")String phone) {
        AppResult<Account> appResult = new AppResult<>();
        Account account = accountService.getAccountByPhoneNumber (phone);
        if (account == null)
        {
            appResult = ResultBuilder.fail(ResultCode.PhoneNotFound);
        }
        else
        {
            appResult = ResultBuilder.successData(ResultCode.OK,account);
        }
        return appResult;
    }

    @UserLoginToken
    @PutMapping(value = "/",produces="application/Json;charset=UTF-8")
    public AppResult<String> updateAccount(@RequestBody Account account){
        AppResult<String> appResult = new AppResult<>();
        if(account.getPassword ()!=null){
      account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword().getBytes()));}
        accountService.updateAccount (account);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }

    @UserLoginToken
    @PatchMapping(value = "/",produces="application/Json;charset=UTF-8")
    public AppResult<String> updatePassword(@RequestBody Account account){
        AppResult<String> appResult = new AppResult<>();
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword().getBytes()));
        accountService.updatePassword (account);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }

    @PassToken
    @PostMapping(value = "/", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public AppResult<String> insertAccount(@RequestBody Account account ){
        AppResult<String> appResult = new AppResult<>();
      System.out.println (account.getPassword());
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword ().getBytes()));
        System.out.println (account.getPassword());
        accountService.insertAccount (account);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }

    @PassToken
    @GetMapping( "/sendVCode" )
    public AppResult<String> sendVCode(@RequestParam("phone") String phone) throws ClientException {
        AppResult<String> appResult = new AppResult<>();
        System.out.println ("手机号"+phone);
        String sms = accountService.sendMsg (phone);
        String msg;
        System.out.println ("sms:"+sms);
        if(sms != null){
            appResult = ResultBuilder.successNoData(ResultCode.Handled);
        }
        else {
            appResult = ResultBuilder.fail(ResultCode.VerifyCodeNotSend);
        }
        return appResult;
    }

}
