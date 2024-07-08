package org.example;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username) VALUES (?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.execute();
        close(preparedStatement, connection, null);
        return user;
    }

    public User update(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setLong(2, user.getId());
        preparedStatement.execute();
        close(preparedStatement, connection, null);
        return user;
    }

    public User read(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        preparedStatement.setLong(1, user.getId());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            user.setUsername(resultSet.getString("username"));
        } else {
            System.out.printf("Не найден пользователь с id = {}", user.getId());
        }
        close(preparedStatement, connection, resultSet);
        return user;
    }

    public void delete(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?");
        preparedStatement.setLong(1, user.getId());
        preparedStatement.execute();
        close(preparedStatement, connection, null);
    }

    public List<User> readAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        List<User> list = new ArrayList<>();
        while (resultSet.next()) {
           list.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
        }
        close(preparedStatement, connection, resultSet);
        return list;
    }

    private void close(PreparedStatement preparedStatement, Connection connection, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();
    }
}
