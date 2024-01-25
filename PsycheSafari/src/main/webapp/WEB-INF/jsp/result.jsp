<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<head>
    <title>診断結果</title>
</head>
<body>
    <h2>診断結果</h2>
    <p>あなたの性格タイプは <%= session.getAttribute("personalityType") %> です。</p>
</body>
</html>