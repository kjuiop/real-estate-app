let onReady = function() {
    console.log("dto", dto);
    loadBasicInfo();
    loadLandInfo();
    loadPriceInfo();
    loadConstructInfo();
    $('#customerInfoSection').html(drawUnitCustomerInfo());
    onlyNumberKeyEvent({className: "only-number"});
}

let loadBasicInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    loadKakaoMap(dto.address);

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

let loadLandInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;

    if (checkNullOrEmptyValue(dto.landInfoId)) {
        url = "/real-estate/land/" + dto.realEstateId;
    } else {
        url = "/real-estate/land/ajax/public-data"
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
            let landList = result.data,
                landInfo = landList[0];
            let $frm = $('form[name="frmLandRegister"]');
            $frm.find('.landSize').text(landList.length);
            $frm.find('.area').text(landInfo.lndpclAr);
            $frm.find('.pyung').text(landInfo.lndpclArByPyung);
            $frm.find('.lndpclAr').val(landInfo.lndpclAr);
            $frm.find('.lndpclArByPyung').val(landInfo.lndpclArByPyung);
            $frm.find('.pblntfPclnd').val(addCommasToNumber(landInfo.pblntfPclnd));
            $frm.find('.totalPblntfPclnd').val(addCommasToNumber(landInfo.totalPblntfPclnd));
            $frm.find('.lndcgrCodeNm').val(landInfo.lndcgrCodeNm);
            $frm.find('.prposArea1Nm').val(landInfo.prposArea1Nm);
            $frm.find('.ladUseSittnNm').val(landInfo.ladUseSittnNm);
            $frm.find('.roadSideCodeNm').val(landInfo.roadSideCodeNm);
            $frm.find('.tpgrphHgCodeNm').val(landInfo.tpgrphHgCodeNm);
            $frm.find('.tpgrphFrmCodeNm').val(landInfo.tpgrphFrmCodeNm);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadPriceInfo = function() {

    if (!checkNullOrEmptyValue(dto) || !checkNullOrEmptyValue(dto.priceInfoId)) {
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

    if (checkNullOrEmptyValue(dto.constructInfoId)) {
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

let drawBtnUsageCode = function(categories) {

    let tags = "";
    $.each(categories, function (idx, item) {
        if (dto != null && dto.usageType != null && dto.usageType.id === item.id) {
            tags += '<button type="button" class="btn btn-xs btn-primary btnUsageCode" usageTypeId="' + item.id + '" name="usageTypeId" style="margin-right: 5px;"> ' + item.name + '</button>';
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

    let $frm = $('form[name="frmBasicRegister"]'),
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

let landInfoSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json();

    if (!checkNullOrEmptyValue(params.address)) {
        twoBtnModal('주소를 입력해주세요.');
        return;
    }

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;

    params.lndpclAr = params.lndpclAr.replaceAll(',', '');
    params.lndpclArByPyung = params.lndpclArByPyung.replaceAll(',', '');
    params.pblntfPclnd = params.pblntfPclnd.replaceAll(',', '');
    params.totalPblntfPclnd = params.totalPblntfPclnd.replaceAll(',', '');

    $.ajax({
        url: "/real-estate/land",
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

let priceInfoSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmLand = $('form[name="frmPriceRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json();

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;

    console.log("params", params);


    $.ajax({
        url: "/real-estate/price",
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

let constructInfoSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmConstruct = $('form[name="frmConstructRegister"]'),
        params = serializeObject({form:$frmConstruct[0]}).json();

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;

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
        $unit.html(drawCustomerInfo());
    } else {
        $unit.html(drawCompanyInfo());
    }

}

let drawUnitCustomerInfo = function() {
    let tag = '';
    tag += '<div class="customerInfoUnit">';
    tag += drawCustomerInfo();
    tag += '</div>';
    return tag;
}

let addCustomerInfo = function(e) {
    e.preventDefault();

    $('#customerInfoSection').append(drawUnitCustomerInfo());
}

let drawCustomerInfo = function() {

    let tag = '';
    tag +=     '<div class="row display-flex-row margin-bottom-5">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<div class="display-flex-row">';
    tag +=                 '<div class="col-md-6 no-left-padding">';
    tag +=                     '<label class="text-label">고객명</label>';
    tag +=                 '</div>';
    tag +=                 '<div class="col-md-6">';
    tag +=                     '<label class="text-label pull-right">';
    tag +=                      '<input type="hidden" class="form-control form-control-sm" name="type" value="customer" />';
    tag +=                         '<span class="toggleCustomer text-blue button-pointer" type="customer">개인</span>  | ';
    tag +=                         '<span class="toggleCustomer button-pointer" type="company">법인</span>' ;
    tag +=                     '</label>';
    tag +=                 '</div>';
    tag +=             '</div>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="customerName"/>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-2">';
    tag +=             '<label class="text-label">성별</label>';
    tag +=             '<select class="form-control form-control-sm valid-ignore custom-select" name="gender">';
    tag +=                 '<option value="man">남</option>';
    tag +=                 '<option value="woman">여</option>';
    tag +=             '</select>';
    tag +=         '</div>';
    tag +=         '<div class="col-md-4">';
    tag +=             '<label class="text-label">생년월일</label>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="birth"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="phone" />';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">기타전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="etcPhone" />';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line">';
    tag +=         '<input type="text" class="form-control form-control-sm" name="etcInfo" placeholder="비고"/>';
    tag +=     '</div>';

    return tag;
}

let drawCompanyInfo = function() {

    let tag = '';
    tag +=     '<div class="row display-flex-row margin-bottom-5 test">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<div class="display-flex-row">';
    tag +=                 '<div class="col-md-6 no-left-padding">';
    tag +=                     '<label class="text-label">법인명</label>';
    tag +=                 '</div>';
    tag +=                 '<div class="col-md-6">';
    tag +=                     '<label class="text-label pull-right">';
    tag +=                      '<input type="hidden" class="form-control form-control-sm" name="type" value="company" />';
    tag +=                         '<span class="toggleCustomer button-pointer" type="customer">개인</span>  | ';
    tag +=                         '<span class="toggleCustomer button-pointer text-blue" type="company">법인</span>' ;
    tag +=                     '</label>';
    tag +=                 '</div>';
    tag +=             '</div>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="companyName" />';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">대표자명</label>';
    tag +=             '<input type="text" class="form-control form-control-sm input-height-36" name="representName" />';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="row display-flex-row">';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">법인전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="companyPhone" />';
    tag +=         '</div>';
    tag +=         '<div class="col-md-6">';
    tag +=             '<label class="text-label">휴대전화</label>';
    tag +=             '<input type="text" class="form-control form-control-sm" name="representPhone"/>';
    tag +=         '</div>';
    tag +=     '</div>';
    tag +=     '<div class="col-md-12 display-flex-row no-left-padding margin-top-5 line">';
    tag +=         '<input type="text" class="form-control form-control-sm" name="etcInfo" placeholder="비고"/>';
    tag +=     '</div>';

    return tag;
}

let addCustomerSave = function(e) {
    e.preventDefault();

    let params = [];
    let $frm = $('form[name="frmCustomerRegister"]');
    $frm.find('.customerInfoUnit').each(function (idx, item) {
        let type = $(this).find('input[name="type"]').val();
        let param;
        if (type === 'customer') {
            param = {
                "type" : type,
                "customerName" : $(item).find('input[name="customerName"]').val(),
                "gender" : $(item).find('select[name="gender"]').val(),
                "birth" : $(item).find('input[name="birth"]').val(),
                "phone" : $(item).find('input[name="phone"]').val(),
                "etcPhone" : $(item).find('input[name="etcPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        } else {
            param = {
                "type" : type,
                "companyName" : $(item).find('input[name="companyName"]').val(),
                "representName" : $(item).find('select[name="representName"]').val(),
                "companyPhone" : $(item).find('input[name="companyPhone"]').val(),
                "representPhone" : $(item).find('input[name="representPhone"]').val(),
                "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
            }
        }

        params.push(param);
    });

    console.log("params", params);
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

$(document).ready(onReady)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '.btnUsageCode', selectUsageCode)
    .on('click', '.btnBasicSave', basicInfoSave)
    .on('click', '.btnLandSave', landInfoSave)
    .on('click', '.btnPriceSave', priceInfoSave)
    .on('click', '.btnConstructSave', constructInfoSave)
    .on('click', '.toggleCustomer', changeCustomerInfo)
    .on('click', '.btnCustomerAdd', addCustomerInfo)
    .on('click', '.btnCustomerSave', addCustomerSave);
