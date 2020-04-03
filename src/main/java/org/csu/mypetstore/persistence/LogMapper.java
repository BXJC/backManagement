package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Log;
import org.springframework.stereotype.Repository;

import java.sql.Date;

import java.util.List;

@Repository
public interface LogMapper {
    public void insertBrowseLog(@Param ("account") Account account, @Param ("itemId") String itemid,@Param ("browseTime") Date browseTime);

    public void insertAddLog(@Param ("account") Account account,@Param ("itemId") String itemid,@Param ("addTime") Date addTime);

    public List<Log> getBrowseLogListByUsername(@Param ("username") String username);

    public List<Log> getAddListByUsername(@Param ("username") String username);
}
