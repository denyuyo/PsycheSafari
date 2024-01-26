package jp.psycheexplorer.safari.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.psycheexplorer.safari.bean.QuestionBean;

public class CommonFunction {
	
	// 文字列が空かどうかをチェックするメソッド
	public static boolean isBlank(String text) {
		// 文字列 text が存在せず、空である場合に true を返し、それ以外の場合に false を返す
		return text == null || text.isEmpty();
	}
	
	public static String check(String id, String password) {
		String errorMessages = "";
		
		if (isBlank(id)) {
			errorMessages = "IDが入力されていません。<br>";
		}
		// += 演算子：errorMessages に新しいエラーメッセージを追加
		if (isBlank(password)) {
			errorMessages += "パスワードが入力されていません。<br>";
		}
		// 呼び出し元にエラーメッセージを表示
		return errorMessages;
	}
	
	// 全ての質問に回答があるかどうかをチェックするメソッド
	public static String checkRadioButtonSelection(HttpServletRequest request, List<QuestionBean> questions) {
		StringBuilder errorMessages = new StringBuilder();
		boolean isErrorPresent = false;
		
		for (QuestionBean question : questions) {
			String selectedOption = request.getParameter("answer" + question.getQuestionId());
			if (selectedOption == null || (!selectedOption.equals("A") && !selectedOption.equals("B"))) {
				if (isErrorPresent) {
					errorMessages.append("<br>");
				}
				errorMessages.append("質問 '").append(question.getQuestionText()).append("' に回答してください。");
				isErrorPresent = true;
			}
		}
		return errorMessages.toString();
	}
}
