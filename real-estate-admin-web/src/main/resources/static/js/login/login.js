let onReady = function() {
    initICheck();
    settingExceptionMsg();

    let $frm = $('form[name="loginForm"]');
    $('form[name="loginForm"] *').keydown(function (e) {
        if (e.keyCode === 13) {
            $frm.submit();
        }
    })
}

let settingExceptionMsg = function() {

    if (exceptionMessage == null) {
        return;
    }

    console.log(exceptionMessage);
    console.log(exception);

    if (exception === 'CredentialsExpiredException') {
        oneBtnModal(exceptionMessage, function () {
            //비밀번호 변경하기 페이지 이동
        });
    } else if (exception === 'InternalAuthenticationServiceException') { //임시비밀번호 발급된 계정
        // oneBtnModal("임시비밀번호가 부여된 계정입니다. 비밀번호 변경 페이지로 이동합니다.", function () {
        //     //비밀번호 변경하기 페이지 이동
        //     $form.attr("action", "/admin/reset-password/chage-password");
        //     $form.attr("method", "post");
        //     $form.submit();
        // });
    }
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
            if (isDuplicate) {
                twoBtnModal('이미 존재하는 이메일 입니다.');
                $frm.find('#emailCheckYn').val(false);
            } else {
                twoBtnModal('사용가능한 이메일 입니다.');
                $frm.find('#emailCheckYn').val(true);
            }
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    })
}

let checkValidPassword = function() {

    const $frm = $('form[name=frmRegister]');
    let $field = $frm.find('.passwordMsg'),
        password = $frm.find('input[name="password"]').val(),
        repeat = $frm.find('#confirmPassword').val();

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

    const $frm = $('form[name=frmRegister]');
    let params = serializeObject({form:$frm[0]}).json();

    let emailCheckYn = $frm.find("#emailCheckYn").val();
    if (emailCheckYn === "false") {
        twoBtnModal('이메일 중복확인을 해주세요.');
        return;
    }

    let confirmPassword = $('#confirmPassword').val();
    if (!checkNullOrEmptyValue(confirmPassword)) {
        twoBtnModal('비밀번호 확인란에 비밀번호를 입력해주세요.');
        return;
    }

    let pwValidCheckYn = $('#pwValidCheckYn').val();
    if (pwValidCheckYn === "false") {
        twoBtnModal('비밀번호를 올바르게 입력해주세요.');
        return;
    }

    let pwEqualCheckYn = $('#pwEqualCheckYn').val();
    if (pwEqualCheckYn === "false") {
        twoBtnModal('동일한 비밀번호가 아닙니다.');
        return;
    }

    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal('이름을 입력해주세요.');
        return;
    }

    if (!checkNullOrEmptyValue(params.phone)) {
        twoBtnModal('전화번호를 입력해주세요.');
        return;
    }

    if (!checkNullOrEmptyValue(params.teamId)) {
        twoBtnModal('관리자의 팀을 선택해주세요.');
        return;
    }

    let roleNames = [];
    roleNames.push(params.roleName);
    params.roleNames = roleNames;
    if (params.roleNames.length <= 0) {
        twoBtnModal('관리자의 권한을 선택해주세요.');
        return;
    }

    let isPrivacyAgree = $('#privacyAgree').prop('checked');
    if (!isPrivacyAgree) {
        twoBtnModal('개인 정보 수집 및 이용에 동의해주세요.');
        return;
    }

    let isPolicyAgree = $('#policyAgree').prop('checked');
    if (!isPolicyAgree) {
        twoBtnModal('이용약관에 동의해주세요.');
        return;
    }

    console.log("params", params);

    $.ajax({
        url: "/administrators",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let message = "회원가입 요청이 완료되었습니다."
            twoBtnModal(message, function() {
                location.href = '/login';
                // $('#sign-up-modal').find('.close').trigger('click');
                // $('#email-auth-modal').modal('show');
                // $('#email-auth-modal').find('#username').text(params.username);
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let sendEmailValidCode = function(e) {
    e.preventDefault();

}

let login = function(e) {
    e.preventDefault();

    let $frm = $('form[name="loginForm"]'),
        $username = $frm.find('input[name="username"]'),
        $password = $frm.find('input[name="password"]');

    if (!checkNullOrEmptyValue($username.val())) {
        $('.loginCheck').html('<small class="error-message text-small text-danger margin-left-3">로그인하는 계정을 입력해주세요.</small>');
        return;
    } else {
        $('.loginCheck').html('');
    }

    if (!checkNullOrEmptyValue($password.val())) {
        $('.passwordCheck').html('<small class="error-message text-small text-danger margin-left-3">비밀번호를 입력해주세요.</small>');
        return;
    } else {
        $('.passwordCheck').html('');
    }


    $frm.submit();
}

$(document).ready(onReady)
    .on('click', '.btnLogin', login)
    .on('click', '#btnSignUpModal', signUpModal)
    .on('click', '#btnSignUp', signUp)
    .on('click', '#sign-up-modal .btnCheckDuplicate', checkDuplicateData)
    .on('blur', '#confirmPassword', checkValidPassword)
    .on('click', '.btnEmailAuth', sendEmailValidCode);
