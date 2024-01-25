package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.psycheexplorer.safari.bean.UserBean;
import jp.psycheexplorer.safari.util.DbSource;

public class UserDao {

    public UserBean findUserByUsernameAndPassword(String username, String password) throws SQLException, NamingException {
        DataSource dataSource = DbSource.getDateSource();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	UserBean user = new UserBean();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        }
        return null; // ユーザーが見つからない場合はnullを返す
    }
}
