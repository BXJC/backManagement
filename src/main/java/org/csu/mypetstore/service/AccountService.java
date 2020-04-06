package org.csu.mypetstore.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.other.AliyunMessageUtil;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;



    public Account getAccount(String username) {
        Account account = accountMapper.getAccountByUsername(username);
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        if(accountMapper.getAccountByUsernameAndPassword(account) == null)
            return accountMapper.getSignOnByUsernameAndPassword(account);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) {
        accountMapper.insertSignon(account);
    }



    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }

/*    public void sendSms(String mobile) {
        //生成6位数验证码
        String checkcode = RandomStringUtils.randomNumeric(6);
        //验证码存入缓存,为了注册的时候校验验证码是否正确
        redisTemplate.opsForValue().set("checkcode"+mobile,checkcode,5, TimeUnit.MINUTES);
        //验证码放入消息队列
        Map<String,String> map = new HashMap();
        map.put("mobile",mobile);
        map.put("checkcode",checkcode);
        rabbitTemplate.convertAndSend("sms",map);
    }*/

}
