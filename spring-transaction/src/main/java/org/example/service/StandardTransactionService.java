package org.example.service;

import java.sql.SQLException;
import java.util.List;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StandardTransactionService {

    private final UserDao userDao;

    public StandardTransactionService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * declarative transaction
     */
    @Transactional
    public void joinAllUser(List<User> users) {
        for (User user : users) {
            try {
                userDao.joinUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
