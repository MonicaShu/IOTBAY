package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.DatabaseSetup;  // 确保导入你的数据库配置类

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if (emailExists(email)) {
            request.setAttribute("message", "Email already exists.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        } else {
            registerNewUser(email, password, firstName, lastName);
            response.sendRedirect("login.jsp");
        }
    }

    private boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DatabaseSetup.getConnection();  // 使用DatabaseSetup中的方法获取连接
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerNewUser(String email, String password, String firstName, String lastName) {
        String sql = "INSERT INTO users (email, password, firstName, lastName) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseSetup.getConnection();  // 使用DatabaseSetup中的方法获取连接
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);  // 未加密
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
