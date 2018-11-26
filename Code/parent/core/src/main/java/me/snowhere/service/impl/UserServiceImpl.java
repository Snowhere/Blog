package me.snowhere.service.impl;

import me.snowhere.dao.UserDao;
import me.snowhere.datasource.DataSource;
import me.snowhere.datasource.DataSourceEnum;
import me.snowhere.dmo.User;
import me.snowhere.service.LocalUserService;
import me.snowhere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    LocalUserService localUserService;

    @Autowired
    RemoteUserServiceImpl remoteUserService;

    @Autowired
    UserDao userDao;

    @Override

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
       /* users.addAll(localUserService.getLocalUser());
        users.addAll(remoteUserService.getRemoteUser());*/
        users.addAll(userDao.getAllUser());
        users.addAll(userDao.getAllUser());
        return users;
    }

}
