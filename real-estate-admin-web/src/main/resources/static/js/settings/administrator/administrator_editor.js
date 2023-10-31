let onReady = function() {

    console.log("dto", dto);
    loadRole();
    onlyNumberKeyEvent({className: "only-number"});
}

let createValidate = function($frm) {

    $.validator.setDefaults({
        onkeyup:false,
        onclick:false,
        onfocusout:false,
        showErrors: function(errorMap, errorList) {
            if (errorList.length) {
                jQueryErrorField(errorList);
            }
        }
    });

    $.validator.addMethod("isEmailDuplicateCheck", function(value, element){
        let isDuplication = $('#emailCheckYn').val();

        console.log("duplication", isDuplication);
        return isDuplication;
    });

    $.validator.addMethod("isPwValidCheckYn", function(value, element){
        let isValid = $('#pwValidCheckYn').val();

        console.log("isValid", isValid);
        return isValid;
    });

    $.validator.addMethod("isPwEqualCheckYn", function(value, element){
        let isValid = $('#pwEqualCheckYn').val();

        console.log("isValid", isValid);
        return isValid;
    });

    $frm.validate({
        debug : true,
        ignore : '.valid-ignore, *:not([name])',
        rules: {
            username: {
                required: true,
                isEmailDuplicateCheck: false
            },
            name: {
                required: true
            },
            password: {
                required: true,
            },
            confirmPassword: {
                required: true,
            }
        },
        messages: {
            username: {
                required: '이메일을 입력해주세요.',
                isEmailDuplicateCheck: '이미 사용중인 이메일입니다.'
            },
            name: {
                required: '이름을 입력해주세요.'
            },
            password: {
                required: '비밀번호를 입력해주세요.',
            },
            confirmPassword: {
                required: '비밀번호를 입력해주세요.',
            }
        },
        submitHandler: function() {
            save();
        }
    });
}

let updateValidate = function($frm) {

    $.validator.setDefaults({
        onkeyup:false,
        onclick:false,
        onfocusout:false,
        showErrors: function(errorMap, errorList) {
            if (errorList.length) {
                jQueryErrorField(errorList);
            }
        }
    });

    $.validator.addMethod("isEmailDuplicateCheck", function(value, element){
        let isDuplication = $('#emailCheckYn').val();

        console.log("duplication", isDuplication);
        return isDuplication;
    });

    $frm.validate({
        debug : true,
        ignore : '.valid-ignore, *:not([name])',
        rules: {
            username: {
                required: true,
                isEmailDuplicateCheck: false
            },
            name: {
                required: true
            }
        },
        messages: {
            username: {
                required: '이메일을 입력해주세요.',
                isEmailDuplicateCheck: '이미 사용중인 이메일입니다.'
            },
            name: {
                required: '이름을 입력해주세요.'
            }
        },
        submitHandler: function() {
            save();
        }
    });
}

let createTeam = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmTeamRegister"]');


}

let loadRole = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    if (dto) {
        $.each(dto.roles, function (idx, role) {
            $('.excludeData option[value="' + role + '"]').hide();
            $('.includeData option[value="' + role + '"]').show();
        });
    }
}

let addData = function(e) {
    e.preventDefault();

    let $section = $(this).parents(`.roleSection`),
        role = $section.find('.excludeData option:checked').val();

    $section.find('.excludeData option[value="' + role + '"]').hide();
    $section.find('.includeData option[value="' + role + '"]').show();
}

let removeData = function(e) {
    e.preventDefault();

    let $section = $(this).parents(`.roleSection`),
        role = $section.find('.includeData option:checked').val();

    $section.find('.excludeData option[value="' + role + '"]').show();
    $section.find('.includeData option[value="' + role + '"]').hide();
}

let save = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmAdminRegister"]'),
    params = serializeObject({form:$frm[0]}).json();
    params['roleNames'] = getRoleNames();
    console.log("params", params);

    let isDuplication = $('#emailCheckYn').val();
    if (!isDuplication) {
        return;
    }

    let isValidPassword = $('#pwValidCheckYn').val();
    if (!isValidPassword) {
        return;
    }

    let isEqualPassword = $('#pwEqualCheckYn').val();
    if (!isEqualPassword) {
        return;
    }

    if (!checkNullOrEmptyValue(params.username)) {
        twoBtnModal("이메일을 입력해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal("이름을 입력해주세요.");
        return;
    }

    if (params.roleNames.length === 0) {
        twoBtnModal("관리자의 권한을 선택해주세요.");
        return;
    }

    if (params.roleNames.length > 1) {
        twoBtnModal("관리자는 최대 1개의 권한만 선택 가능합니다.");
        return;
    }



    $.ajax({
        url: "/settings/administrators",
        method: 'post',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            twoBtnModal('정상적으로 수정되었습니다.', function() {
                location.href = '/settings/administrators/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let update = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmAdminRegister"]'),
        params = serializeObject({form:$frm[0]}).json();
    params['roleNames'] = getRoleNames();
    console.log("params", params);

    if (params.roleNames.length === 0) {
        twoBtnModal("관리자의 권한을 선택해주세요.");
        return;
    }

    if (params.roleNames.length > 1) {
        twoBtnModal("관리자는 최대 1개의 권한만 선택 가능합니다.");
        return;
    }



    $.ajax({
        url: "/settings/administrators",
        method: 'put',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            twoBtnModal('정상적으로 수정되었습니다.', function() {
                location.href = '/settings/administrators/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let checkDuplicateData = function(e) {
    e.preventDefault();

    if (dto.adminId != null) {
        return false;
    }

    let value = $(this).val(),
        $field = $('.checkEmailSection');

    if (!checkEmailValidCheck(value)) {
        return;
    }

    if (checkNullOrEmptyValue(value)) {
        $.ajax({
            url: "/settings/administrators/check-duplicate/username/" + value,
            method: "get",
            type: "json",
            contentType: "application/json",
            success: function(result) {

                let isDuplicate = result.data;

                console.log("isDuplicate", isDuplicate);

                $field.siblings('.error-message').remove();
                if (isDuplicate) {
                    drawErrorMessage($field, '이미 존재하는 이메일 입니다.');
                    $('#emailCheckYn').val(false);
                } else {
                    drawSuccessMessage($field, '사용가능한 이메일 입니다.');
                    $('#emailCheckYn').val(true);
                }
            },
            error: function(error){
                ajaxErrorFieldByText(error);
            }
        })
    }
};

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

let getRoleNames = function() {
    let roleNames = [];

    if ($('.includeData').length > 0) {
        let isEmptyRole = true;

        $('.includeData option').filter(function () {
            return $(this).css('display') === 'block';
        }).each(function (idx, role) {
            roleNames.push($(role).val());
            isEmptyRole = false;
        });

    }

    return roleNames;
}

$(document).ready(onReady)
    .on('blur', 'input[name=username]', checkDuplicateData)
    .on('click', '.btnIncludeData', addData)
    .on('click', '.btnExcludeData', removeData)
    .on('blur', 'input[name="confirmPassword"]', checkValidPassword)
    .on('click', '.btnSave', save)
    .on('click', '.btnUpdate', update);
