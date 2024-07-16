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
public class ProductDao {

    private DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Product create(Product product) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (account, balance, type, userId) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, product.getAccount());
        preparedStatement.setBigDecimal(2, product.getBalance());
        preparedStatement.setString(3, product.getType().name());
        preparedStatement.setLong(4, product.getUserId());
        preparedStatement.execute();
        close(preparedStatement, connection, null);
        return product;
    }

    public Product read(Product product) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
        preparedStatement.setLong(1, product.getId());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            product.setAccount(resultSet.getString("account"));
            product.setBalance(resultSet.getBigDecimal("balance"));
            product.setType(Type.valueOf(resultSet.getString("type")));
            product.setUserId(resultSet.getLong("userId"));
        } else {
            System.out.printf("Не найден продукт с id = {}", product.getId());
        }
        close(preparedStatement, connection, resultSet);
        return product ;
    }

    public List<Product> readByUser(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE userId = ?");
        preparedStatement.setLong(1, userId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        List<Product> list = new ArrayList<>();
        while (resultSet.next()) {
           list.add(new Product(
                   resultSet.getLong("id"),
                   resultSet.getString("account"),
                   resultSet.getBigDecimal("balance"),
                   Type.valueOf(resultSet.getString("type")),
                   resultSet.getLong("userId")));
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
