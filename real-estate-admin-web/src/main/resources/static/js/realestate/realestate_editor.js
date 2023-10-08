let onReady = function() {
    console.log("dto", dto);
    loadBasicInfo();
    loadLandInfoList();
    loadPriceInfo();
    loadConstructInfo();
    loadConstructFloorInfo();
    loadCustomerInfo();
    loadMemoInfo();
    loadLandInfoList();
    $('#customerInfoSection').html(drawUnitCustomerInfo("CUSTOMER", null));
    onlyNumberKeyEvent({className: "only-number"});
}

let loadBasicInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    loadKakaoMap(dto.address);
    loadImg(dto.imgUrl);

    let $frm = $('form[name="frmBasicRegister"]'),
        usageCodeId = $frm.find('.usageCode').val();

    $.ajax({
        url: "/settings/category-manager/children-categories?parentId=" + usageCodeId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let categories = result.data;

            let tags = drawBtnUsageCode(categories);
            $frm.find('.usageCdsSection').html(tags);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let loadImg = function(imgUrl) {
    if (!checkNullOrEmptyValue(imgUrl)) {
        return;
    }
    let $imagePanel = $('.image-section');
    let tag = imgDraw(imgUrl);
    $imagePanel.html(tag);
}


let loadPriceInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    if (dto.existPriceInfo !== true) {
        return;
    }

    $.ajax({
        url: "/real-estate/price/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let priceList = result.data,
                priceInfo = priceList[0];
            let $frm = $('form[name="frmPriceRegister"]');
            $frm.find('.salePrice').val(priceInfo.salePrice);
            $frm.find('.depositPrice').val(priceInfo.depositPrice);
            $frm.find('.revenueRate').val(priceInfo.revenueRate);
            $frm.find('.averageUnitPrice').val(priceInfo.averageUnitPrice);
            $frm.find('.guaranteePrice').val(priceInfo.guaranteePrice);
            $frm.find('.rentMonth').val(priceInfo.rentMonth);
            $frm.find('.management').val(priceInfo.management);
            $frm.find('.managementExpense').val(priceInfo.managementExpense);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadConstructInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;

    if (dto.existConstructInfo === true) {
        url = "/real-estate/construct/" + dto.realEstateId;
    } else {
        url = "/real-estate/construct/ajax/public-data"
            + "?legalCode=" + dto.legalCode
            + "&landType=" + dto.landType
            + "&bun=" + dto.bun
            + "&ji=" + dto.ji
    }

    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let constructInfo = result.data;
            console.log("constructInfo", constructInfo);

            let $frm = $('form[name="frmConstructRegister"]');
            $frm.find('.mainPurpsCdNm').val(constructInfo.mainPurpsCdNm);
            $frm.find('.etcPurps').val(constructInfo.etcPurps);
            $frm.find('.strctCdNm').val(constructInfo.strctCdNm);
            $frm.find('.useAprDay').val(constructInfo.useAprDay);
            $frm.find('.platArea').val(constructInfo.platArea);
            $frm.find('.hhldCnt').val(constructInfo.hhldCnt);
            $frm.find('.archArea').val(constructInfo.archArea);
            $frm.find('.bcRat').val(constructInfo.bcRat);
            $frm.find('.totArea').val(constructInfo.totArea);
            $frm.find('.vlRat').val(constructInfo.vlRat);
            $frm.find('.grndFlrCnt').val(constructInfo.grndFlrCnt);
            $frm.find('.ugrndFlrCnt').val(constructInfo.ugrndFlrCnt);
            $frm.find('.rideUseElvtCnt').val(constructInfo.rideUseElvtCnt);
            $frm.find('.emgenUseElvtCnt').val(constructInfo.emgenUseElvtCnt);
            $frm.find('.indrAutoUtcnt').val(constructInfo.indrAutoUtcnt);
            $frm.find('.oudrAutoUtcnt').val(constructInfo.oudrAutoUtcnt);
            $frm.find('.indrMechUtcnt').val(constructInfo.indrMechUtcnt);
            $frm.find('.oudrMechUtcnt').val(constructInfo.oudrMechUtcnt);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadConstructFloorInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    if (dto.existPriceInfo === true) {
        url = "/real-estate/construct/floor/" + dto.realEstateId;
    } else {
        url = "/real-estate/construct/floor/ajax/public-data"
            + "?legalCode=" + dto.legalCode
            + "&landType=" + dto.landType
            + "&bun=" + dto.bun
            + "&ji=" + dto.ji
    }

    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("floor result", result);
            let floorData = result.data;
            let $tbody = $('.construct-floor-table tbody'),
                $tfoot = $('.construct-floor-table tfoot');
            if (floorData.length === 0) {
                let tag = '';
                tag += '<tr>';
                tag += '<td class="text-center" colspan="7">해당 건물의 층별 정보가 없습니다.</td>';
                tag += '</tr>';
                $tbody.html(tag);
                $tfoot.addClass('hidden');
                return;
            }
            
            let tag = drawConstructFloorInfo(floorData);
            $tbody.html(tag);
            $tfoot.removeClass('hidden');
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let drawConstructFloorInfo = function(data) {
    let tag = '';
    $.each(data, function(idx, item) {
        tag += '<tr>';
        tag += '<td class="center-text padding-6 flrNoNm" flrNo="' + item.flrNo + '" data="' + item.flrNoNm + '">' + item.flrNoNm + '</td>';
        tag += '<td class="center-text padding-6 area" data="' + item.area + '">' + item.area + '</td>';
        tag += '<td class="center-text padding-6 mainPurpsCdNm" data="' + item.mainPurpsCdNm + '">' + item.mainPurpsCdNm + '</td>';
        tag += '<td class="center-text padding-6 etcPurps" data="' + item.etcPurps + '">' + item.etcPurps + '</td>';
        tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm" value="0" name="guaranteePrice" style="min-width: 100px;"/></td>';
        tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm" value="0" name="rent" style="min-width: 100px;"/></td>';
        tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm" value="0" name="management" style="min-width: 100px;"/></td>';
        tag += '</tr>';
    })
    return tag;
}

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

        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadMemoInfo = function() {

    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    $.ajax({
        url: "/real-estate/memo/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("memo result", result);
            let memoInfoList = result.data;
            let tag = '';
            if (memoInfoList.length === 0) {
                tag = drawEmptyMemoInfo();
            } else {
                tag = drawMemoInfoList(memoInfoList);
            }
            $('.memoInfo').html(tag);
            initICheck();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let drawBtnUsageCode = function(categories) {

    let tags = "";
    $.each(categories, function (idx, item) {
        if (dto != null && dto.usageType != null && dto.usageType.id === item.id) {
            tags += '<button type="button" class="btn btn-xs btn-primary btnUsageCode selected" usageTypeId="' + item.id + '" name="usageTypeId" style="margin-right: 5px;"> ' + item.name + '</button>';
        } else {
            tags += '<button type="button" class="btn btn-xs btn-default btnUsageCode" usageTypeId="' + item.id + '" name="usageTypeId" style="margin-right: 5px;"> ' + item.name + '</button>';
        }
    });

    return tags;
}

let selectUsageCode = function(e) {
    e.preventDefault();

    let $this = $(this),
        $section = $(this).parents('.usageCdsSection');

    $section.find('.btnUsageCode').each(function() {
        $(this).removeClass("btn-primary");
        $(this).removeClass("selected");
        $(this).addClass("btn-default");
    });

    $this.removeClass("btn-default");
    $this.addClass("btn-primary");
    $this.addClass("selected");
}


let basicInfoSave = function(e) {
    e.preventDefault();

    let isModify = dto.realEstateId != null;
    let $frm = $('form[name="frmBasicRegister"]'),
    httpMethod = isModify ? 'put' : 'post',
    params = serializeObject({form:$frm[0]}).json();
    params["usageTypeId"] = $frm.find('.btnUsageCode.selected').attr("usageTypeId");

    if (!checkNullOrEmptyValue(params.managerUsername)) {
        twoBtnModal('담당자를 선택해주세요.');
        return;
    }

    if (!checkNullOrEmptyValue(params.buildingName)) {
        twoBtnModal('건물명을 입력해주세요.');
        return;
    }

    if (!checkNullOrEmptyValue(params.address)) {
        twoBtnModal('주소를 입력해주세요.');
        return;
    }

    console.log("params", params);

    $.ajax({
        url: "/real-estate/basic",
        method: httpMethod,
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

let priceInfoSave = function(e) {
    e.preventDefault();

    let isModify = dto.existPriceInfo;
    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmPrice = $('form[name="frmPriceRegister"]'),
        httpMethod = isModify ? 'put' : 'post',
        params = serializeObject({form:$frmPrice[0]}).json();

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;
    params.imgUrl = $frmPrice.find('.thumbnailInfo').find('img').attr('src');
    params.realEstateId = dto.realEstateId;

    let floorInfo = [];
    $('.construct-floor-table tbody tr').each(function(idx, item) {
        let param = {
            "flrNo" : $(item).find('.flrNoNm').attr('flrNo'),
            "flrNoNm" : $(item).find('.flrNoNm').attr('data'),
            "area" : $(item).find('.area').attr('data'),
            "mainPurpsCdNm" : $(item).find('.mainPurpsCdNm').attr('data'),
            "etcPurps" : $(item).find('.etcPurps').attr('data'),
            "guaranteePrice" : $(item).find('input[name="guaranteePrice"]').val(),
            "rent" : $(item).find('input[name="rent"]').val(),
            "management" : $(item).find('input[name="management"]').val(),
        }
        floorInfo.push(param);
    });

    params.floorInfo = floorInfo;

    console.log("params", params);

    $.ajax({
        url: "/real-estate/price",
        method: httpMethod,
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

let constructInfoSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let isModify = dto.existConstructInfo;
    let $frmConstruct = $('form[name="frmConstructRegister"]'),
        params = serializeObject({form:$frmConstruct[0]}).json();

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;
    params.realEstateId = dto.realEstateId;

    console.log("params", params);


    $.ajax({
        url: "/real-estate/construct",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let message = '정상적으로 저장되었습니다.';
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
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
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="customerName" value="' +  item.customerName + '"/>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-2">';
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
    tag +=         '<div class="col-md-4">';
    tag +=             '<label class="text-label">생년월일</label>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="birth" value="' +  item.birth + '"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="phone" value="' +  item.phone + '"/>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">기타전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="etcPhone" value="' +  item.etcPhone + '"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line">';
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
    tag +=             '<label class="text-label">대표자명</label>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="representName" value="' +  item.representName + '"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">법인전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="companyPhone" value="' +  item.companyPhone + '"/>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="representPhone" value="' +  item.representPhone + '"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line">';
    tag +=         '<input type="text" class="form-control form-control-sm" name="etcInfo" value="' +  item.etcInfo + '" placeholder="비고"/>';
    tag +=     '</div>';

    return tag;
}

let addCustomerSave = function(e) {
    e.preventDefault();

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
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let addMemo = function(e) {

    if (e.key !== "Enter") {
        return;
    }

    if (dto.realEstateId === null) {
        return;
    }

    e.preventDefault();

    let params = {
        "realEstateId" : dto.realEstateId,
        "memo" : $(this).val(),
    }

    console.log("memo", params);

    $.ajax({
        url: "/real-estate/memo",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let message = '정상적으로 저장되었습니다.';
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let drawEmptyMemoInfo = function() {
    let tag = '';
    tag += '<tr>';
    tag +=    '<td class="text-center" colSpan="4">등록된 메모가 없습니다.</td>';
    tag += '</tr>';
    return tag;
}

let drawMemoInfoList = function(memoInfoList) {
    let tag = '';

    $.each(memoInfoList, function(idx, item) {
        tag += '<tr>';
        tag +=  '<td style="width:10%">';
        tag +=      '<input type="checkbox" class="checkElement" name="memoId" value="' + item.memoId +'">';
        tag +=  '</td>';
        tag +=  '<td style="width:20%">' + item.createdAtFormat + '</td>';
        tag +=  '<td style="width:20%">' + item.createdByName + '</td>';
        tag +=  '<td style="width:50%">' + item.memo + '</td>';
        tag += '</tr>';
    });

    return tag;
}

let searchAddress = function(e) {
    e.preventDefault();

    let $frm = $(this).parents(`form[name="frmBasicRegister"]`);
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
            $frm.find(`input[name="address"]`).val(data.address);
            $frm.find(`input[name="addressDetail"]`).focus();
            loadKakaoMap(data.address)
        }
    }).open();
}

let addCommasToNumber = function(number) {
    number = Math.floor(number);
    number = Math.round(number / 100) * 100;
    let numberStr = number.toString();
    numberStr = numberStr.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return numberStr;
}

let uploadImage = function(e) {
    e.preventDefault();

    documentUpload({
        multiple: false,
        accept: '.jpg, .png, .gif',
        sizeCheck: false,
        usageType: `RealEstate`,
        fileType: `Image`,
        callback: function (res) {
            console.log("res", res);
            let image = res.data;
            let $imagePanel = $('.image-section');
            let tag = imgDraw(image.fullPath);
            $imagePanel.html(tag);
            $imagePanel.find('.thumbnailInfo').last().data('thumbnail-data', image);
        }
    });
}

let imgDraw = function (fullPath) {

    let tag = '' +
        '<div class="thumbnailInfo ui-state-default">' +
        '<div class="col-md-12 no-left-padding right-margin">' +
        '<div class="image-panel" style="width:100%;">' +
        '<button type="button" class="btn btn-danger pull-right remove-image">' +
        '<i class="fa fa-times" aria-hidden="true"></i>' +
        '</button>' +
        '<a href="#"><img src="' + fullPath + '" class="btnImageUpload"></a>' +
        '</div>' +
        '</div>' +
        '</div>';

    return tag;
};

let removeImage = function() {
    let $imagePanel = $('.image-section');
    let tag = '<img src="/images/no-image-found.jpeg" class="col-sm-12 no-left-padding btnImageUpload thumbnailInfo" style="cursor: pointer;"/>';
    $imagePanel.html(tag);
}

let removeBtn = function(e) {
    e.preventDefault();
    $(this).parents('button').remove();
}

$(document).ready(onReady)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '.btnUsageCode', selectUsageCode)
    .on('click', '.btnBasicSave', basicInfoSave)
    .on('click', '.btnPriceSave', priceInfoSave)
    .on('click', '.btnConstructSave', constructInfoSave)
    .on('click', '.toggleCustomer', changeCustomerInfo)
    .on('click', '.btnCustomerAdd', addCustomerInfo)
    .on('click', '.btnCustomerSave', addCustomerSave)
    .on('keydown', '#memoInput', addMemo)
    .on('click', '.btnImageUpload', uploadImage)
    .on('click', '.remove-image', removeImage)
    .on('click', '.btnLandSave', landInfoSave)
    .on('click', '.btnLandLoad', loadLandInfoById)
    .on('click', '.btnLandAdd', landInfoAdd)
    .on('click', '.removeBtn', removeBtn);
