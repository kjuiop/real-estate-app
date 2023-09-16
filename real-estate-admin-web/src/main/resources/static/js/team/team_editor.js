let onReady = function() {

    let $frm = $('form[name="frmTeamRegister"]');
    isModify($frm, 'teamId') ? updateValidate($frm) : createValidate($frm);
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

let save = function() {

    let $frm = $('form[name="frmTeamRegister"]'),
        formMethod = isModify($frm, 'teamId') ? 'put' : 'post',
        param = serializeObject({form:$frm[0]}).json();

    console.log("params", param);

    $.ajax({
        url: "/settings/team",
        method: formMethod,
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (result) {
            console.log("result : ", result);
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

$(document).ready(onReady);