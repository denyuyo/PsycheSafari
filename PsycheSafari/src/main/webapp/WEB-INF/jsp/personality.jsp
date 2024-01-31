<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="jp.psycheexplorer.safari.bean.QuestionBean"
	import="java.util.ArrayList"
%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "n	o-cache");
	response.setDateHeader("Expires", 0);
	
	// 診断項目一覧をすべて取得
	ArrayList<QuestionBean> questions = (ArrayList<QuestionBean>) request.getAttribute("questions");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>性格診断ゲーム</title>
	<link rel="stylesheet" href="design/p2.css" type="text/css">
</head>
<body>
	<header>PsycheSafari</header>
	<form action="/PsycheSafari/PersonalityServlet" method="post" id="form">
		<% String errorMessages = (String) request.getAttribute("errorMessages");
			if (errorMessages != null) { %>
				 <div class="error-messages" id="errorMessages"><%=errorMessages%></div>
		<% } %>
		
		<%-- 質問リスト（questions）をループして、各質問に対するラジオボタンを生成 --%>
		<% for (QuestionBean question : questions) { %>
			<p><%= question.getQuestionText() %></p>
			<%-- option_a のラジオボタン --%>
			<input type="radio" name="answer<%= question.getQuestionId() %>" id="optionA_<%= question.getQuestionId() %>" value="A">
			<label for="optionA_<%= question.getQuestionId() %>"><%= question.getOptionA() %></label><br>
			
			<%-- option_b のラジオボタン --%>
			<input type="radio" name="answer<%= question.getQuestionId() %>" id="optionB_<%= question.getQuestionId() %>" value="B">
			<label for="optionB_<%= question.getQuestionId() %>"><%= question.getOptionB() %></label><br>
		<% } %>
		<input class="btn btn-malformation btn-malformation--pastel" type="submit" name="Result" value="診断結果を見る">
	</form>
</body
</html>