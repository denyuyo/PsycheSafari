<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ログイン画面</title>
	<link rel="stylesheet" href="css/master.css" type="text/css">
</head>
<body>
	<header>掲示板</header>
	<div class="form-container">
		<p class="login-message">ユーザーネームとパスワードを入力してログインしてください。</p>
		
		<% String errorMessages = (String) request.getAttribute("errorMessages");
			if (errorMessages != null) { %>
				<div class="error-messages" style="color: red;"><%=errorMessages%></div>
		<% } %>
		
		<!-- ログインフォーム -->
		<form action="/PsycheSafari/LoginServlet" method="post" name="Form1">
			<div class="form-group">
				<label for="id" class="itemName">UserName:</label>
				<input type="text" name="username" id="username" value="">
			</div>
			<div class="form-group">
				<label for="password" class="itemName">Password:</label>
				<input type="password" name="password" id="password">
			</div>
			<div class="form-group">
				<input class="button" type="submit" name="Login" value="ログイン">
			</div>
		</form>
	</div>
</body>
</html>