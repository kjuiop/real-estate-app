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
            $frm.find('input[name="vlRatEstmTotArea"]').val(constructInfo.vlRatEstmTotArea);
            $frm.find('input[name="vlRatEstmTotAreaByPyung"]').val(constructInfo.vlRatEstmTotAreaByPyung);
            $frm.find('input[name="heit"]').val(constructInfo.heit);

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

            calculateFloorInfo();
            $(".construct-floor-table tbody").sortable().disableSelection();
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
        tag += '<td class="center-text padding-8"><select class="form-control form-control-sm" style="min-width: 70px;" name="underFloorYn">';
        if (item.underFloorYn === 'Y') {
            tag += '<option value="N">지상</option><option value="Y" selected>지하</option>';
        } else {
            tag += '<option value="N" selected>지상</option><option value="Y">지하</option>';
        }
        tag += '</select></td>';
        tag += '<td class="center-text padding-8 flrNo" flrNo="' + item.flrNo + '" data="' + item.flrNoNm + '"><input type="text" class="form-control form-control-sm flrNoNm" value="' + convertNullOrEmptyValue(item.flrNoNm) + '" name="flrNoNm" style="min-width: 100px;"/></td>';
        tag += '<td class="center-text padding-8 area" data="' + item.area + '">' + item.area + '<span style="font-size: 15px; padding: 3px;">㎡</span></td>';
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="text" class="form-control form-control-sm lndpclAr calSumField" value="' + convertNullOrEmptyValue(item.lndpclAr) + '" name="lndpclAr"  sum="totalLndpclAr" unit="㎡" style="min-width: 100px;"/><span style="font-size: 15px; padding: 3px;">㎡</span></div></td>';
        tag += '<td class="center-text padding-6"><div class="display-flex-row"><input type="text" class="form-control form-control-sm lndpclArByPyung calSumField" value="' +  convertNullOrEmptyValue(item.lndpclArByPyung) + '" name="lndpclArByPyung" sum="totalLndpclArByPyung" unit="평" style="min-width: 100px;"/><span style="font-size: 14px; padding: 3px;">평</span></div></td>';
        tag += '<td class="center-text padding-6 etcPurps" data="' + item.etcPurps + '">' + item.etcPurps + '</td>';
        tag += '<td class="center-text padding-6"><input type="text" class="form-control form-control-sm companyName" value="' + convertNullOrEmptyValue(item.companyName) + '" name="companyName" style="min-width: 100px;"/></td>';
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
        tag += '</tr>';
    })
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
            "underFloorYn" : $(item).find('select[name="underFloorYn"] option:selected').val(),
            "flrNo" : $(item).find('.flrNo').attr('flrNo'),
            "flrNoNm" : $(item).find('input[name="flrNoNm"]').val(),
            "area" : $(item).find('.area').attr('data'),
            "lndpclAr" : $(item).find('input[name="lndpclAr"]').val(),
            "lndpclArByPyung" : $(item).find('input[name="lndpclArByPyung"]').val(),
            "etcPurps" : $(item).find('.etcPurps').attr('data'),
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

    $sumSection.html(totalValue + '<span style="font-size: 15px; padding: 3px;">' + unit + '</span>');
}
