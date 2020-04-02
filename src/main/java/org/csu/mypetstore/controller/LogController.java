package org.csu.mypetstore.controller;

import com.sun.tracing.dtrace.Attributes;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/log")
@SessionAttributes({"account","browseLogList","addToItemToCartLogList"})
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/viewLog")
    public String viewLog(@SessionAttribute("account") Account account, Model model){
        List<Log> browseLogList=logService.getBrowseLogListByUsername (account.getUsername ());
        System.out.println (account.getUsername ());
        List<Log> addToItemToCartLogList=logService.getAddLogListByUsername (account.getUsername ());
        model.addAttribute ("browseLogList",browseLogList);
        model.addAttribute ("addToItemToCartLogList",addToItemToCartLogList);
        return "log/viewLog";
    }
}
