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
	<link rel="stylesheet" href="design/r1.css" type="text/css">
</head>
<body>
	<header>PsycheSafari</header>
	<div class="container">
		<div class="form-box">
			<form action="/PsycheSafari/ResultServlet" method="post">
				<p>あなたの性格タイプは <%= session.getAttribute("personalityType") %> です。</p>
				<div class="btn-radius-gradient-wrap">
					<input class="btn btn-radius-gradient" type="submit" name="Back" value="診断画面に戻る"></input>
				</div>
			</form>
		</div>
	</div>
	<script src="design/result.js"></script>
</body>
</html>