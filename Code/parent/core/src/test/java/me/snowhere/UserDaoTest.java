package me.snowhere;

import me.snowhere.dao.UserDao;
import me.snowhere.dmo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void testDao() {
        User user = new User();
        user.setName("test1");
        User user2 = new User();
        user2.setName("test2");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        userDao.addUserList(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(1);
        user.setName("test1");
        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                System.out.println("主键重复");
            } else {
                System.out.println("其他异常");
            }
        }

    }
}
