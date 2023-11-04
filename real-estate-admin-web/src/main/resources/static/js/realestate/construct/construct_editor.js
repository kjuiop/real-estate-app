let loadConstructInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;

    if (dto.existConstructInfo === true) {
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
            let constructInfo = result.data;
            console.log("constructInfo result", constructInfo);

            let $basicFrm = $('form[name="frmBasicRegister"]'),
                $buildingName = $basicFrm.find('input[name="buildingName"]');

            if (checkNullOrEmptyValue(constructInfo.bldNm)) {
                $buildingName.val(constructInfo.bldNm);
            }

            let $frm = $('form[name="frmConstructRegister"]');
            $frm.find('.mainPurpsCdNm').val(constructInfo.mainPurpsCdNm);
            $frm.find('.etcPurps').val(constructInfo.etcPurps);
            $frm.find('.strctCdNm').val(constructInfo.strctCdNm);
            $frm.find('.useAprDay').val(constructInfo.useAprDay);
            $frm.find('.platArea').val(constructInfo.platArea);
            $frm.find('.platAreaByPyung').val(constructInfo.platAreaByPyung);
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

            if (constructInfo.illegalConstructYn === 'Y') {
                $frm.find('input[name="illegalConstructYn"]').iCheck('check');
            } else {
                $frm.find('input[name="illegalConstructYn"]').iCheck('uncheck');
            }

            // calculateConstructInfo(constructInfo);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadConstructFloorInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    if (dto.existPriceInfo === true) {
        url = "/real-estate/construct/floor/" + dto.realEstateId;
    } else {
        url = "/real-estate/construct/floor/ajax/public-data"
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
            console.log("floor result", result);
            let floorData = result.data;
            let $tbody = $('.construct-floor-table tbody'),
                $tfoot = $('.construct-floor-table tfoot');
            if (floorData.length === 0) {
                let tag = '';
                tag += '<tr>';
                tag += '<td class="text-center" colspan="7">해당 건물의 층별 정보가 없습니다.</td>';
                tag += '</tr>';
                $tbody.html(tag);
                $tfoot.addClass('hidden');
                return;
            }

            let tag = drawConstructFloorInfo(floorData);
            $tbody.html(tag);
            $tfoot.removeClass('hidden');
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let drawConstructFloorInfo = function(data) {
    let tag = '';
    $.each(data, function(idx, item) {
        tag += '<tr class="floor-unit">';
        tag += '<td class="center-text padding-8 flrNoNm" flrNo="' + item.flrNo + '" data="' + item.flrNoNm + '">' + item.flrNoNm + '</td>';
        tag += '<td class="center-text padding-8 area" data="' + item.area + '">' + item.area + '㎡</td>';
        tag += '<td class="center-text padding-6 etcPurps" data="' + item.etcPurps + '">' + item.etcPurps + '</td>';
        tag += '<td class="center-text padding-6"><input type="text" class="form-control form-control-sm companyName" value="' + convertNullOrEmptyValue(item.companyName) + '" name="companyName" style="min-width: 100px;"/></td>';
        if (item.guaranteePrice > 0) {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subGuaranteePrice" value="' + item.guaranteePrice + '" name="guaranteePrice" style="min-width: 100px;"/></td>';
        } else {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subGuaranteePrice" value="0" name="guaranteePrice" style="min-width: 100px;"/></td>';
        }

        if (item.rent > 0) {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subRent" value="' + item.rent + '" name="rent" style="min-width: 100px;"/></td>';
        } else {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subRent" value="0" name="rent" style="min-width: 100px;"/></td>';
        }

        if (item.management > 0) {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subManagement" value="' + item.management + '" name="management" style="min-width: 100px;"/></td>';
        } else {
            tag += '<td class="center-text padding-6"><input type="number" class="form-control form-control-sm subManagement" value="0" name="management" style="min-width: 100px;"/></td>';
        }
        tag += '</tr>';
    })
    return tag;
}

let constructInfoSave = function(e) {
    e.preventDefault();

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let isModify = dto.existConstructInfo;
    let $frmConstruct = $('form[name="frmConstructRegister"]'),
        httpMethod = isModify ? 'put' : 'post',
        params = serializeObject({form:$frmConstruct[0]}).json();

    let illegalConstructYn = $frmConstruct.find('input[name="illegalConstructYn"]').is(":checked") ? "Y" : "N";

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;
    params.realEstateId = dto.realEstateId;
    params.illegalConstructYn = illegalConstructYn;

    console.log("params", params);


    $.ajax({
        url: "/real-estate/construct",
        method: 'post',
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

let assembleFloorParams = function() {

    let floorInfoList = [];
    $('.construct-floor-table tbody tr').each(function(idx, item) {
        let param = {
            "flrNo" : $(item).find('.flrNoNm').attr('flrNo'),
            "flrNoNm" : $(item).find('.flrNoNm').attr('data'),
            "area" : $(item).find('.area').attr('data'),
            "etcPurps" : $(item).find('.etcPurps').attr('data'),
            "companyName" : $(item).find('input[name="companyName"]').val(),
            "guaranteePrice" : $(item).find('input[name="guaranteePrice"]').val(),
            "rent" : $(item).find('input[name="rent"]').val(),
            "management" : $(item).find('input[name="management"]').val(),
        }
        floorInfoList.push(param);
    });

    return floorInfoList;
}

// temp
let calculateConstructInfo = function(constructInfo) {

    let $frm = $('form[name="frmConstructRegister"]');

    if (!checkNullOrEmptyValue(constructInfo)) {
        return;
    }

    let platAreaPyung = $frm.find('.platAreaByPyung').val();

    let platArea = constructInfo.platArea;
    if (platArea > 0 && (platAreaPyung === 0 || !checkNullOrEmptyValue(platAreaPyung))) {
        platAreaPyung = platArea / 3.305785;
        platAreaPyung = Math.round(platAreaPyung * 100) / 100;
        $frm.find('.platAreaByPyung').val(platAreaPyung);
    }

    let archArea = constructInfo.archArea;
    let bcRat = constructInfo.bcRat;


    console.log("constructInfo : ", constructInfo)
    if (bcRat === 0 && archArea > 0 && platArea > 0) {
        bcRat = (archArea / platArea) * 100;
        bcRat = Math.round(bcRat * 100) / 100;
        $frm.find('.bcRat').val(bcRat);
    }

    let vlRat = constructInfo.vlRat;
    let totArea = constructInfo.totArea;
    if (vlRat === 0 && totArea > 0 && platArea > 0) {
        vlRat = (totArea / platArea) * 100;
        vlRat = Math.round(vlRat * 100) / 100;
        $frm.find('.vlRat').val(vlRat);
    }

}
