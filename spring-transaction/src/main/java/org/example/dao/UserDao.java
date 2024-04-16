package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.example.entity.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

/**
 * 유저를 한번에 DB에 저장해야한다.
 * 저장하다가 한명이라도 저장이 안되면 이전에 저장했던 유저들을 다 삭제해야한다.
 */
@Repository
public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void joinUser(User user, Connection conn) throws SQLException {
        insertUser(user, conn);
    }

    public void joinUser(User user) throws SQLException {
        Connection conn = DataSourceUtils.getConnection(dataSource);

        insertUser(user, conn);
    }

    public void joinUser(User user, EntityManager em) {
        if (Objects.equals(user.getName(), "A")) {
            throw new IllegalArgumentException();
        }
        em.persist(user);
    }

    private static void insertUser(User user, Connection conn) throws SQLException {
        if (Objects.equals(user.getName(), "A")) {
            throw new IllegalArgumentException();
        }

        String sql = "INSERT INTO user(id, name, age) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setInt(3, user.getAge());
        pstmt.executeUpdate();
    }
}
