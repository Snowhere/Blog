package me.snowhere.dao;

import me.snowhere.dmo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Select("select id,name from user ")
    List<User> getAllUser();

    @Insert("insert into user (name) values(#{user.name})")
    @Options(useGeneratedKeys=true, keyProperty="user.id", keyColumn="id")
    int addUser(@Param("user")User user);

    @Insert({"<script>insert into user (name) values ",
            "<foreach collection =\"users\" item=\"user\" separator =\",\">",
            "     ( #{user.name})",
            "</foreach >",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty="user.id", keyColumn="id")
    int addUserList(@Param("users") List<User> users);
}
