package jp.psycheexplorer.safari;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.psycheexplorer.safari.bean.UserBean;
import jp.psycheexplorer.safari.dao.UserDao;
import jp.psycheexplorer.safari.util.CommonFunction;
import jp.psycheexplorer.safari.util.PropertyLoader;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.safari.personality");
		
		// セッションを取得し、存在しない場合は null を返す
		HttpSession session = request.getSession(false);
		
		// セッションが存在し、かつ "user" という属性がセッションに存在する場合（ユーザーログ済み）、診断画面へリダイレクト
		if (session != null && session.getAttribute("user") != null) {
			response.sendRedirect(resultPage);
			return;
		}
		// ログインしていない場合はログイン画面に転送
		resultPage = PropertyLoader.getProperty("url.jsp.login");
		request.getRequestDispatcher(resultPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
		
		String resultPage = PropertyLoader.getProperty("url.jsp.login");
		
		// フォームから送信されたユーザーネームとパスワードを取得
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String errorMessages = "";
		
		// CommonFunctionのcheckメソッドを呼び出して妥当性チェックを行う
		errorMessages = CommonFunction.check(username, password);
		
		
		// バリデーションエラーがある場合、ログイン画面でエラーメッセージを表示
		if (!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}
		
		try {
			UserDao dao = new UserDao();
			// ユーザーが入力したIDとパスワードをデータベースと照合
			UserBean user = dao.findUserByUsernameAndPassword(username, password);
			// もしデータベースに該当のアカウントが存在しない場合
			if (user == null) {
				errorMessages = "ログインできません。";
				// エラーメッセージをログインフォームに表示
				request.setAttribute("errorMessages",errorMessages);
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
			}
			/*
			 * ユーザーログインが無事にログイン出来たら
			 */
			
			// セッションを取得または新しいセッションを作成して、ログインできるようにする
			HttpSession session = request.getSession(true);
			
			// sessionにuserを関連付けて、どのユーザーがログインしているか識別
			session.setAttribute("user", user);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 問題がなくログインに成功した場合、診断画面に遷移
		resultPage = PropertyLoader.getProperty("url.safari.personality");
		response.sendRedirect(resultPage);
	}

}
