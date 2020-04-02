package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account")
@SessionAttributes({"account","cart"})
public class AccountController {

    @Autowired
    AccountService accountService;

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

    @GetMapping("/signOnForm")
    public String signOnForm(){
        return "account/signOn.html";
    }
    @PostMapping("/signOn")
    public String login(String password, String username, Model model) {
        Account account = accountService.getAccount (username, password);
        Cart cart = null;
        if (account != null) {
            model.addAttribute ("account", account);
            cart = cartService.getCart(username);
            model.addAttribute("cart",cart);
            return "catalog/main";
        } else {
            model.addAttribute ("msg", "用户名或密码错误");
            return "account/signOn";
        }
    }

    @GetMapping("/signOut")
    public String signOff(Model model) {
        Account account = null;
        model.addAttribute("account",account);
        return "catalog/main";
    }

    @GetMapping("/editUserInfoForm")
    public String  editUserInfoForm(@SessionAttribute("account") Account account, Model model){
        model.addAttribute ("account",account);
        model.addAttribute("languageList",languageList);
        model.addAttribute("categoryList",categoryList);
        System.out.println(account);
        return "account/editAccount";
    }
    @PostMapping("/editUserInfo")
    public String editUserInfo(Account account,String repeatPassword,Model model){
        String msg=null;
        if(account.getPassword ().equals (repeatPassword))
        {
            accountService.updateAccount (account);
            msg="修改成功";
            model.addAttribute ("account",account);
            model.addAttribute ("msg",msg);
            return "account/editAccount";

        }
        else {
            model.addAttribute ("account",account);
            System.out.println (account.getPassword ());
            System.out.println (repeatPassword);
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
    public String newAccount(String username,String password,String repeatPassword,Model model){
        Account account=new Account();
        String msg=null;

        if(accountService.getAccount (username)!=null) {
            msg = "用户名已被使用";
            model.addAttribute ("msg",msg);
            return "account/newAccount";
        }
        else if(password.equals (repeatPassword))
        {
            account.setUsername (username);
            account.setPassword (password);

            accountService.insertAccount(account);
            return "account/signOn";

        }
        else{
            msg="两次密码输入不一致";
            model.addAttribute ("msg",msg);
            return "account/newAccount";

        }

    }
}
