let onReady = function() {
    console.log("dto", dto);
    loadBasicInfo();
    loadLandInfoList();
    loadLandPriceInfoList();
    loadLandUsageInfo();
    loadPriceInfo();
    loadConstructInfo();
    loadConstructFloorInfo();
    loadCustomerInfo();
    loadMemoInfo();
    loadImageInfo();
    $('#customerInfoSection').html(drawUnitCustomerInfo("CUSTOMER", null));
    loadPrintInfo();
    onlyNumberKeyEvent({className: "only-number"});
}

let realEstateSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        $frmPrice = $('form[name="frmPriceRegister"]'),
        $frmConstruct = $('form[name="frmConstructRegister"]'),
        $frmLandUsage = $('form[name="frmLandUsageRegister"]'),
        params = serializeObject({form:$frmBasic[0]}).json();

    params["banAdvertisingYn"] = $frmBasic.find('input[name="banAdvertisingYn"]').is(":checked") ? "Y" : "N";
    params["imgUrl"] = $frmPrice.find('.main-section img').attr('src');
    params["managerIds"] = getManagerIds();
    params["propertyTypeId"] = $frmBasic.find('select[name="propertyType"] option:selected').val();
    params["buildingTypeCds"] = $frmBasic.find('.buildingTypeCds option:selected').val();
    params["exclusiveCds"] = extractCodeId($('.exclusiveSection'));
    params["realEstateGradeCds"] = extractCodeId($('.realEstateGradeSection'));
    params["usageCds"] = extractCodeId($('.usageTypeSection'));

    let subImages = [];
    let $imgSubImgSection = $('.image-sub-section');
    $imgSubImgSection.find('.sub-img-unit').each(function(idx, item) {
       let image = {
           "imageId" : $(item).find('.sub-image').attr('imageId'),
           "fullPath" : $(item).find('.sub-image').attr('src'),
       }
       subImages.push(image);
    });
    params.subImages = subImages;

    let landInfoList = assembleLandParams();
    if (landInfoList.length === 0) {
        return;
    }

    params.landInfoList = landInfoList;
    params.landUsageInfo = serializeObject({form:$frmLandUsage[0]}).json();

    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();
    params.priceInfo.totalLndpclArByPyung = $('input[name="totalLndpclArByPyung"]').val();
    params.priceInfo.totArea = $('input[name="totArea"]').val();
    params.priceInfo.totAreaByPyung = $frmConstruct.find('input[name="totAreaByPyung"]').val();
    params.priceInfo.landUnitPrice = removeComma($frmPrice.find('input[name="landUnitPrice"]').val());
    params.priceInfo.totalAreaUnitPrice = removeComma($frmPrice.find('input[name="totalAreaUnitPrice"]').val());
    params.priceInfo.salePrice = removeComma($frmPrice.find('input[name="salePrice"]').val());
    params.priceInfo.priceAdjuster = removeComma($frmPrice.find('input[name="priceAdjuster"]').val());
    params.priceInfo.guaranteePrice = removeComma($frmPrice.find('input[name="guaranteePrice"]').val());
    params.priceInfo.rentMonth = removeComma($frmPrice.find('input[name="rentMonth"]').val());
    params.priceInfo.management = removeComma($frmPrice.find('input[name="management"]').val());
    params.priceInfo.managementExpense = removeComma($frmPrice.find('input[name="managementExpense"]').val());

    let floorInfoList = assembleFloorParams();
    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    let customerInfoList = assembleCustomerParam();
    params.customerInfoList = customerInfoList;

    let $pblntTable = $('.pblnt-table tbody');
    let landPriceInfoList = [];
    $pblntTable.find('tr').each(function(idx, item) {
        let pblntPrice = {
            "responseCode": $(item).find('.data-field').attr('responseCode'),
            "lastCurlApiAt": $(item).find('.data-field').attr('lastCurlApiAt'),
            "landPriceId": $(item).find('.data-field').attr('landPriceId'),
            "pnu": $(item).find('.data-field').attr('pnu'),
            "pclndStdrYear": $(item).find('.data-field').attr('pclndStdrYear'),
            "pblntfPclnd": $(item).find('.pblntfPclnd').attr('pblntfPclnd'),
            "pblntfPclndPy": $(item).find('.pblntfPclndPy').attr('pblntfPclndPy'),
            "changeRate": $(item).find('.changeRate').attr('changeRate'),
        }
        landPriceInfoList.push(pblntPrice);
    });
    params.landPriceInfoList = landPriceInfoList;

    console.log("save params", params);

    twoBtnModal("매물 정보를 일괄 저장하시겠습니까?", function () {
        $.ajax({
            url: "/real-estate",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                location.href = '/real-estate/' + result.data + '/edit';
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
        $frmLandUsage = $('form[name="frmLandUsageRegister"]'),
        params = serializeObject({form:$basicFrm[0]}).json();

    params["propertyTypeId"] = $basicFrm.find('select[name="propertyType"] option:selected').val();

    params["banAdvertisingYn"] = $basicFrm.find('input[name="banAdvertisingYn"]').is(":checked") ? "Y" : "N";
    params["imgUrl"] = $frmPrice.find('.main-section img').attr('src');
    params["managerIds"] = getManagerIds();
    params["exclusiveCds"] = extractCodeId($('.exclusiveSection'));
    params["realEstateGradeCds"] = extractCodeId($('.realEstateGradeSection'));
    params["buildingTypeCds"] = $basicFrm.find('.buildingTypeCds option:selected').val();
    params["usageCds"] = extractCodeId($('.usageTypeSection'));

    let subImages = [];
    let $imgSubImgSection = $('.image-sub-section');
    $imgSubImgSection.find('.sub-img-unit').each(function(idx, item) {
        let image = {
            "imageId" : $(item).find('.sub-image').attr('imageId'),
            "fullPath" : $(item).find('.sub-image').attr('src'),
        }
        subImages.push(image);
    });
    params.subImages = subImages;

    let landInfoList = assembleLandParams();
    if (landInfoList.length === 0) {
        return;
    }
    
    params.landInfoList = landInfoList;
    params.landUsageInfo = serializeObject({form:$frmLandUsage[0]}).json();

    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();
    params.priceInfo.totalLndpclArByPyung = $('input[name="totalLndpclArByPyung"]').val();
    params.priceInfo.totArea = $('input[name="totArea"]').val();
    params.priceInfo.totAreaByPyung = $frmConstruct.find('input[name="totAreaByPyung"]').val();
    params.priceInfo.landUnitPrice = removeComma($frmPrice.find('input[name="landUnitPrice"]').val());
    params.priceInfo.totalAreaUnitPrice = removeComma($frmPrice.find('input[name="totalAreaUnitPrice"]').val());
    params.priceInfo.salePrice = removeComma($frmPrice.find('input[name="salePrice"]').val());
    params.priceInfo.priceAdjuster = removeComma($frmPrice.find('input[name="priceAdjuster"]').val());
    params.priceInfo.guaranteePrice = removeComma($frmPrice.find('input[name="guaranteePrice"]').val());
    params.priceInfo.rentMonth = removeComma($frmPrice.find('input[name="rentMonth"]').val());
    params.priceInfo.management = removeComma($frmPrice.find('input[name="management"]').val());
    params.priceInfo.managementExpense = removeComma($frmPrice.find('input[name="managementExpense"]').val());



    let floorInfoList = assembleFloorParams();
    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    let customerInfoList = assembleCustomerParam();
    params.customerInfoList = customerInfoList;

    let $pblntTable = $('.pblnt-table tbody');
    let landPriceInfoList = [];
    $pblntTable.find('tr').each(function(idx, item) {
        let pblntPrice = {
            "responseCode": $(item).find('.data-field').attr('responseCode'),
            "lastCurlApiAt": $(item).find('.data-field').attr('lastCurlApiAt'),
            "landPriceId": $(item).find('.data-field').attr('landPriceId'),
            "pnu": $(item).find('.data-field').attr('pnu'),
            "pclndStdrYear": $(item).find('.data-field').attr('pclndStdrYear'),
            "pblntfPclnd": $(item).find('.pblntfPclnd').attr('pblntfPclnd'),
            "pblntfPclndPy": $(item).find('.pblntfPclndPy').attr('pblntfPclndPy'),
            "changeRate": $(item).find('.changeRate').attr('changeRate')
        }
        landPriceInfoList.push(pblntPrice);
    });
    params.landPriceInfoList = landPriceInfoList;

    console.log("update params", params);

    twoBtnModal("매물 정보를 일괄 수정하시겠습니까?", function () {
        $.ajax({
            url: "/real-estate",
            method: 'put',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                location.href = '/real-estate/' + result.data + '/edit';
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });

}

let drawBtnUsageCode = function(categories) {

    let tags = "";
    $.each(categories, function (idx, item) {
        if (dto.usageCdId === item.id || (dto.usageType != null && dto.usageType.id === item.id)) {
            tags += '<button type="button" class="btn btn-sm btn-primary btnUsageCode selected" usageTypeId="' + item.id + '" name="usageTypeId" style="margin-right: 5px;"> ' + item.name + '</button>';
        } else {
            tags += '<button type="button" class="btn btn-sm btn-default btnUsageCode" usageTypeId="' + item.id + '" name="usageTypeId" style="margin-right: 5px;"> ' + item.name + '</button>';
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

let changeProcessStatus = function(e) {
    e.preventDefault();

    if (dto.ownUser !== true) {
        twoBtnModal("매물 상태를 변경할 권한이 없습니다.");
        return;
    }

    let processStatus = $(this).attr('processType');
    let params = {
        "realEstateId" : dto.realEstateId,
        "processType": processStatus
    }

    if (processStatus === 'Prepare' && dto.processType !== 'NotAssign') {
        return;
    }

    if (processStatus === dto.processType) {
        return;
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
                location.reload();
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

let changeRStatus = function(e) {
    e.preventDefault();

    let params = {
        "realEstateId" : dto.realEstateId,
        "rYn" : $(this).attr('rStatus')
    }

    console.log('r status params : ', params)

    twoBtnModal('R 표시를 전환하겠습니까?', function() {
        $.ajax({
            url: "/real-estate/status/r",
            method: "put",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                location.reload();
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let changeABStatus = function(e) {
    e.preventDefault();

    let params = {
        "realEstateId" : dto.realEstateId,
        "abYn" : $(this).attr('abStatus'),
    }

    console.log('abStatus params : ', params)

    twoBtnModal('AB 표시를 전환하겠습니까?', function() {
        $.ajax({
            url: "/real-estate/status/ab",
            method: "put",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                location.reload();
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let movePrevPage = function(e) {
    e.preventDefault();

    if (!checkNullOrEmptyValue(dto.prevId)) {
        twoBtnModal("이전 매물정보가 존재하지 않습니다.");
        return;
    }

    location.href = '/real-estate/' + dto.prevId + '/edit';
}

let moveNextPage = function(e) {
    e.preventDefault();

    if (!checkNullOrEmptyValue(dto.nextId)) {
        twoBtnModal("다음 매물정보가 존재하지 않습니다.");
        return;
    }

    location.href = '/real-estate/' + dto.nextId + '/edit';
}

let printPdf = function(e) {
    e.preventDefault();
    window.print();
}

let openPrintPop = function(e) {
    e.preventDefault();

    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    let _width = 1300;
    let _height = 1000;
    let _left = Math.ceil((window.screen.width - _width) / 2);
    let _top = Math.ceil((window.screen.height - _height) / 2);


    let options = 'top=10, left=10, width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top + ', status=no, menubar=no, toolbar=no, resizable=no';
    window.open('/real-estate/print/' + dto.realEstateId, 'print-popup', options);
}

let loadPrintInfo = function() {
    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    let $frm = $('form[name="frmPrintImage"]');

    if (checkNullOrEmptyValue(dto.printInfo) && checkNullOrEmptyValue(dto.printInfo.propertyImgUrl)) {
        let $imagePanel = $frm.find('#propertyImgUrl');
        let tag = imgDraw(dto.printInfo.propertyImgUrl);
        $imagePanel.html(tag);
    }

    if (checkNullOrEmptyValue(dto.printInfo) && checkNullOrEmptyValue(dto.printInfo.buildingImgUrl)) {
        let $imagePanel = $frm.find('#buildingImgUrl');
        let tag = imgDraw(dto.printInfo.buildingImgUrl);
        $imagePanel.html(tag);
    }

    if (checkNullOrEmptyValue(dto.printInfo) && checkNullOrEmptyValue(dto.printInfo.locationImgUrl)) {
        let $imagePanel = $frm.find('#locationImgUrl');
        let tag = imgDraw(dto.printInfo.locationImgUrl);
        $imagePanel.html(tag);
    }

    if (checkNullOrEmptyValue(dto.printInfo) && checkNullOrEmptyValue(dto.printInfo.landDecreeImgUrl)) {
        let $imagePanel = $frm.find('#landDecreeImgUrl');
        let tag = imgDraw(dto.printInfo.landDecreeImgUrl);
        $imagePanel.html(tag);
    }

    if (checkNullOrEmptyValue(dto.printInfo) && checkNullOrEmptyValue(dto.printInfo.developPlanImgUrl)) {
        let $imagePanel = $frm.find('#developPlanImgUrl');
        let tag = imgDraw(dto.printInfo.developPlanImgUrl);
        $imagePanel.html(tag);
    }
}

let toggleAddressType = function(e) {
    e.preventDefault();

    let type = $(this).val();
    let $frm = $('form[name="frmMoveRegister"]');

    if (type === 'kakao') {
        $frm.find('.kakao-section').removeClass('hidden');
        $frm.find('.direct-section').addClass('hidden');
    } else if (type === 'direct') {
        $frm.find('.kakao-section').addClass('hidden');
        $frm.find('.direct-section').removeClass('hidden');
    }

    $frm.find('input[name="address"]').val('');
}

let getChildAreaData = function() {
    let areaId = $(this).find('option:selected').attr('areaId'),
        name = $(this).attr('name');

    if (!checkNullOrEmptyValue(areaId)) {
        oneBtnModal("시/도, 군/구를 선택해주세요.");
        return;
    }

    let $frm = $(this).parents('form');

    $.ajax({
        url: "/settings/area-manager/" + areaId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function (result) {
            console.log("area result : ", result);
            let areaList = result.data;

            let $gungu = $frm.find('select[name="gungu"]'),
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
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '" name="' + item.name + '">' + item.name + '</option>';
    });
    return tag;
}

let showCadastralModal = function(e) {
    e.preventDefault();
    let $CadastralModal = $('#landCadastralModal');
    $CadastralModal.modal('show');
}

let toggleSelectButton = function(e) {
    e.preventDefault();

    let $this = $(this);
    if ($this.hasClass('selected')) {
        $this.removeClass('selected');
        $this.removeClass('btn-primary');
        $this.addClass('btn-default');
    } else {
        $this.addClass('selected');
        $this.addClass('btn-primary');
        $this.removeClass('btn-default');
    }
}

let toggleSelectOneButton = function(e) {
    e.preventDefault();

    let $this = $(this),
        $section = $(this).parents('.selected-button-radio-section');

    $section.find('button').each(function() {
        $(this).removeClass("btn-primary");
        $(this).removeClass("selected");
        $(this).addClass("btn-default");
    });

    $this.removeClass("btn-default");
    $this.addClass("btn-primary");
    $this.addClass("selected");
}

let extractCodeId = function(section) {
    let extractCds = '';
    $(section).find('.btnCodeCd').each(function(idx, item) {
        if ($(item).hasClass('selected')) {
            extractCds += $(item).attr('code');
            extractCds += ',';
        }
    });

    if (extractCds.endsWith(',')) {
        extractCds = extractCds.slice(0, -1);
    }
    return extractCds;
}

let drawManager = function(e) {
    e.preventDefault();

    let $this = $(this),
        username = $this.val(),
        name = $this.find('option:selected').attr('adminName'),
        adminId = parseInt($this.find('option:selected').attr('adminId'))
    ;

    if (!checkNullOrEmptyValue(adminId) || isNaN(adminId)) {
        return;
    }

    let isExist = false;
    $('.managerSection').find('.btnManager').each(function(idx, item) {
        let id = parseInt($(item).attr('adminId'));
        if (id === adminId) {
            isExist = true;
            return;
        }
    });
    if (isExist) {
        return;
    }

    let tag = '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + adminId + '" username="' + username + '" adminName="' + name + '" style="margin-right: 5px;">' + name + '</button>';
    $('.managerSection').append(tag);
}

let getManagerIds = function() {
    let managerIds = [];
    $('.managerSection').find('.btnManager').each(function(idx, item) {
        let id = parseInt($(item).attr('adminId'));
        managerIds.push(id);
    });
    return managerIds;
}

let removeManager = function(e) {
    e.preventDefault();

    let $this = $(this),
        adminId = parseInt($this.attr('adminId')),
        createdById = parseInt(dto.createdById)
    ;
    if (adminId === createdById) {
        return;
    }
    twoBtnModal("담당자를 해제하시겠습니까?", function () {
        $this.remove();
    });
}

$(document).ready(onReady)
    .on('click', '.btnSave', realEstateSave)
    .on('click', '.btnUpdate', realEstateUpdate)
    .on('click', '.selected-button-checkbox-section button', toggleSelectButton)
    .on('click', '.selected-button-radio-section button', toggleSelectOneButton)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '.btnUsageCode', selectUsageCode)
    .on('change', '.usageCode', changeUsageCode)
    .on('click', '.toggleCustomer', changeCustomerInfo)
    .on('click', '.btnCustomerAdd', addCustomerInfo)
    .on('click', '.btnCustomerSave', addCustomerSave)
    .on('keydown', '#memoInput', addMemo)
    .on('click', '.btnLandUsageReload', landUsageInfoReload)
    .on('click', '.btnPblntReload', pblntInfoReload)
    .on('click', '.btnConstructReload', constructInfoReload)
    .on('click', '.btnConstructFloorReload', constructFloorInfoReload)
    .on('click', '.btnImageUpload', uploadImage)
    .on('click', '.remove-image', removeImage)
    .on('click', '.btnLandLoad', loadLandInfoById)
    .on('click', '.btnLandAdd', landInfoAdd)
    .on('click', '.removeLandBtn', removeLandBtn)
    .on('click', '.btnRemoveCustomerInfo', removeCustomerInfo)
    .on('click', '.btnRemoveMemo', removeMemo)
    .on('click', '.btnRemoveAllMemo', removeAllMemo)
    .on('click', '.btnShowDelMemo', showDelMemo)
    .on('click', '.btnProcessType', changeProcessStatus)
    .on('click', '.btnR', changeRStatus)
    .on('click', '.btnAB', changeABStatus)
    .on('click', '#btnLandModal', showLandModal)
    .on('click', '.btnApplyLand', applyLandInfo)
    .on('click', '.btnMultiImgUpload', multiImgUpload)
    .on('click', '.sub-image', imgModal)
    .on('click', '.btnRemoveSubImg', removeSubImg)
    .on('click', '.btnPrev', movePrevPage)
    .on('click', '.btnNext', moveNextPage)
    .on('blur', '.subGuaranteePrice', calculateGuaranteePrice)
    .on('blur', '.subRent', calculateRentPrice)
    .on('blur', '.subManagement', calculateManagementPrice)
    .on('blur', '.managementExpense', calculateManagementExpense)
    .on('blur', '.salePrice', calculateAveragePrice)
    .on('blur', '.depositPrice, .guaranteePrice, .rentMonth, .management', calculateAveragePrice)
    .on('click', '.btnOpenPrintPop', openPrintPop)
    .on('blur', '.calSumField', calculateSumField)
    .on('click', '.btnRowAdd', floorInfoRowAdd)
    .on('click', '.btnRowRemove', floorInfoRowRemove)
    .on('ifToggled', 'input[name="toggleAddress"]', toggleAddressType)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData)
    .on('click', '.btnCadastralModal', showCadastralModal)
    .on('blur', '.calAreaPyung', calculateAreaPyung)
    .on('blur', '.calAreaBcRate', calculateAreaBcRate)
    .on('blur', '.calculateAreaVlRate', calculateAreaVlRate)
    .on('ifToggled', '.checkNotiRead', checkNotiRead)
    .on('change', '.managerList', drawManager)
    .on('click', '.btnManagerRemove', removeManager)
;
