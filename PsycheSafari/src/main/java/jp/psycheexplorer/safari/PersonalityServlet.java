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

//@WebServlet("/PersonalityServlet")
public class PersonalityServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resultPage = PropertyLoader.getProperty("url.jsp.personality");
		
		String errorMessages = "";
		
		HttpSession session = request.getSession(false);
		
		// セッションを取得（存在しない場合はnullを返す）
		if (session == null || session.getAttribute("user") == null) {
			// セッションが無効、またはユーザー情報がない場合、ログインページにリダイレクト
			resultPage = PropertyLoader.getProperty("url.safari.login");
			response.sendRedirect(resultPage);
			return;
		}
		
		try {
			// 質問データをデータベースから取得
			QuestionDao questionDao = new QuestionDao();
			ArrayList<QuestionBean> questions = questionDao.getAllQuestions();
			
			// 質問データをリクエスト属性に設定
			request.setAttribute("questions", questions);
		} catch (SQLException | NamingException e) {
			errorMessages = "エラーが発生しました。";
			// もし例外が発生した場合は、エラーページに転送します
			request.setAttribute("errorMessage", errorMessages);
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			request.getRequestDispatcher(resultPage).forward(request, response);
			return;
		}
		
		// 診断画面に転送
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
		
		String errorMessages = "";
		
		// セッションを取得（存在しない場合はnullを返す）
		HttpSession session = request.getSession(false);
		
		// 診断項目のリストを初期化
		ArrayList<QuestionBean> questions = null;
		
		int countA = 0;
		
		
		 // 診断結果を見るボタンが押下された場合
		if (request.getParameter("Result") != null) {
			// セッションからユーザー情報を取得
			UserBean user = (UserBean) session.getAttribute("user");
			int userId = user.getUserId();
			
			try {
				// 全質問を取得
				QuestionDao questionDao = new QuestionDao();
				questions = questionDao.getAllQuestions();
				
				QuestionBean question = new QuestionBean();
								
				// radioボタンの選択を確認するループ処理
				for (QuestionBean questionData : questions) {
					
					// ユーザーがフォームに入力したQuestionIdに紐づくradioボタンの値（answer）を取得
					question.setOptionA(request.getParameter("answer" + questionData.getQuestionId()) );
					
					// answerが未選択の場合
					if(request.getParameter("answer" + questionData.getQuestionId()) == null) {
						errorMessages = "ラジオボタンが選択されていません。少なくとも1つのオプションを選択してください。";
						
						// エラーメッセージをリクエスト属性にセット
						request.setAttribute("errorMessages", errorMessages);
						request.setAttribute("questions", questions);
						
						RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
						dispatcher.forward(request, response);
						return;
					}
					
					if(request.getParameter("answer" + questionData.getQuestionId()).equals("A")) {
						countA += 1;
					}
				}
				
				// セッションに入力内容を保存
				session.setAttribute("QuestionBean", question);
				
				// ユーザーの回答をデータベースに保存
				ResponseDao responseDao = new ResponseDao();
				responseDao.saveUserResponses(request, userId, questions);
				
				// 結果の判定
				String personalityType = "内向型";
				if(countA >= 3) {
					personalityType = "外向型";
				}
				
				// ユーザーの性格タイプを決定し、結果を保存
				PersonalityResultDao personalityResultDao = new PersonalityResultDao();
				personalityResultDao.savePersonalityResult(userId, personalityType);
				
				// 性格タイプをセッションに保存
				session.setAttribute("personalityType", personalityType);
				
				// 処理が正常に完了したらセッションにフラグを設定
			    session.setAttribute("personalityCompleted", Boolean.TRUE);
			} catch (SQLException | NamingException e) {
				errorMessages = "エラーが発生しました。";
				// もし例外が発生した場合は、エラーページに転送します
				request.setAttribute("errorMessage", errorMessages);
				resultPage = PropertyLoader.getProperty("url.jsp.error");
				request.getRequestDispatcher(resultPage).forward(request, response);
				return;
			}
			resultPage = PropertyLoader.getProperty("url.safari.result");
			response.sendRedirect(resultPage);
		}
	}
}