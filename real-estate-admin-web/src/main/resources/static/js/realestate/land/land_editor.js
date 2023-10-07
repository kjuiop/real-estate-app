
let landInfoAdd = function(e) {
    e.preventDefault();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json();

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

    let $this = $(this);
    console.log("land-data", $this.data('land-data'));

    let landId = $(this).attr('landId');
    if (!checkNullOrEmptyValue(landId)) {
        return;
    }

    alert("in");
}

let loadLandInfoList = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;

    if (dto.existLandInfo === true) {
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
            $frm.find('input[name="pnu"]').val(landInfo.pnu);
            // let tag = drawLandButton(landList);
            // $frm.find('.btnLandSection').html(tag);
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
        params = serializeObject({form:$frmLand[0]}).json();

    if (!checkNullOrEmptyValue(params.address)) {
        twoBtnModal('주소를 입력해주세요.');
        return;
    }

    $frmLand.find('.btnLandSection .btnLandLoad').each(function (idx, item) {
        let landData = $(item).attr('data-param');
        console.log("landData : ", landData);
    });


    // params.legalCode = detailParams.legalCode;
    // params.landType = detailParams.landType;
    // params.bun = detailParams.bun;
    // params.ji = detailParams.ji;
    //
    // params.lndpclAr = params.lndpclAr.replaceAll(',', '');
    // params.lndpclArByPyung = params.lndpclArByPyung.replaceAll(',', '');
    // params.pblntfPclnd = params.pblntfPclnd.replaceAll(',', '');
    // params.totalPblntfPclnd = params.totalPblntfPclnd.replaceAll(',', '');
    // params.realEstateId = dto.realEstateId;
    //
    // $.ajax({
    //     url: "/real-estate/land",
    //     method: httpMethod,
    //     type: "json",
    //     contentType: "application/json",
    //     data: JSON.stringify(params),
    //     success: function (result) {
    //         console.log("result : ", result);
    //         let message = '정상적으로 저장되었습니다.';
    //         twoBtnModal(message, function() {
    //             location.href = '/real-estate/' + result.data + '/edit';
    //         });
    //     },
    //     error:function(error){
    //         ajaxErrorFieldByText(error);
    //     }
    // });
}
