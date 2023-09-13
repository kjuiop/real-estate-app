let onReady = function() {
    initICheck();
}

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
    .on('click', '#btnSignUpSubmit', signUp);
