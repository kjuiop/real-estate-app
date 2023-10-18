let loadLandInfoList = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    let isLandInfo = dto.existLandInfo;
    if (isLandInfo === true) {
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
            console.log("load land info", result);
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
            $frm.find('input[name="pnu"]').val(landInfo.pnuStr);
            if (landInfo.commercialYn === 'Y') {
                $frm.find('input[name="commercialYn"]').iCheck('check');
            } else {
                $frm.find('input[name="commercialYn"]').iCheck('uncheck');
            }

            if (isLandInfo === true) {
                $frm.find('.btnLandSection').html('');
                let tag = '';
                $.each(landList, function(idx, item) {
                    tag += drawLandButton(item);
                    $frm.find('.btnLandSection').append(tag);
                    $frm.find('.btnLandSection .btnLandLoad').last().data('land-data', item);
                });

                calculateLandInfo();
            }
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let landInfoAdd = function(e) {
    e.preventDefault();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json(),
        pnu = $frmLand.find('input[name="pnu"]').val(),
        $section = $frmLand.find('.btnLandSection'),
        isAlready = false;

    params.pnu = pnu;
    params.pblntfPclnd = removeComma(params.pblntfPclnd)
    params.totalPblntfPclnd = removeComma(params.totalPblntfPclnd)

    console.log("landInfoAdd params", params)

    $section.find('.btnLandLoad').each(function(idx, item) {
        let alreadyPnu = $(item).attr('pnu');
        if (pnu === alreadyPnu) {
            twoBtnModal("이미 등록된 토지 정보입니다. 정보를 갱신하겠습니까?", function() {
                $(item).data('land-data', params);
            });
            isAlready = true;
            return false;
        }
    });

    if (isAlready === true) {
        return;
    }

    let tag = drawLandButton(params);
    $frmLand.find('.btnLandSection').append(tag);
    $frmLand.find('.btnLandSection .btnLandLoad').last().data('land-data', params);
    calculateLandInfo();
}

let calculateLandInfo = function() {

    let $frmLand = $('form[name="frmLandRegister"]'),
        $section = $frmLand.find('.btnLandSection');

    let totalLndpclArByPyung = 0;

    $section.find('.btnLandLoad').each(function(idx, item) {
        let landData = $(item).data('land-data');
        totalLndpclArByPyung += parseFloat(landData.lndpclArByPyung);
    });

    $frmLand.find('input[name="totalLndpclArByPyung"]').val(totalLndpclArByPyung);
    $frmLand.find('.pyung').html(totalLndpclArByPyung);
}

let drawLandButton = function(data) {
    let tag = '';

    if (checkNullOrEmptyValue(data.landId)) {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="' + data.landId + '">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeBtn" aria-hidden="true"></i></button>'
    } else {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeBtn" aria-hidden="true"></i></button>'
    }

    return tag;
}


let loadLandInfoById = function(e) {
    e.preventDefault();

    let $this = $(this),
        landInfo = $this.data('land-data');

    if (!checkNullOrEmptyValue(landInfo)) {
        return;
    }
    console.log("land data", landInfo);

    let $frm = $('form[name="frmLandRegister"]');
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
    $frm.find('input[name="pnu"]').val(landInfo.pnu);
}

let landInfoSave = function(e) {
    e.preventDefault();

    let isModify = dto.existLandInfo;
    let $frmBasic = $('form[name="frmBasicRegister"]'),
        httpMethod = isModify ? 'put' : 'post',
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmLand = $('form[name="frmLandRegister"]'),
        formData = serializeObject({form:$frmLand[0]}).json();

    let commercialYn = $frmLand.find('input[name="commercialYn"]').is(":checked") ? "Y" : "N";

    let $section = $frmLand.find('.btnLandSection'),
        addBtnLength = $section.find('.btnLandLoad').length;

    if (addBtnLength === 0) {
        twoBtnModal('저장하려는 토지 정보를 추가해주세요.');
        return;
    }

    if (!checkNullOrEmptyValue(formData.address)) {
        twoBtnModal('주소를 입력해주세요.');
        return;
    }

    let landInfoList = [];
    $frmLand.find('.btnLandSection .btnLandLoad').each(function (idx, item) {
        let landData = $(item).data('land-data');
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclAr = removeComma(landData.lndpclAr)
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclArByPyung = removeComma(landData.lndpclArByPyung)
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.pblntfPclnd = removeComma(landData.pblntfPclnd)
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.totalPblntfPclnd = removeComma(landData.totalPblntfPclnd)
        }
        landData.realEstateId = dto.realEstateId;
        landInfoList.push(landData);
    });

    let params = {
        "legalCode" : detailParams.legalCode,
        "landType" : detailParams.landType,
        "bun" : detailParams.bun,
        "ji" : detailParams.ji,
        "address" : dto.address,
        "commercialYn" : commercialYn,
        "landInfoList" : landInfoList,
        "realEstateId" : dto.realEstateId
    }

    console.log("land params", params);


    $.ajax({
        url: "/real-estate/land",
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

let assembleLandParams = function() {

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

    return landInfoList;
}

let searchAddress = function(e) {
    e.preventDefault();

    let $modal = $('#landModal');
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
            $modal.find(`input[name="address"]`).val(data.address);
            loadKakaoModalMap(data.address)
        }
    }).open();
}

let showLandModal = function(e) {
    e.preventDefault();

    $('#landModal').modal('show');
}


let applyLandInfo = function(e) {
    e.preventDefault();

    twoBtnModal("검색하신 매물정보를 적용하겠습니까?", function() {
        $('.btnModalClose').trigger('click');
    });
}
