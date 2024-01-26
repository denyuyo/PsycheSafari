// JavaScriptの関数: 全ての質問に回答が選択されているかを確認する
function checkAllQuestions() {
	// フォーム内の全てのラジオボタンを取得
	var questions = document.querySelectorAll('input[type="radio"]');

	// 各質問には2つの選択肢（AとB）があるため、質問の総数を計算
	var questionCount = questions.length / 2;

	// 選択された回答のカウンターを初期化
	var answeredCount = 0;

	// 全てのラジオボタンをループして、選択された回答の数をカウント
	for (var i = 0; i < questions.length; i++) {
		if (questions[i].checked) {
			answeredCount++;
		}
	}

	// 各質問に対する回答が選択されているかをチェック
	if (answeredCount < questionCount) {
		// 未回答の質問がある場合、アラートを表示して送信を防ぐ
		alert("全ての質問に回答してください。");
		return false; // フォームの送信を阻止
	}

	// すべての質問に回答が選択されている場合、フォームの送信を許可
	return true;
}
