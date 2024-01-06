let loadCustomerInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    if (dto.existCustomerInfo !== true) {
        return;
    }

    $.ajax({
        url: "/real-estate/customer/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("customer result", result);
            let customerInfoList = result.data;

            let tag = '';
            $.each(customerInfoList, function(idx, item) {
                tag += drawUnitCustomerInfo(item.type, item);
            });
            $('#customerInfoSection').html(tag);
            setCustomerCnt();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}


let changeCustomerInfo = function(e) {
    e.preventDefault();

    let $this = $(this),
        type = $this.attr("type"),
        $unit = $this.parents('.customerInfoUnit');

    $unit.find('.toggleCustomer').removeClass('text-blue');
    $this.addClass('text-blue');

    if (type === 'customer') {
        $unit.html(drawCustomerInfo(null));
    } else {
        $unit.html(drawCompanyInfo(null));
    }

}

let drawUnitCustomerInfo = function(type, item) {
    let tag = '';
    tag += '<div class="customerInfoUnit">';

    if (type === 'CUSTOMER') {
        tag += drawCustomerInfo(item);
    } else {
        tag += drawCompanyInfo(item);
    }
    tag += '</div>';
    return tag;
}

let addCustomerInfo = function(e) {
    e.preventDefault();

    $('#customerInfoSection').append(drawUnitCustomerInfo("CUSTOMER", null));
    setCustomerCnt();
}

let removeCustomerInfo = function(e) {
    e.preventDefault();
    $(this).parents('.customerInfoUnit').remove();
    setCustomerCnt();
}

let setCustomerCnt = function() {
    let customerLength = $('#customerInfoSection .customerInfoUnit').length;
    $('#customerCnt').html(customerLength);
}

let drawCustomerInfo = function(item) {

    if (!checkNullOrEmptyValue(item)) {
        item = {
            "type": "",
            "customerName": "",
            "gender": "",
            "birth": "",
            "phone": "",
            "etcPhone": "",
            "etcInfo": "",
        }
    }

    let tag = '';
    tag +=     '<div class="row display-flex-row margin-bottom-5">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<div class="display-flex-row">';
    tag +=                 '<div class="col-md-6 no-left-padding">';
    tag +=                     '<label class="text-label">고객명</label>';
    tag +=                 '</div>';
    tag +=                 '<div class="col-md-6">';
    tag +=                     '<label class="text-label pull-right">';
    tag +=                      '<input type="hidden" class="form-control form-control-sm" name="type" value="CUSTOMER" />';
    tag +=                         '<span class="toggleCustomer text-blue button-pointer" type="customer">개인</span>  | ';
    tag +=                         '<span class="toggleCustomer button-pointer" type="company">법인</span>' ;
    tag +=                     '</label>';
    tag +=                 '</div>';
    tag +=             '</div>';
    if (!checkNullOrEmptyValue(dto.realEstateId) || dto.ownUser) {
        tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="customerName" value="' +  item.customerName + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm input-height-36" name="customerName" value="**********"/>';
    }
    tag +=         '</div>';
    tag +=         '<div class="col-md-3">';
    tag +=             '<label class="text-label">성별</label>';
    tag +=             '<select class="form-control form-control-sm valid-ignore custom-select" name="gender">';
    if (item.gender === "MAN") {
        tag +=                 '<option value="MAN" selected>남</option>';
    } else {
        tag +=                 '<option value="MAN">남</option>';
    }
    if (item.gender === "WOMAN") {
        tag +=                 '<option value="WOMAN" selected>여</option>';
    } else {
        tag +=                 '<option value="WOMAN">여</option>';
    }
    tag +=             '</select>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-3">';
    tag +=         '<div>';
    tag +=             '<label class="text-label">생년월일</label>';
    tag +=             '<button type="button" class="btn btn-xs btn-default btnRemoveCustomerInfo pull-right margin-bottom-5">삭제</button>'
    tag +=         '</div>'
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="birth" value="' +  item.birth + '"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    if (dto.ownUser || !checkNullOrEmptyValue(dto.realEstateId)) {
        tag +=             '<input type="text" class="form-control form-control-sm" name="phone" value="' +  item.phone + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm" name="phone" value="**********"/>';
    }

    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">기타전화</label>';
    if (dto.ownUser || !checkNullOrEmptyValue(dto.realEstateId)) {
        tag +=             '<input type="text" class="form-control form-control-sm" name="etcPhone" value="' +  item.etcPhone + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm" name="etcPhone" value="**********"/>';
    }

    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line" style="padding-right: 0;">';
    tag +=         '<input type="text" class="form-control form-control-sm" name="etcInfo" value="' +  item.etcInfo + '" placeholder="비고"/>';
    tag +=     '</div>';

    return tag;
}

let drawCompanyInfo = function(item) {

    if (!checkNullOrEmptyValue(item)) {
        item = {
            "type": "",
            "companyName": "",
            "representName": "",
            "companyPhone": "",
            "representPhone": "",
            "etcInfo": "",
        }
    }

    let tag = '';
    tag +=     '<div class="row display-flex-row margin-bottom-5 test">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<div class="display-flex-row">';
    tag +=                 '<div class="col-md-6 no-left-padding">';
    tag +=                     '<label class="text-label">법인명</label>';
    tag +=                 '</div>';
    tag +=                 '<div class="col-md-6">';
    tag +=                     '<label class="text-label pull-right">';
    tag +=                      '<input type="hidden" class="form-control form-control-sm" name="type" value="COMPANY" />';
    tag +=                         '<span class="toggleCustomer button-pointer" type="customer">개인</span>  | ';
    tag +=                         '<span class="toggleCustomer button-pointer text-blue" type="company">법인</span>' ;
    tag +=                     '</label>';
    tag +=                 '</div>';
    tag +=             '</div>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="companyName" value="' +  item.companyName + '"/>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=         '<div>';
    tag +=             '<label class="text-label">대표자명</label>';
    tag +=             '<button type="button" class="btn btn-xs btn-default btnRemoveCustomerInfo pull-right">삭제</button>'
    tag +=         '</div>'
    if (dto.ownUser || !checkNullOrEmptyValue(dto.realEstateId)) {
        tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="representName" value="' +  item.representName + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm input-height-36" name="representName" value="**********"/>';
    }

    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">법인전화</label>';
    if (dto.ownUser || !checkNullOrEmptyValue(dto.realEstateId)) {
        tag +=             '<input type="text" class="form-control form-control-sm" name="companyPhone" value="' +  item.companyPhone + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm" name="companyPhone" value="**********"/>';
    }


    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    if (dto.ownUser || !checkNullOrEmptyValue(dto.realEstateId)) {
        tag +=             '<input type="text" class="form-control form-control-sm" name="representPhone" value="' +  item.representPhone + '"/>';
    } else {
        tag +=             '<input type="password" class="form-control form-control-sm" name="representPhone" value="**********"/>';
    }

    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line" style="padding-right: 0;">';
    tag +=         '<input type="text" class="form-control form-control-sm" name="etcInfo" value="' +  item.etcInfo + '" placeholder="비고"/>';
    tag +=     '</div>';

    return tag;
}

let assembleCustomerParam = function() {
    let $frm = $('form[name="frmCustomerRegister"]');

    let customerInfo = [];
    $frm.find('.customerInfoUnit').each(function (idx, item) {
        let type = $(this).find('input[name="type"]').val();
        let param;
        if (type === 'CUSTOMER') {
            param = {
                "type" : type,
                "customerName" : $(item).find('input[name="customerName"]').val(),
                "gender" : $(item).find('select[name="gender"]').val(),
                "birth" : $(item).find('input[name="birth"]').val(),
                "phone" : $(item).find('input[name="phone"]').val(),
                "etcPhone" : $(item).find('input[name="etcPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        } else if (type === 'COMPANY') {
            param = {
                "type" : type,
                "companyName" : $(item).find('input[name="companyName"]').val(),
                "representName" : $(item).find('input[name="representName"]').val(),
                "companyPhone" : $(item).find('input[name="companyPhone"]').val(),
                "representPhone" : $(item).find('input[name="representPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        }

        customerInfo.push(param);
    });

    return customerInfo;
}

let addCustomerSave = function(e) {
    e.preventDefault();

    let isModify = dto.existCustomerInfo;
    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let customerInfo = [];
    let $frm = $('form[name="frmCustomerRegister"]');
    $frm.find('.customerInfoUnit').each(function (idx, item) {
        let type = $(this).find('input[name="type"]').val();
        let param;
        if (type === 'CUSTOMER') {
            param = {
                "type" : type,
                "customerName" : $(item).find('input[name="customerName"]').val(),
                "gender" : $(item).find('select[name="gender"]').val(),
                "birth" : $(item).find('input[name="birth"]').val(),
                "phone" : $(item).find('input[name="phone"]').val(),
                "etcPhone" : $(item).find('input[name="etcPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        } else if (type === 'COMPANY') {
            param = {
                "type" : type,
                "companyName" : $(item).find('input[name="companyName"]').val(),
                "representName" : $(item).find('input[name="representName"]').val(),
                "companyPhone" : $(item).find('input[name="companyPhone"]').val(),
                "representPhone" : $(item).find('input[name="representPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        }

        customerInfo.push(param);
    });

    let params = {
        "customerInfo": customerInfo,
    }
    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;
    params.realEstateId = dto.realEstateId;

    console.log("params", params);

    $.ajax({
        url: "/real-estate/customer",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let message = '정상적으로 저장되었습니다.';
            if (isModify) {
                message = '정상적으로 수정되었습니다.';
            }
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}
