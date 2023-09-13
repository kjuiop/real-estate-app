let onReady = function() {
    initICheck();
}

let signUpModal = function(e) {
    e.preventDefault();

    $('#sign-up-modal').modal('show');
}

const initICheck = function() {
    $('input[type="checkbox"], input[type="radio"]').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_flat-blue'
    });
};

$(document).ready(onReady)
    .on('click', '#btnSignUpModal', signUpModal)
