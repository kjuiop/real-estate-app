let onReady = function() {
    let address = '서울특별시 강남구 도산대로 149';
    if (checkNullOrEmptyValue(condition.address)) {
        address = condition.address;
    }
    loadKakaoMap(address);
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

            setSearchAddress();
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let setSearchAddress = function() {
    let $frm = $('form[name="frmSearch"]'),
        $address = $frm.find('input[name="address"]'),
        sido = $frm.find('select[name="sido"] option:selected').attr('name'),
        gungu = $frm.find('select[name="gungu"] option:selected').attr('name'),
        dong = $frm.find('select[name="dong"] option:selected').attr('name'),
        address = "";

    if (!checkNullOrEmptyValue(sido)) {
        $address.val(address);
        return;
    }

    address = sido;

    if (!checkNullOrEmptyValue(gungu)) {
        $address.val(address);
        return;
    }

    address = sido + " " + gungu;

    if (!checkNullOrEmptyValue(dong)) {
        $address.val(address);
        return;
    }

    address = sido + " " + gungu + " " + dong;
    $address.val(address);
}

let drawAreaOption = function(depth, areaList) {
    let tag = '';
    if (depth === 'gungu') {
        tag += '<option value="">구군 선택</option>';
    } else if (depth === 'dong') {
        tag += '<option value="">동 선택</option>';
    }
    $.each(areaList, function(idx, item) {
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '" name="' +  item.name + '">' + item.name + '</option>';
    });
    return tag;
}

$(document).ready(onReady)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData)
    .on('change', 'select[name="dong"]', setSearchAddress)
    .on('click', '.real-estate-unit', moveMapFocus);