function checkAllQuestions() {
	var questions = document.querySelectorAll('input[type="radio"]');
	var questionCount = questions.length / 2; // 各質問には2つの選択肢（AとB）があるため、2で割る
	var answeredCount = 0;
	
	for (var i = 0; i < questions.length; i++) {
		if (questions[i].checked) {
			answeredCount++;
		}
	}
	
	// 各質問に対する回答が選択されているかをチェック
	if (answeredCount < questionCount) {
		alert("全ての質問に回答してください。");
		return false; // フォームの送信を防ぐ
	}
	return true; // フォームの送信を許可
}   