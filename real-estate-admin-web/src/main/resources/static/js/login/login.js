let onReady = function() {
    initICheck();
}

let checkDuplicateData  = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        username = $frm.find('input[name="username"]').val();

    console.log("username", username);

    if (!checkNullOrEmptyValue(username)) {
        oneBtnModal('이메일을 입력해주세요.');
        return;
    }

    $.ajax({
        url: "/administrators/check-duplicate/username/" + username,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {

            let isDuplicate = result.data;
            console.log("isDuplicate", isDuplicate);

            if (isDuplicate) {
                oneBtnModal('이미 존재하는 이메일 입니다.');
                $('#emailCheckYn').val(false);
            } else {
                oneBtnModal('사용가능한 이메일 입니다.');
                $('#emailCheckYn').val(true);
            }
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    })
}

let checkValidPassword = function() {

    const $frm = $('form[name=frmRegister]');
    let $password = $frm.find('input[name="password"]'),
        $field = $frm.find('.passwordMsg'),
        password = $frm.find('input[name="password"]').val(),
        repeat = $frm.find('input[name="confirmPassword"]').val();

    console.log("passowrd", $password)
    console.log("field", $field)
    console.log("password", password)

    if (!checkNullOrEmptyValue(password)) {
        drawErrorMessage($field, '비밀번호를 입력해주세요.');
        return false;
    }

    if (password.length < 6) {
        $('#pwValidCheckYn').val(false);
        drawErrorMessage($field, '6자리 이상의 미빌번호를 입력해주세요.');
        return false;
    } else {
        $('#pwValidCheckYn').val(true);
    }

    if (password !== repeat) {
        $('#pwEqualCheckYn').val(false);
        drawErrorMessage($field, '동일한 비밀번호가 아닙니다.');
        return false;
    } else {
        $('#pwEqualCheckYn').val(true);
        drawSuccessMessage($field, '비밀번호가 동일합니다.');
        return false;
    }

};

let signUpModal = function(e) {
    e.preventDefault();

    $('#sign-up-modal').modal('show');
}

let initICheck = function() {
    $('input[type="checkbox"], input[type="radio"]').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_flat-blue'
    });
};

let signUp = function(e) {
    e.preventDefault();

    let isPrivacyAgree = $('#privacyAgree').prop('checked');
    if (!isPrivacyAgree) {
        oneBtnModal('개인 정보 수집 및 이용에 동의해주세요.');
        return;
    }

    let isPolicyAgree = $('#policyAgree').prop('checked');
    if (!isPolicyAgree) {
        oneBtnModal('이용약관에 동의해주세요.');
        return;
    }

    console.log("ee")
}

$(document).ready(onReady)
    .on('click', '#btnSignUpModal', signUpModal)
    .on('click', '#btnSignUp', signUp)
    .on('click', '#sign-up-modal .btnCheckDuplicate', checkDuplicateData)
    .on('blur', 'input[name="confirmPassword"]', checkValidPassword);
