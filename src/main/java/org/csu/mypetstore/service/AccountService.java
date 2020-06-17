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

    public Account getAccountByUsername(String username) {
        Account account = accountMapper.getAccountByUsername(username);
        if(account == null){
            return accountMapper.getSignOnByUsername(username);
        }
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getSignOnByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) {
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        System.out.println (account.getCity ());
    }

    public String sendMsg(String phoneNumber) throws  ClientException {

            String randomNum = "";
            int num=6;
            for(int i = 0; i < num;i ++){
                int randomNumInt = (int)(Math.random() * 10);
                randomNum += randomNumInt;
            }

            String jsonContent = "{\"code\":\"" + randomNum + "\"}";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("phoneNumber", phoneNumber);
            paramMap.put("msgSign", "CSUDazz宠物商店");
            paramMap.put("templateCode", "SMS_187271386");
            paramMap.put("jsonContent", jsonContent);

            SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
            if((sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))){
                System.out.println ("发送成功");
                return randomNum;
            }
            else  {
                System.out.println ("requstsId"+sendSmsResponse.getRequestId ());
                System.out.println ("code"+sendSmsResponse.getCode ());
                System.out.println ("message"+sendSmsResponse.getMessage ());
                System.out.println ("bieid"+sendSmsResponse.getBizId ());
                if(sendSmsResponse.getCode() == null) {
                    System.out.println ("发送失败");

                }
                if(!sendSmsResponse.getCode().equals("OK")) {
                    System.out.println ("发送失败");

                }
                return null;
            }
        }

    public Account getAccountByPhoneNumber(String phoneNumber) {
        Account account = new Account();
        account.setPhone(phoneNumber);
        if(accountMapper.getAccountByPhone(account) == null)
            return accountMapper.getSignOnByPhone(account);
        return accountMapper.getAccountByPhone(account);

    }

    public boolean checkPhone(String phoneNumber) {
        if(accountMapper.checkPhone(phoneNumber) != null)
            return true;
        else return false;
    }

    public void updatePassword(Account account) {
        accountMapper.updatePassword (account);
    }
}
