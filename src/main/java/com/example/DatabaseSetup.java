package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:sqlite:myapp.db";  // 指定SQLite数据库的位置

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }


    public static void main(String[] args) {
        createUsersTable();
        createProductsTable();
        createOrdersTable();  
        createOrderLineItemsTable();
        createPaymentsTable();
        createShipmentsTable();
        createBankAccountsTable();


    }

    private static void executeSQL(String sql) {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Executed SQL: " + sql);
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + sql);
            e.printStackTrace();
        }
    }

    private static void createUsersTable() {
        executeSQL("CREATE TABLE IF NOT EXISTS users (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "email TEXT NOT NULL UNIQUE, " +
                   "password TEXT NOT NULL, " +
                   "role TEXT NOT NULL DEFAULT 'user', " +
                   "firstName TEXT, " +
                   "lastName TEXT, " +
                   "street TEXT, " +
                   "suburb TEXT, " +
                   "postcode TEXT, " +
                   "country TEXT, " +
                   "phone TEXT, " +
                   "dob TEXT, " +
                   "active BOOLEAN NOT NULL DEFAULT 1" +
                   ")");
    }

    private static void createProductsTable() {
        executeSQL("CREATE TABLE IF NOT EXISTS products (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "name TEXT NOT NULL, " +
                   "size TEXT, " +
                   "weight TEXT, " +
                   "brand TEXT, " +
                   "description TEXT, " +
                   "price REAL NOT NULL, " +
                   "stock INTEGER NOT NULL, " +
                   "category TEXT, " +
                   "image_url TEXT" +
                   ")");
    }

    private static void createOrdersTable() {
        executeSQL("CREATE TABLE IF NOT EXISTS orders (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "customer_id INTEGER, " + 
                   "status TEXT NOT NULL DEFAULT 'pending', " +
                   "created_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                   "updated_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                   "subtotal REAL NOT NULL, " +
                   "tax REAL NOT NULL, " +
                   "shipping_cost REAL NOT NULL, " +
                   "total_amount REAL NOT NULL, " +
                   "FOREIGN KEY(customer_id) REFERENCES users(id)" +
                   ")");
    }
    private static void createOrderLineItemsTable() {
        executeSQL("CREATE TABLE IF NOT EXISTS orders (" +
                    "CREATE TABLE IF NOT EXISTS order_line_items (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "order_id INTEGER NOT NULL, " +
                     "product_id INTEGER NOT NULL, " +
                     "quantity INTEGER NOT NULL, " +
                     "price REAL NOT NULL, " +
                     "FOREIGN KEY(order_id) REFERENCES orders(id), " +
                     "FOREIGN KEY(product_id) REFERENCES products(id)" +
                     ")");
       
    }
    
    private static void createPaymentsTable() {
        executeSQL("CREATE TABLE IF NOT EXISTS payments (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "order_id INTEGER NOT NULL, " +
                     "method TEXT NOT NULL, " +
                     "amount REAL NOT NULL, " +
                     "status TEXT NOT NULL, " +
                     "payment_date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                     "FOREIGN KEY(order_id) REFERENCES orders(id)" +
                     ")");
       
    }
    
    private static void createShipmentsTable() {
        executeSQL( "CREATE TABLE IF NOT EXISTS shipments (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "order_id INTEGER NOT NULL, " +
                     "method TEXT NOT NULL, " +
                     "status TEXT NOT NULL, " +
                     "street TEXT, " +
                     "city TEXT, " +
                     "state TEXT, " +
                     "postal_code TEXT, " +
                     "country TEXT, " +
                     "FOREIGN KEY(order_id) REFERENCES orders(id)" +
                     ")");
   
    }
    
    private static void createBankAccountsTable() {
        executeSQL( "CREATE TABLE IF NOT EXISTS bank_account (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "username TEXT NOT NULL UNIQUE, " +
                     "password TEXT NOT NULL, " +
                     "balance REAL NOT NULL" +
                     ")");
   
      
    }
    
}
