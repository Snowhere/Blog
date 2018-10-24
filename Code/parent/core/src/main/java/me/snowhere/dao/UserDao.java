package me.snowhere.dao;

import me.snowhere.dmo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Select("select id,name from user ")
    List<User> getAllUser();
}
