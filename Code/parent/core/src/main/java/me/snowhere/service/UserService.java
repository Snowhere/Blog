package me.snowhere.service;

import me.snowhere.datasource.DataSource;
import me.snowhere.datasource.DataSourceEnum;
import me.snowhere.dmo.User;

import java.util.List;
@DataSource(DataSourceEnum.SLAVE)
public interface UserService {
    @DataSource(DataSourceEnum.MASTER)
    List<User> getAllUser();
}
