package org.example.service;

import java.sql.SQLException;
import java.util.List;
import org.example.annotation.MyTransactional;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MyTransactionalService {

    private final UserDao userDao;

    public MyTransactionalService(UserDao userDao) {
        this.userDao = userDao;
    }

    @MyTransactional
    public void useAbstractTrans(User user) {
        try {
            userDao.joinUser(user);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @MyTransactional
    public void useAbstractTrans(List<User> users) {
        joinAllUserFromAbstractTran(users);
    }

    /**
     * transaction abstraction : ALL
     */
    public void joinAllUserFromAbstractTran(List<User> users) {
        try {
            for (User user : users) {
                userDao.joinUser(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
