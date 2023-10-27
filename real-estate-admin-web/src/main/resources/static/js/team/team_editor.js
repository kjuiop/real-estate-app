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

let changeStatus = function() {
    let adminId = $(this).parents('tr').find('input[name="adminId"]').val(),
        status = $(this).val();

    let params = [];
    let param = {
        "adminId" : adminId,
        "status" : status,
    }
    params.push(param);

    twoBtnModal("상태를 변경하시겠습니까?", function() {
        $.ajax({
            url: "/settings/administrators/status",
            method : "put",
            type: "json",
            contentType : "application/json",
            data: JSON.stringify(params),
            success: function (response) {
                console.log(response);
                if (response.status === "OK") {
                    twoBtnModal("상태 변경이 완료되었습니다.", function () {
                        location.reload();
                    });
                }
            },
            error : function (response) {
                oneBtnModal("변경 중 오류가 발생하였습니다.", function(){});
            }
        });
    });
}

$(document).ready(onReady)
    .on('change', 'select[name="status"]', changeStatus)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox);