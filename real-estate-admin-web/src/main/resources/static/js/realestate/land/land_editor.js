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

            settingLandInfo(landInfo);

            if (isLandInfo === true) {
                $frm.find('.btnLandSection').html('');
                $.each(landList, function(idx, item) {
                    let tag = drawLandButton(item);
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

let settingLandInfo = function(landInfo) {
    let $frm = $('form[name="frmLandRegister"]');
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
    $frm.find('.roadWidth').val(landInfo.roadWidth)
    $frm.find('input[name="pnu"]').val(landInfo.pnuStr);
    if (landInfo.commercialYn === 'Y') {
        $frm.find('input[name="commercialYn"]').iCheck('check');
    } else {
        $frm.find('input[name="commercialYn"]').iCheck('uncheck');
    }
}

let landInfoAdd = function(e) {
    e.preventDefault();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json(),
        pnu = $frmLand.find('input[name="pnu"]').val(),
        $section = $frmLand.find('.btnLandSection'),
        isAlready = false;

    params.pnu = pnu;
    params.pblntfPclnd = removeComma(params.pblntfPclnd);
    params.totalPblntfPclnd = removeComma(params.totalPblntfPclnd);
    params.address = $frmLand.find('input[name="address"]').val();

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
    let totalLndpcl = 0;
    let landLength = $section.find('.btnLandLoad').length;

    $section.find('.btnLandLoad').each(function(idx, item) {
        let landData = $(item).data('land-data');
        totalLndpclArByPyung += parseFloat(landData.lndpclArByPyung);
        totalLndpcl += parseFloat(landData.lndpclAr);
    });


    console.log("before calculate totalLndpcl : ", totalLndpcl)
    console.log("before calculate totalLndpclArByPyung : ", totalLndpclArByPyung)
    totalLndpcl = Math.floor(totalLndpcl * 100) / 100;
    totalLndpclArByPyung = Math.floor(totalLndpclArByPyung * 100) / 100;

    console.log("after calculate totalLndpcl : ", totalLndpcl)
    console.log("after calculate totalLndpclArByPyung : ", totalLndpclArByPyung)

    $frmLand.find('input[name="totalLndpclAr"]').val(totalLndpcl);
    $frmLand.find('input[name="totalLndpclArByPyung"]').val(totalLndpclArByPyung);
    $frmLand.find('.pyung').html(totalLndpclArByPyung);
    $frmLand.find('.landSize').html(landLength);
    $frmLand.find('.area').html(totalLndpcl);
}

let drawLandButton = function(data) {
    let tag = '';

    if (checkNullOrEmptyValue(data.landId)) {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="' + data.landId + '">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeLandBtn" aria-hidden="true"></i></button>'
    } else {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeLandBtn" aria-hidden="true"></i></button>'
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
    $frm.find('.roadWidth').val(landInfo.roadWidth);
    $frm.find('input[name="pnu"]').val(landInfo.pnu);
}

let assembleLandParams = function() {

    let landInfoList = [];

    let $frmLand = $('form[name="frmLandRegister"]'),
        commercialYn = $frmLand.find('input[name="commercialYn"]').is(":checked") ? "Y" : "N";

    let $section = $frmLand.find('.btnLandSection'),
        addBtnLength = $section.find('.btnLandLoad').length;

    if (addBtnLength === 0) {
        twoBtnModal('저장하려는 토지 정보를 추가해주세요.');
        return landInfoList;
    }


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
        if (typeof landData.totalPblntfPclnd === 'string') {
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

    let $landModal = $('#landModal');
    $landModal.find('input[name="address"]').val('');
    $landModal.find('input[name="bun"]').val('');
    $landModal.find('input[name="ji"]').val('');
    $landModal.find('select[name="landType"]').val('general');
    $landModal.find('input[name="bCode"]').val('');
    $landModal.find('input[name="hCode"]').val('');

    let tag = '';
    tag += '<span class="margin-bottom-10">지도</span>';
    tag += '<p style="font-size: 12px; margin-top: 20px; color: #7987a1;">';
    tag +=     '검색버튼을 클릭해서';
    tag +=     '<br/>';
    tag +=     '주소를 입력해주세요.';
    tag += '</p>';
    $landModal.find('#modalMap').html(tag);
    $landModal.modal('show');
}


let applyLandInfo = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmLandRegister"]'),
        $modal = $('#landModal'),
        address = $('#landModal').find('input[name="address"]').val();

    let url = "/real-estate/land/ajax/public-data"
        + "?legalCode=" + $modal.find('input[name="bCode"]').val()
        + "&landType=" + $modal.find('select[name="landType"] option:selected').val()
        + "&bun=" + $modal.find('input[name="bun"]').val()
        + "&ji=" + $modal.find('input[name="ji"]').val()
    ;

    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("load land info", result);
            let landList = result.data,
                landInfo = landList[0];
            $frm.find('input[name="address"]').val(address);
            $frm.find('input[name="pnu"]').val(landInfo.pnu);
            settingLandInfo(landInfo);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });

    $('.btnModalClose').trigger('click');
}

let removeLandBtn = function(e) {
    e.preventDefault();
    $(this).parents('button').remove();
    calculateLandInfo();
}