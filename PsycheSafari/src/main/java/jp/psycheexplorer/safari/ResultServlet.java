package jp.psycheexplorer.safari;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		if (session == null || session.getAttribute("personalityCompleted") == null) {
	        // フラグが設定されていない場合、診断画面にリダイレクト
			resultPage = PropertyLoader.getProperty("url.safari.personality");
	        response.sendRedirect(resultPage);
	        return;
	    }

	    // フラグをセッションから削除
	    session.removeAttribute("personalityCompleted");
	    
		// 結果画面に転送
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resultPage = PropertyLoader.getProperty("url.safari.result");
		
		// 診断画面に戻るボタンが押された場合
		if (request.getParameter("Back") != null) {
			
			resultPage = PropertyLoader.getProperty("url.safari.personality");
			response.sendRedirect(resultPage);
		}
	}
}
