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
import jp.psycheexplorer.safari.dao.PersonalityResultDao;
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
		
		if (session == null || session.getAttribute("user") == null) {
			// セッションが存在しない、またはセッション内に user 属性が設定されていない場合は、ログインページへリダイレクト
			resultPage = PropertyLoader.getProperty("url.safari.login");
			response.sendRedirect(resultPage);
			return;
		}
		
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
		if (session == null || session.getAttribute("user") == null) {
			// セッションが無効なら、ログインページへリダイレクトする
			resultPage = PropertyLoader.getProperty("url.safari.login");
			response.sendRedirect(resultPage);
			return;
		}
		
		// 診断項目一覧を初期化
		List<QuestionBean> questions = null;
		
		if (request.getParameter("Submit") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			int userId = user.getUserId();
			
			try {
				QuestionDao questionDao = new QuestionDao();
				questions = questionDao.getAllQuestions();
				
				ResponseDao responseDao = new ResponseDao();
				responseDao.saveUserResponses(request, userId, questions);
				
				// 性格タイプを決定する処理を追加
				PersonalityResultDao personalityResultDao = new PersonalityResultDao();
				String personalityType = personalityResultDao.determinePersonalityType(userId);
				personalityResultDao.savePersonalityResult(userId, personalityType);
				
				// 性格タイプをセッションに保存
				session.setAttribute("personalityType", personalityType);
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
				// 内部エラーをクライアントに通知
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			resultPage = PropertyLoader.getProperty("url.safari.result");
			response.sendRedirect(resultPage);
		}
	}
}