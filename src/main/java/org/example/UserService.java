package org.example;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(String username) {
        User user = new User();
        user.setUsername(username);
        try {
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    public void delete(Long id) {
        User user = new User();
        user.setId(id);
        try {
            userDao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public User read(Long id) {
        User user = new User();
        user.setId(id);
        try {
            userDao.read(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<User> readAll() {
        List<User> list;
        try {
            list = userDao.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return list;
    }
}
