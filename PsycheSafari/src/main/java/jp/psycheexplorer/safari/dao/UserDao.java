package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.psycheexplorer.safari.bean.UserBean;
import jp.psycheexplorer.safari.util.DbSource;

public class UserDao {
	
	// ユーザー名とパスワードを使用して、データベース内のユーザー情報を検索するメソッド
	public UserBean findUserByUsernameAndPassword(String username, String password) throws SQLException, NamingException {
		Connection connection = null;
		
		// SQLクエリ: データベース内のユーザー情報と入力値の一致を判定
		String sql =
			"SELECT "
			+ 	"user_id, "
			+ 	"username, "
			+ 	"password "
			+ "FROM "
			+ 	"users "
			+ "WHERE "
			+ 	"username = ? "
			+ "AND "
			+ 	"password = ?;";
		
		try {
			// データベース接続を確立
			connection = DbSource.getDateSource().getConnection();
			
			// SQLクエリをセットして、実行する
			PreparedStatement statement = connection.prepareStatement(sql);
			
			// パラメータをセット
			statement.setString(1, username);
			statement.setString(2, password);
			
			// クエリを実行
			ResultSet resultSet = statement.executeQuery();
			
			// ユーザーが存在すれば、UserBeanを作成して返す
			if (resultSet.next()) {
				UserBean user = new UserBean();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				return user;
			}
		}catch (Exception e) {
			// 例外が発生した場合は処理を投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// ユーザーが存在しなければnullを返す
		return null;
	}
}