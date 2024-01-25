package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.psycheexplorer.safari.bean.QuestionBean;
import jp.psycheexplorer.safari.util.DbSource;

public class QuestionDao {
	
	public List<QuestionBean> getAllQuestions() throws SQLException, NamingException {
		List<QuestionBean> questions = new ArrayList<>();
		DataSource dataSource = DbSource.getDateSource();
		try (Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions");
			ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				QuestionBean question = new QuestionBean();
				question.setQuestionId(rs.getInt("question_id"));
				question.setQuestionText(rs.getString("question_text"));
				question.setOptionA(rs.getString("option_a"));
				question.setOptionB(rs.getString("option_b"));
				questions.add(question);
			}
		}
		return questions;
	}
}