package com.example.doanltweb.dao.db;

import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;
import java.util.List;

public class JDBIConnect {
    private static Jdbi jdbi;

    public static Jdbi get() {
        if (jdbi == null) {
            makeConnect();
        }
        return jdbi;
    }

    private static void makeConnect() {
        // Tạo URL JDBC từ DBProperties
        String host = DBProperties.host();
        int port = DBProperties.port();
        String dbname = DBProperties.dbname();
        String options = DBProperties.option();

        if (host == null || dbname == null || DBProperties.username() == null || DBProperties.password() == null) {
            throw new RuntimeException("Missing required DB environment variables.");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        if (options != null && !options.isEmpty()) {
            url += "?" + options;
        }

        MysqlDataSource src = new MysqlDataSource();
        src.setURL(url);
        src.setUser(DBProperties.username());
        src.setPassword(DBProperties.password());

        try {
            src.setUseCompression(true);
            src.setAutoReconnect(true);
        } catch (SQLException e) {
            throw new RuntimeException("Error configuring data source", e);
        }

        jdbi = Jdbi.create(src);
    }

    public static void main(String[] args) {
        List<Product> products = JDBIConnect.get()
                .withHandle(handle -> handle.createQuery("SELECT * FROM product").mapToBean(Product.class).list());
        System.out.println("Product list:");
        products.forEach(System.out::println);

        List<User> users = JDBIConnect.get()
                .withHandle(handle -> handle.createQuery("SELECT * FROM user").mapToBean(User.class).list());
        System.out.println("User list:");
        users.forEach(System.out::println);
    }
}
