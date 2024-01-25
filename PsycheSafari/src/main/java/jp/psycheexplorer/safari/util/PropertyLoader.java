package jp.psycheexplorer.safari.util;

import java.util.ResourceBundle;

public class PropertyLoader {
	public static String getProperty(String name) {
		// プロパティファイル名が "application.properties" である ResourceBundle オブジェクトを取得
		ResourceBundle resource = ResourceBundle.getBundle("application");
		// name パラメーターで指定された名前のプロパティをプロパティファイルから読み取り、その値を文字列として返す
		return resource.getString(name);
	}
}
