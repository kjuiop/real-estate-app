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

let saveTeamMember = function(e) {
    e.preventDefault();

    let $checkedElements = $('input[name=numbers]:checked');

    let managerCnt = 0;
    let adminIds = [];
    let params = [];
    $checkedElements.each(function(idx, item) {
        let $unit = $(item).parents('.admin-unit');
        let role = $unit.find('select[name="role"] option:selected').val();
        let teamId = dto.teamId;
        let adminId = $(item).val();

        let param = {
            "adminId" : adminId,
            "status" : $unit.find('select[name="status"] option:selected').val(),
            "role" : role,
        }

        console.log("saveTeamMember param : ", param);
        console.log("dto.teamId : ", dto.teamId);

        if (role === 'ROLE_MANAGER') {
            managerCnt++;
        }

        params.push(param);
    });

    if (params.length === 0) {
        twoBtnModal("정보를 변경하려는 관리자를 선택해주세요.");
        return;
    }

    if (managerCnt > 2) {
        twoBtnModal("하나의 팀에는 팀장이 둘 이상이 될 수는 없습니다.");
        return;
    }

    console.log("params : ", params);

    twoBtnModal("선택하신 관리자를 팀으로 이동시키겠습니까?", function () {
        $.ajax({
            url: "/settings/administrators/team/" + dto.teamId,
            method: 'put',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 변경되었습니다.', function() {
                    location.href = '/settings/team-manager/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });


}

$(document).ready(onReady)
    .on('click', '.btnTeamSave', save)
    .on('click', '.btnTeamUpdate', update)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox)
    .on('click', '.btnMemberSave', saveTeamMember);