<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<head>
	<title>診断結果</title>
	<link rel="stylesheet" href="design/result.css" type="text/css">
</head>
<body>
	<div class="container">
		<div class="form-box">
			<h2>診断結果</h2>
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