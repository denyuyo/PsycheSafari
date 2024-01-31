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
	
	// すべての質問をデータベースから取得するメソッド
	public ArrayList<QuestionBean> getAllQuestions() throws SQLException, NamingException {
		Connection connection = null;
		
		ArrayList<QuestionBean> questions = new ArrayList<QuestionBean>();
		
		// SQLクエリ: questionsテーブルから全ての質問を選択
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
			
			// 質問内容を生成してリストに追加
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
			// 例外が発生した場合は処理を投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// 質問リストを呼び出し元に返す
		return questions;
	}
}