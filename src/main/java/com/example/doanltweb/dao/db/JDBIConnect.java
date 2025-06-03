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
            try {
                makeConnect();
            } catch (SQLException e) {
                throw new RuntimeException("Không thể kết nối DB", e);
            }
        }
        return jdbi;
    }

    public static void makeConnect() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(DBProperties.HOST);
        ds.setPort(Integer.parseInt(DBProperties.PORT));
        ds.setDatabaseName(DBProperties.DBNAME);
        ds.setUser(DBProperties.USERNAME);
        ds.setPassword(DBProperties.PASSWORD);

        // Cấu hình SSL (vì Aiven yêu cầu sslMode=REQUIRED)
        ds.setUseSSL(true);
        ds.setRequireSSL(true);
        ds.setVerifyServerCertificate(false); // Có thể bật lại nếu bạn cài chứng chỉ CA
        try {
            ds.setAutoReconnect(true);
            ds.setUseCompression(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        jdbi = Jdbi.create(ds);
    }

    public static void main(String[] args) {
        List<Product> products = JDBIConnect.get()
                .withHandle(handle -> handle.createQuery("SELECT * FROM product").mapToBean(Product.class).list());
        System.out.println("Danh sách sản phẩm:");
        products.forEach(System.out::println);

        List<User> users = JDBIConnect.get()
                .withHandle(handle -> handle.createQuery("SELECT * FROM user").mapToBean(User.class).list());
        System.out.println("Danh sách người dùng:");
        users.forEach(System.out::println);
    }
}
