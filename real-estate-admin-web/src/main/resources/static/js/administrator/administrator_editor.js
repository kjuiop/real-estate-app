let onReady = function() {

    console.log("dto", dto);
    onlyNumberKeyEvent({className: "only-number"});
}

let update = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmAdminRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    console.log("params", params);

    if (!checkNullOrEmptyValue(params.phone)) {
        twoBtnModal("전화번호를 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/mypage",
        method: 'put',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            twoBtnModal('정상적으로 수정되었습니다.', function() {
                location.href = '/mypage';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

const checkValidPassword = function() {

    const $frm = $('form[name=frmAdminRegister]');
    let $password = $frm.find('.checkPasswordSection'),
        $confirm = $frm.find('.checkConfirmPasswordSection'),
        password = $frm.find('input[name="password"]').val(),
        repeat = $frm.find('input[name="confirmPassword"]').val();


    console.log("password : ", password, " confirm : ", repeat);


    if (!checkNullOrEmptyValue(password)) {
        return false;
    }

    if (password.length < 6) {
        $('#pwValidCheckYn').val(false);
        drawErrorMessage($password, '6자리 이상의 미빌번호를 입력해주세요.');
        $confirm.html('');
        $('#pwEqualCheckYn').val(false);
        return false;
    } else {
        $('#pwValidCheckYn').val(true);
    }

    if (password !== repeat) {
        $('#pwEqualCheckYn').val(false);
        drawErrorMessage($confirm, '동일한 비밀번호가 아닙니다.');
        return false;
    }

    $('#pwEqualCheckYn').val(true);
    drawSuccessMessage($confirm, '비밀번호가 동일합니다.');
    $password.html('');

};

let sendSlackAuth = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmAdminRegister"]');

    let params = {
        "username" : $frm.find('#username').val()
    }

    console.log("params", params);

    $.ajax({
        url: "/mypage/slack-auth",
        method: 'post',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let adminId = result.data;
            if (!checkNullOrEmptyValue(adminId)) {
                twoBtnModal("slack 에 가입된 계정인지 확인해주세요.");
                return;
            }

            $('.authSection').removeClass('hidden');
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let checkSlackAuth = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmAdminRegister"]');

    let params = {
        "username" : $frm.find('#username').val(),
        "authCode" : $frm.find('.authCode').val()
    }

    if (!checkNullOrEmptyValue(params.authCode)) {
        twoBtnModal("인증번호를 입력해주세요.");
        return;
    }

    console.log("params", params);

    $.ajax({
        url: "/mypage/slack-auth/check",
        method: 'post',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let isValid = result.data;
            if (!isValid) {
                twoBtnModal("인증번호가 유효하지 않습니다. 다시 요청해주세요.")
                $('.authSection').removeClass('hidden');
                return;
            }
            twoBtnModal("slack 계정에 연동되었습니다.", function() {
                location.reload();
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

$(document).ready(onReady)
    .on('blur', 'input[name="confirmPassword"]', checkValidPassword)
    .on('click', '.btnUpdate', update)
    .on('click', '.btnSendSlackAuth', sendSlackAuth)
    .on('click', '.btnCheckSlackAuth', checkSlackAuth);
