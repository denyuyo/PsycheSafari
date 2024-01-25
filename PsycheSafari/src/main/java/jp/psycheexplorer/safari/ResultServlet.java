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
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false); // false を指定して、既存のセッションがある場合にのみ取得
		    if (session == null || session.getAttribute("user") == null) {
		        // セッションが存在しない、またはセッション内に user 属性が設定されていない場合は、ログインページへリダイレクトする
		        response.sendRedirect(request.getContextPath() + "/login.jsp");
		        return;
		    }

		    // セッションから UserBean を取得し、userId を取得
		    UserBean user = (UserBean) session.getAttribute("user");
		    int userId = user.getUserId();

		    try {
		        // 質問リストを取得
		        QuestionDao questionDao = new QuestionDao();
		        List<QuestionBean> questions = questionDao.getAllQuestions();

		        // ユーザーの回答をデータベースに保存
		        ResponseDao responseDao = new ResponseDao();
		        responseDao.saveUserResponses(request, userId, questions);

		        // 続けて、ユーザーの性格タイプを決定し、結果ページにリダイレクトする処理をここに追加
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        return;
	    }
	}

}
