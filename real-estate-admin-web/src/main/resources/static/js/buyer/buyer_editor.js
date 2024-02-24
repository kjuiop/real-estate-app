let onReady = function() {

};

let addUsageType = function(e) {
    e.preventDefault();

    let id = $(this).val(),
        name = $(this).find('option:selected').attr('name');

    if (checkDuplicateUsageType(id)) {
        twoBtnModal("이미 등록된 매입목적입니다.");
        return;
    }

    let tag = drawUsageTypeButton(id, name);
    $('.usageTypeSection').append(tag);
}

let checkDuplicateUsageType = function(id) {
    let $section = $('.usageTypeSection').find('.btnUsageCode');
    return $section.toArray().some(function (item) {
        let usageTypeId = $(item).attr('usageTypeId');
        return id === usageTypeId;
    });
}

let drawUsageTypeButton = function(id, name) {
    let tags = '';
    tags += '<button type="button" class="btn btn-xs btn-primary btnUsageCode selected margin-top-8" usageTypeId="' + id + '" name="usageTypeId" style="margin-right: 5px;"> ' + name + '</button>';
    return tags
}

let getUsageTypeCds = function() {
    let usageTypeCds = [];
    let $section = $('.usageTypeSection').find('.btnUsageCode');
    $section.toArray().some(function(item) {
        usageTypeCds.push($(item).attr('usageTypeId'));
    });

    return usageTypeCds;
}

let save = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
        return;
    }

    let usageTypeCds = getUsageTypeCds();
    if (usageTypeCds.length === 0) {
        twoBtnModal("매입목적을 선택해주세요.");
        return;
    }
    params['usageTypeCds'] = usageTypeCds;

    console.log("params", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                // twoBtnModal('정상적으로 저장되었습니다.', function() {
                //     location.href = '/real-estate/' + result.data + '/edit';
                // });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let setFakeReadOnly = function(e) {
    e.preventDefault();

    let fakeYn = $(this).val();
    if (fakeYn === 'Y') {
        $('input[name="adManager"]').prop('readonly', false);
    } else {
        $('input[name="adManager"]').prop('readonly', true);
    }
}

$(document).ready(onReady)
    .on('ifToggled', 'input[name="fakeYn"]', setFakeReadOnly)
    .on('click', '.btnSave', save)
    .on('change', '#usageType', addUsageType);