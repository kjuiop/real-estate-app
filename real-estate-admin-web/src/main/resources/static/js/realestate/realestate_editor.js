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
    loadImageInfo();
    $('#customerInfoSection').html(drawUnitCustomerInfo("CUSTOMER", null));
    loadPrintInfo();
    onlyNumberKeyEvent({className: "only-number"});
    // loadMoveOtherPage();
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


    let subImages = [];
    let $imgSubImgSection = $('.image-sub-section');
    $imgSubImgSection.find('.sub-img-unit').each(function(idx, item) {
       let image = {
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

    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();
    params.priceInfo.totalLndpclArByPyung = $('input[name="totalLndpclArByPyung"]').val();
    params.priceInfo.totArea = $('input[name="totArea"]').val();

    let floorInfoList = assembleFloorParams();
    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    let customerInfoList = assembleCustomerParam();
    params.customerInfoList = customerInfoList;

    let $frmPrint = $('form[name="frmPrintImage"]');
    let printInfo = {
        "propertyImgUrl": $frmPrint.find('#propertyImgurl').find('img').attr('src'),
        "buildingImgUrl": $frmPrint.find('#buildingImgUrl').find('img').attr('src'),
        "locationImgUrl": $frmPrint.find('#locationImgUrl').find('img').attr('src'),
        "landDecreeImgUrl": $frmPrint.find('#landDecreeImgUrl').find('img').attr('src'),
        "developPlanImgUrl": $frmPrint.find('#developPlanImgUrl').find('img').attr('src'),
    }
    params.printInfo = printInfo;

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

    let subImages = [];
    let $imgSubImgSection = $('.image-sub-section');
    $imgSubImgSection.find('.sub-img-unit').each(function(idx, item) {
        let image = {
            "fullPath" : $(item).find('.sub-image').attr('src'),
        }
        subImages.push(image);
    });
    params.subImages = subImages;

    let landInfoList = assembleLandParams();
    params.landInfoList = landInfoList;

    params.priceInfo = serializeObject({form:$frmPrice[0]}).json();
    params.priceInfo.totalLndpclArByPyung = $('input[name="totalLndpclArByPyung"]').val();
    params.priceInfo.totArea = $('input[name="totArea"]').val();

    let floorInfoList = assembleFloorParams();
    params.floorInfoList = floorInfoList;

    params.constructInfo = serializeObject({form:$frmConstruct[0]}).json();
    params.constructInfo.illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    let customerInfoList = assembleCustomerParam();
    params.customerInfoList = customerInfoList;

    let $frmPrint = $('form[name="frmPrintImage"]');
    let printInfo = {
        "propertyImgUrl": $frmPrint.find('#propertyImgUrl .thumbnailInfo').find('img').attr('src'),
        "buildingImgUrl": $frmPrint.find('#buildingImgUrl .thumbnailInfo').find('img').attr('src'),
        "locationImgUrl": $frmPrint.find('#locationImgUrl .thumbnailInfo').find('img').attr('src'),
        "landDecreeImgUrl": $frmPrint.find('#landDecreeImgUrl .thumbnailInfo').find('img').attr('src'),
        "developPlanImgUrl": $frmPrint.find('#developPlanImgUrl .thumbnailInfo').find('img').attr('src'),
    }
    params.printInfo = printInfo;

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

let drawBtnUsageCode = function(categories) {

    let tags = "";
    $.each(categories, function (idx, item) {
        if (dto.usageCdId === item.id || (dto.usageType != null && dto.usageType.id === item.id)) {
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

    let _width = 1300;
    let _height = 1000;
    let _left = Math.ceil((window.screen.width - _width) / 2);
    let _top = Math.ceil((window.screen.height - _height) / 2);


    let options = 'top=10, left=10, width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top + ', status=no, menubar=no, toolbar=no, resizable=no';
    window.open('/real-estate/print', 'print-popup', options);
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

$(document).ready(onReady)
    .on('click', '.btnSave', realEstateSave)
    .on('click', '.btnUpdate', realEstateUpdate)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '.btnUsageCode', selectUsageCode)
    .on('click', '.toggleCustomer', changeCustomerInfo)
    .on('click', '.btnCustomerAdd', addCustomerInfo)
    .on('click', '.btnCustomerSave', addCustomerSave)
    .on('keydown', '#memoInput', addMemo)
    .on('click', '.btnImageUpload', uploadImage)
    .on('click', '.remove-image', removeImage)
    .on('click', '.btnLandLoad', loadLandInfoById)
    .on('click', '.btnLandAdd', landInfoAdd)
    .on('click', '.removeLandBtn', removeLandBtn)
    .on('click', '.btnRemoveCustomerInfo', removeCustomerInfo)
    .on('click', '.btnRemoveMemo', removeMemo)
    .on('click', '.btnRemoveAllMemo', removeAllMemo)
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
    .on('click', '.btnOpenPrintPop', openPrintPop)
;
