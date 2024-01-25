package jp.psycheexplorer.safari;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.psycheexplorer.safari.bean.QuestionBean;
import jp.psycheexplorer.safari.bean.UserBean;
import jp.psycheexplorer.safari.dao.QuestionDao;
import jp.psycheexplorer.safari.dao.ResponseDao;
import jp.psycheexplorer.safari.util.PropertyLoader;

//@WebServlet("/PersonalityServlet")
public class PersonalityServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.jsp.personality");
		
		HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user"); // セッションからUserBeanを取得
        int userId = user.getUserId(); // UserBeanからuser_idを取得
        
		try {
			QuestionDao questionDao = new QuestionDao();
			List<QuestionBean> questions = questionDao.getAllQuestions();
			
			request.setAttribute("questions", questions);
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		// ログイン済みを確認されたので、診断画面に転送
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.personality");
		
		// セッションを取得して、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
		
		// 診断項目一覧を初期化
		QuestionBean questionBean =  null;
		
		// クリアボタン押下時、特定のフィールドをリセット
		if (request.getParameter("clear") != null) {
			// クリア時のデフォルト値
			questionBean = new QuestionBean();
		
			try {
				QuestionDao questionDao = new QuestionDao();
				List<QuestionBean> questions = questionDao.getAllQuestions();
				
				request.setAttribute("questions", questions);
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			}
		// ユーザーがフォームに入力したデータをセッション内に保存
		session.setAttribute("QuestionBean", questionBean);
		// 問題ない場合、診断項目一覧を表示
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
		return;
		// 診断結果を見るボタン押下時、診断情報を保持して結果画面に転送
		}else if(request.getParameter("Submit") != null) {
	        
	        UserBean user = (UserBean) session.getAttribute("user");
	        int userId = user.getUserId();
	        
	        // ResponseDao オブジェクトを作成
	        ResponseDao responseDao = new ResponseDao();

	        try {
	            // ユーザーの回答をデータベースに保存
	            responseDao.saveUserResponses(request, userId);

	        } catch (SQLException | NamingException ex) {
	            ex.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        }
	        // 結果表示ページにフォワード
	        resultPage = PropertyLoader.getProperty("url.safari.result");
			response.sendRedirect(resultPage);
	    }
	}
}
