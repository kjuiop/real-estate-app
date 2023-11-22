let onReady = function() {
    loadKakaoMap('서울특별시 강남구 도산대로 149');
}

let getChildAreaData = function() {
    let areaId = $(this).find('option:selected').attr('areaId'),
        name = $(this).attr('name');

    if (!checkNullOrEmptyValue(areaId)) {
        oneBtnModal("시/도, 군/구를 선택해주세요.");
        return;
    }

    $.ajax({
        url: "/settings/area-manager/" + areaId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function (result) {
            console.log("area result : ", result);
            let areaList = result.data;
            let $frm = $('form[name="frmSearch"]'),
                $gungu = $frm.find('select[name="gungu"]'),
                $dong = $frm.find('select[name="dong"');
            let tag;
            if (name === 'sido') {
                tag = drawAreaOption("gungu", areaList);
                $gungu.html(tag);
                $dong.html('<option>동 선택</option>');
                $frm.find('select[name="landType"]').val('general');
                $frm.find('input[name="bun"]').val('');
                $frm.find('input[name="ji"]').val('');
            } else if (name === 'gungu') {
                tag = drawAreaOption("dong", areaList);
                $dong.html(tag);
                $frm.find('select[name="landType"]').val('general');
                $frm.find('input[name="bun"]').val('');
                $frm.find('input[name="ji"]').val('');
            }
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let drawAreaOption = function(depth, areaList) {
    let tag = '';
    if (depth === 'gungu') {
        tag += '<option value="">구군 선택</option>';
    } else if (depth === 'dong') {
        tag += '<option value="">동 선택</option>';
    }
    $.each(areaList, function(idx, item) {
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '">' + item.name + '</option>';
    });
    return tag;
}

$(document).ready(onReady)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData);