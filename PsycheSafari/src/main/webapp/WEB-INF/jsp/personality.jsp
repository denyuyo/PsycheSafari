<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
   	import="jp.psycheexplorer.safari.bean.QuestionBean"
   	import="jp.psycheexplorer.safari.util.CommonFunction"
	import="java.text.SimpleDateFormat"
   	import="java.util.ArrayList"
   	import="java.util.List"
   	import="java.util.Date"
   	import="java.util.Collections"%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	// 診断項目一覧をすべて取得
	List<QuestionBean> questions = (List<QuestionBean>) request.getAttribute("questions");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>診断画面</title>
</head>
<body>
    <h2>性格診断</h2>
    <form action="/PsycheSafari/ResultServlet" method="post" id="form">
        <% if (request.getAttribute("errorMessages") != null) { %>
				<p class="error" style="color: red;"><%= request.getAttribute("errorMessages") %></p>
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
        <input type="submit" name="Result" value="診断結果を見る">
        <input class="button" type="submit" name="clear" value="クリア">
    </form>
</body
</html>