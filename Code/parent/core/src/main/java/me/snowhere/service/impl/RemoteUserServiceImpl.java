package me.snowhere.service.impl;

import me.snowhere.dao.UserDao;
import me.snowhere.datasource.DataSource;
import me.snowhere.datasource.DataSourceEnum;
import me.snowhere.dmo.User;
import me.snowhere.service.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DataSource(DataSourceEnum.MASTER)
public class RemoteUserServiceImpl implements RemoteUserService {

    @Autowired
    UserDao demoDao;

    @Override
    @DataSource(DataSourceEnum.SLAVE)
    public List<User> getRemoteUser() {
        return demoDao.getAllUser();
    }
}
