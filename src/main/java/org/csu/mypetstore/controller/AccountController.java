package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/signOnForm")
    public String signOnForm(){
        return "account/signOn.html";
    }
    @PostMapping("/signOn")
    public String login(String password, String username, Model model,HttpSession session) {
        Account account = accountService.getAccount (username, password);

        if (account != null) {
            session.setAttribute ("account", account);
            return "catalog/main";
        } else {
            model.addAttribute ("msg", "用户名或密码错误");
            return "account/signon";
        }
    }

    @GetMapping("/signOut")
    public String signoff(Model model,HttpSession session) {
        session.removeAttribute ("account");
        return "catalog/main";
    }

    @GetMapping("/updataUserInfoForm")
    public String  updataform(HttpSession session,Model model){
        Account account=(Account)session.getAttribute ("account");
        model.addAttribute ("account",account);
        return "account/editAccount";
    }
    @PostMapping("/updateUserInfo")
    public String updataUserInfo(Account account,String repeatpassword,Model model){
        String msg=null;
        if(account.getPassword ().equals (repeatpassword))
        {
            accountService.updateAccount (account);
            msg="修改成功";
            model.addAttribute ("msg",msg);
            return "account/editAccount";

        }
        else {
            model.addAttribute ("account",account);
            System.out.println (account.getPassword ());
            System.out.println (repeatpassword);
            msg="两次输入密码不一致";
            model.addAttribute ("msg",msg);
            return "account/editAccount";
        }

    }

    @GetMapping("/newAccountForm")
    public String newAccountForm(String msg){
        return "account/newAccount";
    }
    @PostMapping("/newAccount")
    public String newAccount(String username,String password,String repeatpassword,Model model){
        Account account=null;
        String msg=null;

        if(accountService.getAccount (username)!=null) {
            msg = "用户名已被使用";
            model.addAttribute ("msg",msg);
            return "account/newAccount";
        }
        else if(password.equals (repeatpassword))
        {
            account.setUsername (username);
            account.setPassword (password);

            accountService.updateAccount (account);
            model.addAttribute ("msg",msg);
            return "account/signOn";

        }
        else{
            msg="两次密码输入不一致";
            model.addAttribute ("msg",msg);
            return "account/newAccount";

        }

    }
}
