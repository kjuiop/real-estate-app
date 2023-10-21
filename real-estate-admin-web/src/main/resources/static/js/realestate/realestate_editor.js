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
            let image = res.data[0];
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

let multiImgUpload = function(e) {
    e.preventDefault();


    documentUpload({
        multiple: true,
        accept: '.jpg, .png, .gif',
        sizeCheck: false,
        usageType: `RealEstate`,
        fileType: `Image`,
        callback: function (res) {
            console.log("res", res);
            let attachments = res.data;
            let $imagePanel = $('.image-sub-section');
            $.each(attachments, function(idx, item) {
                let tag = drawSubImageTag(idx, item.fullPath);
                $imagePanel.append(tag);
            });

            $( ".sortable-section" ).sortable().disableSelection();
        }
    });
}

let drawSubImageTag = function(idx, fullPath) {
    let tag = '';
    tag += '<div class="display-inline-block sub-img-unit">';
    tag += '<a href="#"><img id="sub-image-' + idx + '" src="' + fullPath + '" class="col-sm-12 no-left-padding thumbnailInfo sub-image" style="cursor: pointer; width: 50px; height: 50px;"/></a>';
    tag += '</div>';
    return tag;
}

let removeSubImg = function(e) {
    e.preventDefault();

    let $imgModal = $('#image-modal');
    let targetId = $imgModal.find('#targetSubImg').val();
    let targetSubImgId = '#' + targetId;
    $(targetSubImgId).parents('.sub-img-unit').remove();
    $imgModal.find('.close').trigger('click');
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
;
