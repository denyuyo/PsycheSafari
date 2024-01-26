package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.psycheexplorer.safari.bean.QuestionBean;
import jp.psycheexplorer.safari.util.DbSource;

public class QuestionDao {
	
	public ArrayList<QuestionBean> getAllQuestions() throws SQLException, NamingException {
		Connection connection = null;
		
		ArrayList<QuestionBean> questions = new ArrayList<QuestionBean>();
		
		String sql =
			"SELECT "
			+ 	"question_id, "
			+ 	"question_text, "
			+ 	"option_a, "
			+ 	"option_b "
			+ "FROM "
			+ 	"questions;";
		
		try  {
			// データベース接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQLクエリをセットして、実行する
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				QuestionBean question = new QuestionBean();
				question.setQuestionId(resultSet.getInt("question_id"));
				question.setQuestionText(resultSet.getString("question_text"));
				question.setOptionA(resultSet.getString("option_a"));
				question.setOptionB(resultSet.getString("option_b"));
				questions.add(question);
			}
			statement.close();
		}catch (Exception e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// 最後に呼び出し元に返す
		return questions;
	}
}