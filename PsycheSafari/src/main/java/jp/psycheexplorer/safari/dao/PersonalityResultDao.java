package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.psycheexplorer.safari.util.DbSource;

public class PersonalityResultDao {
	
	/**
	 * @param userId ユーザーID
	 * @return 性格タイプ（外向型または内向型）
	 * @throws SQLException データベースアクセスエラーが発生した場合
	 * @throws NamingException JNDIルックアップに失敗した場合
	 */
	// 指定されたユーザーIDの性格タイプ（外向型または内向型）を決定
	public String determinePersonalityType(int userId) throws SQLException, NamingException {
		DataSource dataSource = DbSource.getDateSource();
		String personalityType = null;
		
		// SQLクエリ: ユーザーの選択肢Aと選択肢Bの数を比較し、多い方を性格タイプとして決定
		String sql = "SELECT CASE WHEN SUM(CASE WHEN selected_option = 'A' THEN 1 ELSE 0 END) > " +
				"SUM(CASE WHEN selected_option = 'B' THEN 1 ELSE 0 END) THEN '外向型' ELSE '内向型' END " +
				"AS personality_type FROM responses WHERE user_id = ? GROUP BY user_id";
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					personalityType = result.getString("personality_type");
				}
			}
		}
		return personalityType;
	}
	
	// ユーザーの性格タイプをデータベースの personality_results テーブルに保存
	public void savePersonalityResult(int userId, String personalityType) throws SQLException, NamingException {
		DataSource dataSource = DbSource.getDateSource();
		// SQLクエリ: 指定されたユーザーIDと性格タイプを personality_results テーブルに挿入
		String sql = "INSERT INTO personality_results (user_id, personality_type) VALUES (?, ?)";
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);
			statement.setString(2, personalityType);
			statement.executeUpdate();
		}
	}
}