let onReady = function() {
    console.log("dto", dto);
    loadBasicInfo();
    loadLandInfo();
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

    let url = "/real-estate/land/ajax/public-data"
        + "?legalCode=" + dto.legalCode
        + "&landType=" + dto.landType
        + "&bun=" + dto.bun
        + "&ji=" + dto.ji

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

let basicLandSave = function(e) {
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

    console.log("detailParams", detailParams);

    console.log("params", params);

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
    .on('click', '.btnLandSave', basicLandSave);
