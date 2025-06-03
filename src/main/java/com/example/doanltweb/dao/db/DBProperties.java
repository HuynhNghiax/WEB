package com.example.doanltweb.dao.db;

public class DBProperties {
    public static String host() {
        return System.getenv("DB_HOST");
    }

    public static int port() {
        try {
            return Integer.parseInt(System.getenv("DB_PORT"));
        } catch (NumberFormatException e) {
            return 3306; // Mặc định MySQL port
        }
    }

    public static String username() {
        return System.getenv("DB_USERNAME");
    }

    public static String password() {
        return System.getenv("DB_PASSWORD");
    }

    public static String dbname() {
        return System.getenv("DB_NAME");
    }

    public static String option() {
        return System.getenv("DB_OPTION");
    }
}
