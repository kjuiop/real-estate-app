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

let realEstateSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        $frmPrice = $('form[name="frmPriceRegister"]'),
        $frmConstruct = $('form[name="frmConstructRegister"]'),
        params = serializeObject({form:$frmBasic[0]}).json();

    params["usageTypeId"] = $frmBasic.find('.btnUsageCode.selected').attr("usageTypeId");
    params.imgUrl = $frmPrice.find('.thumbnailInfo').find('img').attr('src');

    if (!checkNullOrEmptyValue(params.managerUsername)) {
        twoBtnModal("담당자를 선택해주세요.");
        return;
    }

    params.ownExclusiveYn === 'on' ? (params.ownExclusiveYn = 'Y') : (params.ownExclusiveYn = 'N')
    params.otherExclusiveYn === 'on' ? (params.otherExclusiveYn = 'Y') : (params.otherExclusiveYn = 'N')


    let $frmLand = $('form[name="frmLandRegister"]'),
        commercialYn = $frmLand.find('input[name="commercialYn"]').is(":checked") ? "Y" : "N";

    let $section = $frmLand.find('.btnLandSection'),
        addBtnLength = $section.find('.btnLandLoad').length;

    if (addBtnLength === 0) {
        twoBtnModal('저장하려는 토지 정보를 추가해주세요.');
        return;
    }

    let landInfoList = [];
    $frmLand.find('.btnLandSection .btnLandLoad').each(function (idx, item) {
        let landData = $(item).data('land-data');
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclAr = landData.lndpclAr.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclArByPyung = landData.lndpclArByPyung.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.pblntfPclnd = landData.pblntfPclnd.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.totalPblntfPclnd = landData.totalPblntfPclnd.replaceAll(',', '');
        }
        landData.realEstateId = dto.realEstateId;
        landData.commercialYn = commercialYn;
        landInfoList.push(landData);
    });

    params.landInfoList = landInfoList;
    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();

    let floorInfoList = [];
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
        floorInfoList.push(param);
    });

    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    console.log("save params", params);

    twoBtnModal("매물 정보를 일괄 저장하시겠습니까?", function () {
        $.ajax({
            url: "/real-estate",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    location.href = '/real-estate/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let realEstateUpdate = function(e) {
    e.preventDefault();

    let $basicFrm = $('form[name="frmBasicRegister"]'),
        $frmPrice = $('form[name="frmPriceRegister"]'),
        $frmConstruct = $('form[name="frmConstructRegister"]'),
        params = serializeObject({form:$basicFrm[0]}).json();

    params["usageTypeId"] = $basicFrm.find('.btnUsageCode.selected').attr("usageTypeId");
    params.imgUrl = $frmPrice.find('.thumbnailInfo').find('img').attr('src');

    if (!checkNullOrEmptyValue(params.managerUsername)) {
        twoBtnModal("담당자를 선택해주세요.");
        return;
    }

    params.ownExclusiveYn === 'on' ? (params.ownExclusiveYn = 'Y') : (params.ownExclusiveYn = 'N')
    params.otherExclusiveYn === 'on' ? (params.otherExclusiveYn = 'Y') : (params.otherExclusiveYn = 'N')

    let $frmLand = $('form[name="frmLandRegister"]'),
        commercialYn = $frmLand.find('input[name="commercialYn"]').is(":checked") ? "Y" : "N";

    let $section = $frmLand.find('.btnLandSection'),
        addBtnLength = $section.find('.btnLandLoad').length;

    if (addBtnLength === 0) {
        twoBtnModal('저장하려는 토지 정보를 추가해주세요.');
        return;
    }

    let landInfoList = [];
    $frmLand.find('.btnLandSection .btnLandLoad').each(function (idx, item) {
        let landData = $(item).data('land-data');
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclAr = landData.lndpclAr.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclArByPyung = landData.lndpclArByPyung.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.pblntfPclnd = landData.pblntfPclnd.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.totalPblntfPclnd = landData.totalPblntfPclnd.replaceAll(',', '');
        }
        landData.realEstateId = dto.realEstateId;
        landData.commercialYn = commercialYn;
        landInfoList.push(landData);
    });

    params.landInfoList = landInfoList;
    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();

    let floorInfoList = [];
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
        floorInfoList.push(param);
    });

    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";


    console.log("update params", params);

    twoBtnModal("매물 정보를 일괄 수정하시겠습니까?", function () {
        $.ajax({
            url: "/real-estate",
            method: 'put',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 수정되었습니다.', function() {
                    location.href = '/real-estate/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });

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
            setCustomerCnt();
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
    tag +=         '<div>';
    tag +=             '<label class="text-label">대표자명</label>';
    tag +=             '<button type="button" class="btn btn-xs btn-default btnRemoveCustomerInfo pull-right">삭제</button>'
    tag +=         '</div>'
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
    tag +=    '<td class="text-center" colSpan="5">등록된 메모가 없습니다.</td>';
    tag += '</tr>';
    return tag;
}

let drawMemoInfoList = function(memoInfoList) {
    let tag = '';

    $.each(memoInfoList, function(idx, item) {
        tag += '<tr class="memoUnit">';
        tag +=  '<td style="width:10%">';
        tag +=      '<input type="checkbox" class="checkElement" name="memoId" value="' + item.memoId +'">';
        tag +=  '</td>';
        tag +=  '<td style="width:20%">' + item.createdAtFormat + '</td>';
        tag +=  '<td style="width:20%">' + item.createdByName + '</td>';
        tag +=  '<td style="width:40%">' + item.memo + '</td>';
        tag +=  '<td style="width:10%"><button type="button" class="btn btn-xs btn-danger btnRemoveMemo pull-right"><i class="fa fa-times" aria-hidden="true" style="font-size: 15px;"></i></button></td>';
        tag += '</tr>';
    });
    return tag;
}

let removeMemo = function(e) {
    e.preventDefault();

    let memoId = $(this).parents('.memoUnit').find('input[name="memoId"]').val();

    let params = {
        "realEstateId" : dto.realEstateId,
        "memoId" : memoId,
    }

    twoBtnModal("메모를 삭제하시겠습니까?", function() {
        $.ajax({
            url: "/real-estate/memo",
            method: "delete",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("result : ", result);
                let message = '정상적으로 삭제되었습니다.';
                twoBtnModal(message, function() {
                    location.reload();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let removeAllMemo = function(e) {
    e.preventDefault();

    let memoIds = [];
    $("input[name='memoId']:checked").each(function (idx, item) {
        memoIds.push($(item).val());
    });

    let params = {
        "realEstateId" : dto.realEstateId,
        "memoIds" : memoIds
    }

    twoBtnModal("메모를 삭제하시겠습니까?", function() {
        $.ajax({
            url: "/real-estate/memo/list",
            method: "delete",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("result : ", result);
                let message = '정상적으로 삭제되었습니다.';
                twoBtnModal(message, function() {
                    location.reload();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
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

let removeImage = function() {
    let $imagePanel = $('.image-section');
    let tag = '<img src="/images/no-image-found.jpeg" class="col-sm-12 no-left-padding btnImageUpload thumbnailInfo" style="cursor: pointer;"/>';
    $imagePanel.html(tag);
}

let removeBtn = function(e) {
    e.preventDefault();
    $(this).parents('button').remove();
}

let changeProcessStatus = function(e) {
    e.preventDefault();

    let processStatus = $(this).attr('processType');
    let params = {
        "realEstateId" : dto.realEstateId,
        "processType": processStatus
    }

    console.log("changeProcessStatus params :", params);


    let isValid = checkInputRealEstate();
    if (!isValid) {
        return;
    }

    twoBtnModal('매물 상태를 변경하시겠습니까?', function() {
        $.ajax({
            url: "/real-estate/process",
            method: "put",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("result : ", result);
                let message = '정상적으로 변경되었습니다.';
                twoBtnModal(message, function() {
                    location.reload();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let checkInputRealEstate = function() {
    if (!dto.existLandInfo) {
        twoBtnModal("토지정보를 저장해주세요.");
        return false;
    }

    if (!dto.existPriceInfo) {
        twoBtnModal("금액정보를 저장해주세요.");
        return false;
    }

    if (!dto.existConstructInfo) {
        twoBtnModal("건축물 정보를 저장해주세요.");
        return false;
    }

    if (!dto.existCustomerInfo) {
        twoBtnModal("고객 정보를 저장해주세요.");
        return false;
    }

    return true;
}

$(document).ready(onReady)
    .on('click', '.btnSave', realEstateSave)
    .on('click', '.btnUpdate', realEstateUpdate)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '.btnUsageCode', selectUsageCode)
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
    .on('click', '.removeBtn', removeBtn)
    .on('click', '.btnRemoveCustomerInfo', removeCustomerInfo)
    .on('click', '.btnRemoveMemo', removeMemo)
    .on('click', '.btnRemoveAllMemo', removeAllMemo)
    .on('click', '.btnProcessType', changeProcessStatus);
