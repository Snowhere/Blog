package me.snowhere.service.impl;

import me.snowhere.dao.UserDao;
import me.snowhere.datasource.DataSource;
import me.snowhere.datasource.DataSourceEnum;
import me.snowhere.dmo.User;
import me.snowhere.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    UserDao demoDao;

    @DataSource(DataSourceEnum.WRITE)
    public List<User> getLocalUser() {
        return demoDao.getAllUser();
    }
}