let onReady = function() {
    console.log("processDto", processDto);
    console.log("usageCds", usageCds);

    if (checkNullOrEmptyValue(processDto)) {
        setConvertDoubleToInt();
        setUsageTypeCd(processDto.usageTypeCds);
    }

};

let setUsageTypeCd = function(data) {

    if (!checkNullOrEmptyValue(data)) {
        return
    }

    $('.usageTypeSection').html('');

    let ids = data.split(",");
    $.each(usageCds, function(i, code) {
        $.each(ids, function(j, id) {
            if (code.id == id) {
                let tag = drawUsageTypeButton(code.id, code.name);
                $('.usageTypeSection').append(tag);
            }
        });
    });

    $('.usageTr').removeClass('hidden');
}

let setConvertDoubleToInt = function() {
    let minSalePrice = processDto.minSalePrice,
        maxSalePrice = processDto.maxSalePrice,
        handCache = processDto.handCache,
        exclusiveAreaPy = processDto.exclusiveAreaPy
    ;

    let $frm = $('form[name="frmRegister"]');
    $frm.find('input[name="minSalePrice"]').val(convertDoubleValue(minSalePrice));
    $frm.find('input[name="maxSalePrice"]').val(convertDoubleValue(maxSalePrice));
    $frm.find('input[name="handCache"]').val(convertDoubleValue(handCache));
    $frm.find('input[name="exclusiveAreaPy"]').val(convertDoubleValue(exclusiveAreaPy));
}

let convertDoubleValue = function(doubleValue) {
    if (doubleValue === 0 || doubleValue % 1 > 0) {
        return doubleValue
    }

    return parseInt(doubleValue);
}

let addUsageType = function(e) {
    e.preventDefault();

    let id = $(this).val(),
        name = $(this).find('option:selected').attr('name');

    if (!checkNullOrEmptyValue(id)) {
        return;
    }

    if (checkDuplicateUsageType(id)) {
        twoBtnModal("이미 등록된 매입목적입니다.");
        return;
    }

    let tag = drawUsageTypeButton(id, name);
    $('.usageTypeSection').append(tag);
    $('.usageTr').removeClass('hidden');
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
    let usageTypeCds = "";
    let $section = $('.usageTypeSection').find('.btnUsageCode');
    $section.toArray().some(function(item, index, array) {
        usageTypeCds += $(item).attr('usageTypeId');
        if (index < array.length - 1) {
            usageTypeCds += ",";
        }
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
    params['sortOrder'] = $('select[name="processCd"] option:selected').attr('sortOrder');

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
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    location.href = '/buyer/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let update = function(e) {
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
    params['sortOrder'] = $('select[name="processCd"] option:selected').attr('sortOrder');

    console.log("params", params);

    twoBtnModal("수정하시겠습니까?", function () {
        $.ajax({
            url: "/buyer",
            method: 'put',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 수정되었습니다.', function() {
                    location.href = '/buyer/' + result.data + '/edit';
                });
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
    console.log("fakeYn", fakeYn);
    if (fakeYn === 'Y') {
        $('input[name="adManager"]').prop('readonly', false);
    } else {
        $('input[name="adManager"]').prop('readonly', true);
    }
}

let changeBtn = function(e) {
    e.preventDefault();

    let code = $(this).find('option:selected').attr('code');
    let isExist = false;
    $.each(dto.processList, function(idx, item) {
        console.log("code : " + code + " item : ", item.processCd);
        if (item.processCd === code) {
            isExist = true;
        }
    });

    console.log("isExist", isExist)

    if (isExist) {
        $('.btnSave').addClass('hidden');
        $('.btnUpdate').removeClass('hidden');
    } else {
        $('.btnUpdate').addClass('hidden');
        $('.btnSave').removeClass('hidden');
    }
}

let selectDetail = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        buyerId = $frm.find('input[name="buyerId"]').val(),
        processCd = $(this).val();

    console.log("buyer id : " + buyerId + " processCd " + processCd);

    $.ajax({
        url: "/buyer/" + buyerId + "/" + processCd,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function (result) {
            console.log("result : ", result);
            let data = result.data;
            if (!checkNullOrEmptyValue(data)) {
                return;
            }
            $frm.find('input[name="title"]').val(data.title);
            $frm.find('input[name="inflowPath"]').val(data.inflowPath);
            if (data.fakeYn === 'Y') {
                $frm.find("#fakeYn_Y").iCheck('check');
            } else {
                $frm.find("#fakeYn_N").iCheck('check');
            }
            $frm.find('input[name="adAddress"]').val(data.adAddress);
            $frm.find('input[name="adManager"]').val(data.adManager);
            $frm.find('input[name="minSalePrice"]').val(data.minSalePrice);
            $frm.find('input[name="maxSalePrice"]').val(data.maxSalePrice);
            $frm.find('input[name="handCache"]').val(data.handCache);
            $frm.find('input[name="customerSector"]').val(data.customerSector);
            $frm.find('input[name="customerPosition"]').val(data.customerPosition);
            $frm.find('input[name="customerName"]').val(data.customerName);
            $frm.find('select[name="investmentCharacterCd"]').val(data.investmentCharacterCd);
            $frm.find('input[name="purchasePoint"]').val(data.purchasePoint);
            $frm.find('input[name="preferArea"]').val(data.preferArea);
            $frm.find('input[name="preferSubway"]').val(data.preferSubway);
            $frm.find('input[name="preferRoad"]').val(data.preferRoad);
            $frm.find('input[name="exclusiveAreaPy"]').val(data.exclusiveAreaPy);
            $frm.find('input[name="moveYear"]').val(data.moveYear);
            $frm.find('input[name="moveMonth"]').val(data.moveMonth);
            if (data.companyEstablishAtYn === 'Y') {
                $frm.find("#companyEstablishAt_within").iCheck('check');
            } else {
                $frm.find("#companyEstablishAt_after").iCheck('check');
            }
            $frm.find('input[name="name"]').val(data.name);
            $frm.find('input[name="deliveryWay"]').val(data.deliveryWay);
            $frm.find('input[name="nextPromise"]').val(data.nextPromise);
            $frm.find('textarea[name="requestDetail"]').text(data.requestDetail);
            setUsageTypeCd(data.usageTypeCds);
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

$(document).ready(onReady)
    .on('ifToggled', 'input[name="fakeYn"]', setFakeReadOnly)
    .on('click', '.btnSave', save)
    .on('click', '.btnUpdate', update)
    .on('change', '#usageType', addUsageType)
    .on('change', 'select[name="processCd"]', selectDetail)
;