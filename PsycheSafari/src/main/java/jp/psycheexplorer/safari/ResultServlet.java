package jp.psycheexplorer.safari;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

/**
 * Servlet implementation class ResultServlet
 */
//@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.jsp.result");
		
		HttpSession session = request.getSession(false);
		
		// セッションを取得（存在しない場合はnullを返す）
		if (session == null || session.getAttribute("user") == null) {
			// セッションが無効、またはユーザー情報がない場合、ログインページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.safari.login");
			response.sendRedirect(resultPage);
			return;
		}
		// 結果画面に転送
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.safari.result");
		
		HttpSession session = request.getSession(false);
		
		// セッションから UserBean を取得し、userId を取得
		UserBean user = (UserBean) session.getAttribute("user");
		int userId = user.getUserId();
		
		// 診断画面に戻るボタンが押された場合
	    if (request.getParameter("Back") != null) {
	        resultPage = PropertyLoader.getProperty("url.safari.personality");
	        response.sendRedirect(resultPage);
	        return; // リダイレクト後は以降の処理を実行しない
	    }
		
		try {
			// 質問リストを取得
			QuestionDao questionDao = new QuestionDao();
			ArrayList<QuestionBean> questions = questionDao.getAllQuestions();
			
			// ユーザーの回答をデータベースに保存
			ResponseDao responseDao = new ResponseDao();
			responseDao.saveUserResponses(request, userId, questions);
			
			// ユーザーの性格タイプを決定し、結果ページにリダイレクト
	        PersonalityResultDao personalityResultDao = new PersonalityResultDao();
	        String personalityType = personalityResultDao.determinePersonalityType(userId);

	        // 性格タイプの保存
	        personalityResultDao.savePersonalityResult(userId, personalityType);

	        // 性格タイプをセッションに保存
	        session.setAttribute("personalityType", personalityType);
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		response.sendRedirect(resultPage);
	}
}
