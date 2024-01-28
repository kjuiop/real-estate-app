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
            if (!checkNullOrEmptyValue(constructInfo)) {
                return;
            }
            console.log("constructInfo result", constructInfo);
            settingPublicApi(constructInfo);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let settingPublicApi = function(constructInfo) {
    let $basicFrm = $('form[name="frmBasicRegister"]'),
        $buildingName = $basicFrm.find('input[name="buildingName"]');

    if (checkNullOrEmptyValue(constructInfo.bldNm)) {
        $buildingName.val(constructInfo.bldNm);
    }

    let $frm = $('form[name="frmConstructRegister"]');
    $frm.find('input[name="constructId"]').val(constructInfo.constructId);
    $frm.find('.mainPurpsCdNm').val(constructInfo.mainPurpsCdNm);
    $frm.find('.etcPurps').val(constructInfo.etcPurps);
    $frm.find('.strctCdNm').val(constructInfo.strctCdNm);
    $frm.find('.useAprDate').val(constructInfo.useAprDate);
    $frm.find('.platArea').val(constructInfo.platArea);
    $frm.find('.platAreaByPyung').val(constructInfo.platAreaByPyung);
    // $frm.find('.hhldCnt').val(constructInfo.hhldCnt);
    $frm.find('.houseHoldName').val(constructInfo.houseHoldName);
    $frm.find('.archArea').val(constructInfo.archArea);
    $frm.find('input[name="archAreaByPyung"]').val(constructInfo.archAreaByPyung);
    $frm.find('.bcRat').val(constructInfo.bcRat);
    $frm.find('.totArea').val(constructInfo.totArea);
    $frm.find('.totAreaByPyung').val(constructInfo.totAreaByPyung);
    $frm.find('.vlRat').val(constructInfo.vlRat);
    $frm.find('.grndFlrCnt').val(constructInfo.grndFlrCnt);
    $frm.find('.ugrndFlrCnt').val(constructInfo.ugrndFlrCnt);
    $frm.find('.rideUseElvtCnt').val(constructInfo.rideUseElvtCnt);
    $frm.find('.emgenUseElvtCnt').val(constructInfo.emgenUseElvtCnt);
    $frm.find('.indrAutoUtcnt').val(constructInfo.indrAutoUtcnt);
    $frm.find('.oudrAutoUtcnt').val(constructInfo.oudrAutoUtcnt);
    $frm.find('.indrMechUtcnt').val(constructInfo.indrMechUtcnt);
    $frm.find('.oudrMechUtcnt').val(constructInfo.oudrMechUtcnt);
    $frm.find('input[name="vlRatEstmTotArea"]').val(constructInfo.vlRatEstmTotArea);
    $frm.find('input[name="vlRatEstmTotAreaByPyung"]').val(constructInfo.vlRatEstmTotAreaByPyung);
    $frm.find('input[name="heit"]').val(constructInfo.heit);
    $frm.find('input[name="responseCode"]').val(convertNullOrEmptyValue(constructInfo.responseCode));
    $frm.find('input[name="lastCurlApiAt"]').val(convertNullOrEmptyValue(constructInfo.lastCurlApiAt));

    if (constructInfo.illegalConstructYn === 'Y') {
        $frm.find('input[name="illegalConstructYn"]').iCheck('check');
    } else {
        $frm.find('input[name="illegalConstructYn"]').iCheck('uncheck');
    }

    // calculateConstructInfo(constructInfo);
}

let loadConstructFloorInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    if (dto.existFloorInfo === true) {
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

            if (!checkNullOrEmptyValue(floorData)) {
                $tfoot.removeClass('hidden');
                $('.btnRowAdd').trigger('click');
                return;
            }

            if (floorData.length === 0) {
                let tag = drawEmptyFloorTable();
                $tbody.html(tag);
                $tfoot.addClass('hidden');
                return;
            }

            let tag = drawConstructFloorInfo(floorData);
            $tbody.html(tag);
            $tfoot.removeClass('hidden');

            calculateFloorInfo();
            $(".construct-floor-table tbody").sortable().disableSelection();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let constructInfoReload = function(e) {
    e.preventDefault();

    let url = "/real-estate/construct/ajax/public-data"
        + "?legalCode=" + dto.legalCode
        + "&landType=" + dto.landType
        + "&bun=" + dto.bun
        + "&ji=" + dto.ji
    ;

    twoBtnModal("공공데이터를 불러오겠습니까?", function () {
        $.ajax({
            url: url,
            method: "get",
            type: "json",
            contentType: "application/json",
            success: function(result) {
                let constructInfo = result.data;
                if (!checkNullOrEmptyValue(constructInfo)) {
                    return;
                }
                console.log("constructInfo result", constructInfo);
                settingPublicApi(constructInfo);
            },
            error: function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });

}

let constructFloorInfoReload = function(e) {
    e.preventDefault();

    let url = "/real-estate/construct/floor/ajax/public-data"
        + "?legalCode=" + dto.legalCode
        + "&landType=" + dto.landType
        + "&bun=" + dto.bun
        + "&ji=" + dto.ji
    ;

    twoBtnModal("공공데이터를 불러오겠습니까?", function () {

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

                if (!checkNullOrEmptyValue(floorData)) {
                    $tfoot.removeClass('hidden');
                    $('.btnRowAdd').trigger('click');
                    return;
                }

                if (floorData.length === 0) {
                    let tag = drawEmptyFloorTable();
                    $tbody.html(tag);
                    $tfoot.addClass('hidden');
                    return;
                }

                let tag = drawConstructFloorInfo(floorData);
                $tbody.html(tag);
                $tfoot.removeClass('hidden');

                calculateFloorInfo();
                $(".construct-floor-table tbody").sortable().disableSelection();
            },
            error: function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}


let drawConstructFloorInfo = function(data) {
    let tag = '';
    $.each(data, function(idx, item) {
        tag += drawConstructFloorRow(item);
    })
    return tag;
}

let drawConstructFloorRow = function(item) {
    let tag = '';
    tag += '<tr class="floor-unit">';
    tag += '<td class="center-text padding-8 flrNo floorId" floorId="' + convertNullOrEmptyValue(item.floorId) + '" flrNo="' + item.flrNo + '" data="' + item.flrNoNm + '">';
    tag += '<input type="hidden" name="responseCode" value="' + convertNullOrEmptyValue(item.responseCode) + '" />';
    tag += '<input type="hidden" name="lastCurlApiAt" value="' + convertNullOrEmptyValue(item.lastCurlApiAt) + '" />';
    tag += '<input type="text" class="form-control form-control-sm flrNoNm" value="' + convertNullOrEmptyValue(item.flrNoNm) + '" name="flrNoNm" style="min-width:70px;"/>';
    tag += '</td>';
    tag += '<td class="center-text padding-8"><input type="text" class="form-control form-control-sm roomName" value="' + convertNullOrEmptyValue(item.roomName) + '" name="roomName" style="min-width: 70px;"/></td>';
    tag += '<td class="center-text padding-6" data="' + item.etcPurps + '"><input type="text" class="form-control form-control-sm etcPurps" value="' + convertNullOrEmptyValue(item.etcPurps) + '" name="etcPurps" style="min-width: 190px;"/></td>';
    tag += '<td class="center-text padding-6"><input type="text" class="form-control form-control-sm companyName" value="' + convertNullOrEmptyValue(item.companyName) + '" name="companyName" style="min-width: 130px;"/></td>';
    tag += '<td class="center-text padding-8 area" data="' + item.area + '">' + item.area + '<span style="font-size: 15px; padding: 3px; min-width: 70px;">㎡</span></td>';
    tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="text" class="form-control form-control-sm lndpclAr calSumField" value="' + convertNullOrEmptyValue(item.lndpclAr) + '" name="lndpclAr"  sum="totalLndpclAr" unit="㎡" style="min-width: 85px;"/><span style="font-size: 15px; padding: 3px;">㎡</span></div></td>';
    tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="text" class="form-control form-control-sm lndpclArByPyung calSumField" value="' +  convertNullOrEmptyValue(item.lndpclArByPyung) + '" name="lndpclArByPyung" sum="totalLndpclArByPyung" unit="평" style="min-width: 50px;"/><span style="font-size: 14px; padding: 3px;">평</span></div></td>';
    if (item.guaranteePrice > 0) {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subGuaranteePrice calSumField" value="' + item.guaranteePrice + '" name="guaranteePrice" sum="totalSubGuaranteePrice" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    } else {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subGuaranteePrice calSumField" value="0" name="guaranteePrice" sum="totalSubGuaranteePrice" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    }

    if (item.rent > 0) {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subRent calSumField" value="' + item.rent + '" name="rent" sum="totalSubRent" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    } else {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subRent calSumField" value="0" name="rent" sum="totalSubRent" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    }

    if (item.management > 0) {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subManagement calSumField" value="' + item.management + '" name="management" sum="totalManagement" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    } else {
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="number" class="form-control form-control-sm subManagement calSumField" value="0" name="management" sum="totalManagement" unit="만원" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">만원</span></div></td>';
    }
    tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="text" class="form-control form-control-sm term" value="' + convertNullOrEmptyValue(item.termStartDate) + '" name="termStartDate" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">~</span><input type="text" class="form-control form-control-sm term" value="' + convertNullOrEmptyValue(item.termEndDate) + '" name="termEndDate" style="min-width: 100px;"/></div></td>';
    tag += '<td class="center-text padding-6"><input type="text" class="form-control form-control-sm etcInfo" value="' + convertNullOrEmptyValue(item.etcInfo) + '" name="etcInfo" style="min-width: 100px;"/></td>';
    tag += '<td class="center-text padding-8"><button class="btn btn-sm btn-danger btnRowRemove">삭제</button></td>'
    tag += '</tr>';
    return tag;
}

let calculateFloorInfo = function() {
    let $table = $('.construct-floor-table');
    let totalArea = 0, totalLndpclAr = 0, totalLndpclArByPyung = 0,
        totalSubGuaranteePrice = 0, totalSubRent = 0, totalManagement = 0;
    $('.construct-floor-table tbody tr').each(function(idx, item) {
        let area = $(item).find('.area').attr('data');
        console.log("type check ", typeof area)
        if (!isNaN(area)) {
            totalArea += Number(area);
        }
        let lndpclAr = $(item).find('input[name="lndpclAr"]').val();
        if (!isNaN(lndpclAr)) {
            totalLndpclAr += Number(lndpclAr);
        }
        let lndpclArByPyung = $(item).find('input[name="lndpclArByPyung"]').val();
        if (!isNaN(lndpclArByPyung)) {
            totalLndpclArByPyung += Number(lndpclArByPyung);
        }
        let guaranteePrice = $(item).find('input[name="guaranteePrice"]').val();
        if (!isNaN(guaranteePrice)) {
            totalSubGuaranteePrice += Number(guaranteePrice);
        }
        let rent = $(item).find('input[name="rent"]').val();
        if (!isNaN(rent)) {
            totalSubRent += Number(rent);
        }
        let management = $(item).find('input[name="management"]').val();
        if (!isNaN(management)) {
            totalManagement += Number(management);
        }
    });
    if (totalArea > 0) {
        totalArea = totalArea.toFixed(2);
    }
    if (totalLndpclAr > 0) {
        totalLndpclAr = totalLndpclAr.toFixed(2);
    }
    if (totalLndpclArByPyung > 0) {
        totalLndpclArByPyung = totalLndpclArByPyung.toFixed(2);
    }

    $table.find('.totalArea').html(totalArea + '<span style="font-size: 15px; padding: 3px;">㎡</span>');
    $table.find('.totalLndpclAr').html(totalLndpclAr + '<span style="font-size: 15px; padding: 3px;">㎡</span>');
    $table.find('.totalLndpclArByPyung').html(totalLndpclArByPyung + '<span style="font-size: 15px; padding: 3px;">평</span>');
    $table.find('.totalSubGuaranteePrice').html(totalSubGuaranteePrice + '<span style="font-size: 15px; padding: 3px;">만원</span>');
    $table.find('.totalSubRent').html(totalSubRent + '<span style="font-size: 15px; padding: 3px;">만원</span>');
    $table.find('.totalManagement').html(totalManagement + '<span style="font-size: 15px; padding: 3px;">만원</span>');
}

let assembleFloorParams = function() {

    let floorInfoList = [];
    $('.construct-floor-table tbody tr').each(function(idx, item) {
        let param = {
            "floorId" : $(item).find('.floorId').attr('floorId'),
            "responseCode" : $(item).find('input[name="responseCode"]').val(),
            "lastCurlApiAt" : $(item).find('input[name="lastCurlApiAt"]').val(),
            "flrNo" : $(item).find('.flrNo').attr('flrNo'),
            "flrNoNm" : $(item).find('input[name="flrNoNm"]').val(),
            "roomName" : $(item).find('input[name="roomName"]').val(),
            "area" : $(item).find('.area').attr('data'),
            "lndpclAr" : $(item).find('input[name="lndpclAr"]').val(),
            "lndpclArByPyung" : $(item).find('input[name="lndpclArByPyung"]').val(),
            "etcPurps" : $(item).find('input[name="etcPurps"]').val(),
            "companyName" : $(item).find('input[name="companyName"]').val(),
            "guaranteePrice" : $(item).find('input[name="guaranteePrice"]').val(),
            "rent" : $(item).find('input[name="rent"]').val(),
            "management" : $(item).find('input[name="management"]').val(),
            "termStartDate" : $(item).find('input[name="termStartDate"]').val(),
            "termEndDate" : $(item).find('input[name="termEndDate"]').val(),
            "etcInfo" : $(item).find('input[name="etcInfo"]').val(),
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

let calculateSumField = function(e) {
    e.preventDefault();

    let $this = $(this),
        name = $this.attr('name'),
        sumClass = $this.attr('sum'),
        unit = $this.attr('unit'),
        $table = $('.construct-floor-table'),
        $sumSection = $table.find('.' + sumClass);

    let totalValue = 0;
    $table.find('tbody tr').each(function(idx, item) {
        let data = $(item).find('input[name="' + name + '"]').val();
        if (!isNaN(data)) {
            totalValue += Number(data);
        }
    });
    if (totalValue > 0 && totalValue % 1 > 0) {
        totalValue = totalValue.toFixed(2);
    }

    $sumSection.html(totalValue + '<span style="font-size: 15px; padding: 3px;">' + unit + '</span>');
}

let floorInfoRowAdd = function(e) {
    e.preventDefault();

    let data = {
        "flrNo" : 999,
        "area" : 0,
        "lndpclAr" : 0,
        "lndpclArByPyung" : 0,
    }

    let $tbody = $('.construct-floor-table tbody');
    let tag = drawConstructFloorRow(data);
    $tbody.append(tag);
}

let floorInfoRowRemove = function(e) {
    e.preventDefault();
    let $this = $(this);
    $this.parents('.floor-unit').remove();
}

let calculateAreaPyung = function(e) {
    e.preventDefault();

    let $frm = $(this).parents('form'),
        name = $(this).attr('name'),
        pyung = name + 'ByPyung',
        lndpclAr = $(this).val();
    if (isNaN(lndpclAr)) {
       return;
    }

    lndpclAr = Number(lndpclAr)
    lndpclAr = lndpclAr / 3.305785;
    lndpclAr = Math.round(lndpclAr * 100) / 100;
    $frm.find('input[name="' + pyung + '"]').val(lndpclAr);
}

let calculateAreaBcRate = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmConstructRegister"]');
    let archArea = $frm.find('input[name="archArea"]').val(),
        platArea = $frm.find('input[name="platArea"]').val();

    if (isNaN(archArea) || isNaN(platArea)) {
        return;
    }

    archArea = Number(archArea);
    platArea = Number(platArea);

    if (archArea === 0 || platArea === 0) {
        return;
    }

    let bcRat = (archArea / platArea) * 100;
    bcRat = Math.round(bcRat * 100) / 100;
    $frm.find('input[name="bcRat"]').val(bcRat);
}

let calculateAreaVlRate = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmConstructRegister"]');
    let totArea = $frm.find('input[name="vlRatEstmTotArea"]').val(),
        platArea = $frm.find('input[name="platArea"]').val();

    if (isNaN(totArea) || isNaN(platArea)) {
        return;
    }

    totArea = Number(totArea);
    platArea = Number(platArea);

    if (totArea === 0 || platArea === 0) {
        return;
    }

    let vlRate = (totArea / platArea) * 100;
    vlRate = Math.round(vlRate * 100) / 100;
    $frm.find('input[name="vlRat"]').val(vlRate);
}

let drawEmptyFloorTable = function()  {
    let tag = '';
    tag += '<tr>';
    tag += '<td class="text-center" colspan="14">해당 건물의 층별 정보가 없습니다.</td>';
    tag += '</tr>';
    return tag;
}