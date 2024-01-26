function validateForm() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var errorMessages = document.getElementById('errorMessages');

    if (username === '' || password === '') {
        errorMessages.textContent = 'ユーザーネームとパスワードを入力してください。';
        return false;
    }

    errorMessages.textContent = '';
    return true;
}
