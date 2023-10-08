
let landInfoAdd = function(e) {
    e.preventDefault();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json(),
        pnu = $frmLand.find('input[name="pnu"]').val(),
        $section = $frmLand.find('.btnLandSection'),
        isAlready = false;

    params.pnu = pnu;

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

    console.log("land data", landInfo);

    let $frm = $('form[name="frmLandRegister"]');
    $frm.find('.area').text(landInfo.lndpclAr);
    $frm.find('.pyung').text(landInfo.lndpclArByPyung);
    $frm.find('.lndpclAr').val(landInfo.lndpclAr);
    $frm.find('.lndpclArByPyung').val(landInfo.lndpclArByPyung);
    $frm.find('.pblntfPclnd').val(landInfo.pblntfPclnd);
    $frm.find('.totalPblntfPclnd').val(landInfo.totalPblntfPclnd);
    $frm.find('.lndcgrCodeNm').val(landInfo.lndcgrCodeNm);
    $frm.find('.prposArea1Nm').val(landInfo.prposArea1Nm);
    $frm.find('.ladUseSittnNm').val(landInfo.ladUseSittnNm);
    $frm.find('.roadSideCodeNm').val(landInfo.roadSideCodeNm);
    $frm.find('.tpgrphHgCodeNm').val(landInfo.tpgrphHgCodeNm);
    $frm.find('.tpgrphFrmCodeNm').val(landInfo.tpgrphFrmCodeNm);
    $frm.find('input[name="pnu"]').val(landInfo.pnu);
}

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
            console.log("load database land info", result);
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
            $frm.find('input[name="pnu"]').val(landInfo.pnu);


            if (isLandInfo === true) {
                $frm.find('.btnLandSection').html('');
                let tag = '';
                $.each(landList, function(idx, item) {
                    tag += drawLandButton(item);
                    $frm.find('.btnLandSection').append(tag);
                    $frm.find('.btnLandSection .btnLandLoad').last().data('land-data', item);
                });
            }
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let landInfoSave = function(e) {
    e.preventDefault();

    let isModify = dto.realEstateId != null;
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
        landData.lndpclAr = landData.lndpclAr.replaceAll(',', '');
        landData.lndpclArByPyung = landData.lndpclArByPyung.replaceAll(',', '');
        landData.pblntfPclnd = landData.pblntfPclnd.replaceAll(',', '');
        landData.totalPblntfPclnd = landData.totalPblntfPclnd.replaceAll(',', '');
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
        "landInfoList" : landInfoList
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
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}
