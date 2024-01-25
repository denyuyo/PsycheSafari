package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import jp.psycheexplorer.safari.bean.QuestionBean;
import jp.psycheexplorer.safari.util.DbSource;

public class ResponseDao {
	
	public void saveUserResponses(HttpServletRequest request, int userId, List<QuestionBean> questions) throws SQLException, NamingException {
		Connection connection = null;
		
		// SQLクエリ: ユーザーの回答を responses テーブルに保存
		String insertSql = "INSERT INTO responses (question_id, selected_option, user_id) VALUES (?, ?, ?)";
		
		try {
			// データベース接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQLクエリをセットして、実行する
			PreparedStatement statement = connection.prepareStatement(insertSql);
			
			// 各質問に対するユーザーの回答をデータベースに保存
			for (QuestionBean question : questions) {
				// リクエストから選択された回答を取得
				String selectedOption = request.getParameter("answer" + question.getQuestionId());
				if (selectedOption != null && !selectedOption.trim().isEmpty()) {
					statement = connection.prepareStatement(insertSql);
					// PreparedStatement に値をセット
					statement.setInt(1, question.getQuestionId()); // 質問ID
					statement.setString(2, selectedOption); // 選択されたオプション (A or B)
					statement.setInt(3, userId); // ユーザーID
					// SQLクエリを実行して回答を保存
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
    }
}