package jp.psycheexplorer.safari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import jp.psycheexplorer.safari.bean.QuestionBean;
import jp.psycheexplorer.safari.util.DbSource;

public class ResponseDao {

    public void saveUserResponses(HttpServletRequest request, int userId, List<QuestionBean> questions) throws SQLException, NamingException {
        DataSource dataSource = DbSource.getDateSource();
        Connection connection = null;
        PreparedStatement pstmt = null;
        
        try {
            connection = dataSource.getConnection();
            // トランザクションを開始
            connection.setAutoCommit(false);
            
            String insertSql = "INSERT INTO responses (question_id, selected_option, user_id) VALUES (?, ?, ?)";
            
            // 各質問に対するユーザーの回答をデータベースに保存
            for (QuestionBean question : questions) {
                String selectedOption = request.getParameter("answer" + question.getQuestionId());
                if (selectedOption != null && !selectedOption.trim().isEmpty()) {
                    pstmt = connection.prepareStatement(insertSql);
                    pstmt.setInt(1, question.getQuestionId());
                    pstmt.setString(2, selectedOption);
                    pstmt.setInt(3, userId);
                    pstmt.executeUpdate();
                }
            }
            
            // トランザクションをコミット
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                // エラーが発生した場合はロールバック
                connection.rollback();
            }
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                // オートコミットを元に戻す
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}