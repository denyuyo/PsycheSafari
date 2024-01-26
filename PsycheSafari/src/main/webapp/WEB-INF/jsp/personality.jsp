<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="jp.psycheexplorer.safari.bean.QuestionBean"
	import="java.util.List"
%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "n	o-cache");
	response.setDateHeader("Expires", 0);
	
	// 診断項目一覧をすべて取得
	List<QuestionBean> questions = (List<QuestionBean>) request.getAttribute("questions");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>性格診断ゲーム</title>
	<link rel="stylesheet" href="design/personality.css" type="text/css">
</head>
<body>
	<h2>性格診断</h2>
	<form action="/PsycheSafari/ResultServlet" method="post" id="form" onsubmit="return checkAllQuestions();">
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
		<input class="button" type="submit" name="Result" value="診断結果を見る">
	</form>
	<script src="design/personality.js"></script>
</body
</html>