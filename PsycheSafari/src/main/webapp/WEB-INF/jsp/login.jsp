<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>性格診断ゲーム</title>
	<link rel="stylesheet" href="design/l1.css" type="text/css">
</head>
<body>
	<header>PsycheSafari</header>
	<div class="form-container">
		<p class="login-message">ようこそ！<br>ユーザーネームとパスワードを入力してください</p>
	
		<% String errorMessages = (String) request.getAttribute("errorMessages");
			if (errorMessages != null) { %>
				 <div class="error-messages" id="errorMessages"><%=errorMessages%></div>
		<% } %>
		
		<!-- ログインフォーム -->
		<form action="/PsycheSafari/LoginServlet" method="post" name="loginForm">
			<div class="form-group">
				<label for="username" class="itemName">ユーザーネーム:</label>
				<input type="text" name="username" id="username" value="">
			</div>
			<div class="form-group">
				<label for="password" class="itemName">パスワード:</label>
				<input type="password" name="password" id="password">
			</div>
			<div class="form-group">
				<input class="button" type="submit" name="Login" value="ログイン">
			</div>
		</form>
	</div>
</body>
</html>