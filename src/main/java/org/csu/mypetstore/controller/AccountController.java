package org.csu.mypetstore.controller;

import com.google.gson.annotations.JsonAdapter;
import org.csu.mypetstore.other.ResultBody;
import org.springframework.util.DigestUtils;
import com.aliyuncs.exceptions.ClientException;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//注解，说明这是一个Controller类
@RestController
//接收所有的/account下的url的请求
@RequestMapping("/users")
//将account、cart、sms与session中的同名属性绑定
@SessionAttributes({"account","cart","sms"})
@CrossOrigin


public class AccountController {
    //注解；将项目中的AccountService对象自动注入accountService中
    @Autowired
    AccountService accountService;
    //注解，同上
    @Autowired
    CartService cartService;

    private static final List<String> languageList;
    private static final List<String> categoryList;

    static {
        List<String> langList = new ArrayList<String>();
        langList.add("ENGLISH");
        langList.add("CHINESE");
        languageList = Collections.unmodifiableList(langList);

        List<String> catList = new ArrayList<String>();
        catList.add("FISH");
        catList.add("DOGS");
        catList.add("REPTILES");
        catList.add("CATS");
        catList.add("BIRDS");

        categoryList = Collections.unmodifiableList(catList);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.PUT,produces="application/Json;charset=UTF-8")
    public void updateAccount(@RequestBody Account account){
//        Account account=null;
        accountService.updateAccount (account);
    }
    @RequestMapping(value = "/password", method = RequestMethod.PATCH,produces="application/Json;charset=UTF-8")
    public void updatePassword(@RequestBody Account account){
//        Account account=null;
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword().getBytes()));
        accountService.updatePassword (account);
    }

    @PostMapping(value = "/login", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public Account doInsert(@RequestParam("username") String username,@RequestParam("password") String  password) {
        Account account = accountService.getAccount (username,DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println ("接受到请求");
        System.out.println (account);
        return account;
    }

    @GetMapping(value = "/users/phonenumber/{phonenumber}", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public Account getAccountByPhone(@PathVariable("phonenumber")String phonenumber) {
        Account account = accountService.getAccountByPhoneNumber (phonenumber);
       return account;
    }
    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET,produces="application/Json;charset=UTF-8")
    public Account getAccountByusername(@PathVariable("username")String username){
        Account account=accountService.getAccountByUsername (username);
        return account;
    }

    @PostMapping(value = "/", produces="application/Json;charset=UTF-8" )
    @ResponseBody
//    @RequestMapping(value = "/", method = RequestMethod.POST,produces="application/Json;charset=UTF-8")
    public void insertAccount(@RequestBody Account account ){
        account.setPassword (DigestUtils.md5DigestAsHex(account.getPassword ().getBytes()));
        accountService.insertAccount (account);
    }


    @GetMapping( "/sendVCode" )
    @ResponseBody
    public String sendVCode(String phoneNumber,Model model,HttpSession session) throws ClientException {
        System.out.println ("手机号"+phoneNumber);
        String sms = accountService.sendMsg (phoneNumber);
        String msg;
        System.out.println ("smss"+sms);

        if(sms != null){
            msg = "验证码发送成功";
            model.addAttribute ("sms",sms);
            session.setAttribute ("sms",sms);
        }
        else {
            msg = "验证码发送失败";
        }
        return msg;

    }

}
