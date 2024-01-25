package jp.psycheexplorer.safari.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbSource {
	// データベースへの接続情報を格納するための変数を用意
		private static DataSource dataSource;
		
		// いっぱい作られないコンストラクタを用意（他も見えてないだけであるから空でもいい）
		private DbSource() {
		}
		
		// シングルトンインスタンスを取得するメソッド
		public static synchronized DataSource getDateSource() throws NamingException {
			if (dataSource == null) {
				// コンテキストを取得し、データソースを初期化
				InitialContext context = new InitialContext();
				// データベース接続の情報を代入（参照先をみにいっている）
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/datasource"); 
			}
			return dataSource;
		}
}
