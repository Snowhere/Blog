package me.snowhere.service.impl;

import me.snowhere.dao.UserDao;
import me.snowhere.dmo.User;
import me.snowhere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao demoDao;

    @Override
    public List<User> getAllUser() {
        return demoDao.getAllUser();
    }
}
