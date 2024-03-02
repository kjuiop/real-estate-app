let onReady = function() {
};

let save = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.templateCd)) {
        twoBtnModal("템플릿 아이디를 입력해주세요.");
        return;
    }

    console.log("params", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/settings/alarm-template",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let update = function(e) {
    e.preventDefault();
}

$(document).ready(onReady)
    .on('click', '.btnSave', save)
    .on('click', '.btnUpdate', update)
;