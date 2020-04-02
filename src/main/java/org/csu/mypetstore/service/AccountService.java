package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
