package jp.psycheexplorer.safari.util;

public class CommonFunction {
	
	// 文字列が空かどうかをチェックするメソッド
	public static boolean isBlank(String text) {
		// 文字列 text が存在せず、空である場合に true を返し、それ以外の場合に false を返す
		return text == null || text.isEmpty();
	}
	
	public static String check(String id, String password) {
		String errorMessages = "";
		
		if (isBlank(id)) {
			errorMessages = "ユーザーネームが入力されていません。<br>";
		}
		// += 演算子：errorMessages に新しいエラーメッセージを追加
		if (isBlank(password)) {
			errorMessages += "パスワードが入力されていません。<br>";
		}
		// 呼び出し元にエラーメッセージを表示
		return errorMessages;
	}
}
