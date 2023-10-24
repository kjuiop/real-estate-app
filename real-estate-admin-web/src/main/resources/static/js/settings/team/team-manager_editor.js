let onReady = function() {
    let $frm = $('form[name="frmTeamRegister"]');
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

    $frm.validate({
        debug : true,
        ignore : '.valid-ignore, *:not([name])',
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: '팀 이름을 입력해주세요.'
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

    $frm.validate({
        debug : true,
        ignore : '.valid-ignore, *:not([name])',
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: '팀 이름을 입력해주세요.'
            }
        },
        submitHandler: function() {
            save();
        }
    });
}

let save = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmTeamRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    console.log("params", params);

    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal("팀 이름을 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/settings/team-manager",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            twoBtnModal('정상적으로 저장되었습니다.', function() {
                location.href = '/settings/team-manager/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let update = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmTeamRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    console.log("params", params);

    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal("팀 이름을 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/settings/team-manager",
        method: "put",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            twoBtnModal('정상적으로 수정되었습니다.', function() {
                location.href = '/settings/team-manager/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

$(document).ready(onReady)
    .on('click', '.btnTeamSave', save)
    .on('click', '.btnTeamUpdate', update)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox);