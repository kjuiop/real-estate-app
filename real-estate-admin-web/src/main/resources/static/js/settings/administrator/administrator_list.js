let onReady = function() {
};

let search = function(e) {
    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='size']").val($("#limit :selected").val());
    $frm.find("input[name='page']").val(0);
    $frm.submit();
};

let reset = function(e) {

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='page']").val(0);
    $frm.find("input[name='size']").val(10);
    $frm.find("input[name='username']").val('');
    $frm.find("input[name='name']").val('');
    $frm.find("input[name='userStatus']").val('');
    $frm.submit();
};

let changeAdminStatus = function(e) {
    e.preventDefault();

    let params = [];
    let status = $('#statusType').val();

    $("input[name='numbers']:checked").each(function (idx, item) {
        let param = {
            adminId : $(item).val(),
            status : status
        };
        params.push(param);
    });

    if (params.length <= 0) {
        oneBtnModal('상태를 변경할 관리자를 선택해주세요.');
        return;
    }

    twoBtnModal("선택한 관리자의의 상태를 변경하시겠습니까?", function () {
        $.ajax({
            url: "/settings/administrators/status",
            method : "put",
            type: "json",
            contentType : "application/json",
            data: JSON.stringify(params),
            success: function (response) {
                console.log(response);
                if (response.status === "OK") {
                    location.reload();
                }
            },
            error : function (response) {
                oneBtnModal("변경 중 오류가 발생하였습니다.", function(){});
            }
        });
    });
}

let removeAdmin = function(e) {
    e.preventDefault();

    let params = [];

    $("input[name='numbers']:checked").each(function (idx, item) {
        let param = {
            adminId : $(item).val(),
        };
        params.push(param);
    });

    if (params.length <= 0) {
        twoBtnModal('삭제할 관리자를 선택해주세요.');
        return;
    }

    twoBtnModal("선택한 관리자를 삭제하시겠습니까?", function () {
        $.ajax({
            url: "/settings/administrators/remove",
            method : "post",
            type: "json",
            contentType : "application/json",
            data: JSON.stringify(params),
            success: function (response) {
                console.log(response);
                if (response.status === "OK") {
                    location.reload();
                }
            },
            error : function (response) {
                oneBtnModal("변경 중 오류가 발생하였습니다.", function(){});
            }
        });
    });
}

$(document).ready(onReady)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch', search)
    .on('change', '#limit', search)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox)
    .on('click', '.btnChangeStatus', changeAdminStatus)
    .on('click', '.btnRemove', removeAdmin);
