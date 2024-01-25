package jp.psycheexplorer.safari.util;

public class CommonFunction {
	
	// 文字列の長さが特定の長さ以下かどうかをチェックする checkLen メソッド
	public static boolean checkLen(String text, int maxLength) {
		// 文字列 text が存在し、かつその長さが指定された最大長 maxLength 以下である場合に true を返し、それ以外の場合に false を返す
		return text.length() <= maxLength;
	}
	
	// 文字列が空かどうかをチェックする isNotBlank メソッド
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
}
