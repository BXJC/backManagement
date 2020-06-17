package org.csu.mypetstore.controller;

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

    @PostMapping(value = "/login",produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public Account Login(@RequestParam("username") String username,@RequestParam("password") String  password) {
        Account account = accountService.getAccount (username,DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println ("接受到请求");
        System.out.println (account);
        return account;
    }

    @GetMapping(value = "/username/{username}",produces="application/Json;charset=UTF-8")
    public Account getAccountByUsername(@PathVariable("username")String username){
        Account account = accountService.getAccountByUsername (username);
        return account;
    }

    @GetMapping(value = "/phone/{phone}", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public Account getAccountByPhone(@PathVariable("phone")String phone) {
        Account account = accountService.getAccountByPhoneNumber (phone);
        return account;
    }


    @PutMapping(value = "/update",produces="application/Json;charset=UTF-8")
    public void updateAccount(@RequestBody Account account){
        accountService.updateAccount (account);
    }
    @PatchMapping(value = "/password",produces="application/Json;charset=UTF-8")
    public void updatePassword(@RequestBody Account account){
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword().getBytes()));
        accountService.updatePassword (account);
    }

    @PostMapping(value = "/insert", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public void insertAccount(@RequestBody Account account ){
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword ().getBytes()));
        accountService.insertAccount (account);
    }


    @GetMapping( "/sendVCode" )
    public String sendVCode(@RequestParam("phone") String phone) throws ClientException {
        System.out.println ("手机号"+phone);
        String sms = accountService.sendMsg (phone);
        String msg;
        System.out.println ("sms:"+sms);
        if(sms != null){
            msg = "验证码发送成功";
        }
        else {
            msg = "验证码发送失败";
        }
        return msg;
    }

}
