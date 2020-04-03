package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.persistence.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

import java.util.List;

@Service
public class LogService {

    @Autowired
    LogMapper logMapper;

    public void insertBrowseLog(Account account, String itemId){

        logMapper.insertBrowseLog(account,itemId,new Date (System.currentTimeMillis ()));
    }

    public void insertAddLog(Account account,String itemId){
        Date addTime =new Date( System.currentTimeMillis ());
        logMapper.insertAddLog(account,itemId,addTime);
    }

    public List<Log> getBrowseLogListByUsername(String username){
        System.out.println (username);
        return logMapper.getBrowseLogListByUsername(username);
    }

    public List<Log> getAddLogListByUsername(String username){
       return logMapper.getAddListByUsername(username);
    }
}
