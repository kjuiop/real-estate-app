let loadPriceInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    if (dto.existPriceInfo !== true) {
        return;
    }

    $.ajax({
        url: "/real-estate/price/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let priceList = result.data,
                priceInfo = priceList[0];
            let $frm = $('form[name="frmPriceRegister"]');
            $frm.find('input[name="priceId"]').val(priceInfo.priceId)
            $frm.find('.salePrice').val(priceInfo.salePrice.toLocaleString());
            $frm.find('.priceAdjuster').val(priceInfo.priceAdjuster.toLocaleString());
            $frm.find('.landUnitPrice').val(priceInfo.landUnitPrice.toLocaleString());
            $frm.find('.totalAreaUnitPrice').val(priceInfo.totalAreaUnitPrice.toLocaleString());
            $frm.find('.guaranteePrice').val(priceInfo.guaranteePrice.toLocaleString());
            $frm.find('.rentMonth').val(priceInfo.rentMonth.toLocaleString());
            $frm.find('.management').val(priceInfo.management.toLocaleString());
            $frm.find('.managementExpense').val(priceInfo.managementExpense.toLocaleString());

            calculateRevenueRateRate();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let calculateGuaranteePrice = function(e) {
    e.preventDefault();

    let guaranteePrice = 0;
    $('.floor-unit').each(function(idx, item) {
        guaranteePrice += Number($(item).find('.subGuaranteePrice').val());
    });

    $('.guaranteePrice').val(guaranteePrice);

    calculateRevenueRateRate();
}

let calculateRentPrice = function(e) {
    e.preventDefault();

    let rent = 0;
    $('.floor-unit').each(function(idx, item) {
        rent += Number($(item).find('.subRent').val());
    });

    $('.rentMonth').val(rent);

    calculateRevenueRateRate();
}

let calculateManagementPrice = function(e) {
    e.preventDefault();

    let management = 0;
    $('.floor-unit').each(function(idx, item) {
        management += Number($(item).find('.subManagement').val());
    });

    $('.management').val(management);

    calculateRevenueRateRate();
}

let calculateManagementExpense = function() {
    calculateRevenueRateRate();
}

let calculateAveragePrice = function() {
    // 매매가 / 전체 평

    let $frmBasic = $('form[name="frmBasicRegister"]'),
        $frmLand = $('form[name="frmLandRegister"]'),
        lndpclArByPyung = removeComma($frmLand.find('input[name="totalLndpclArByPyung"]').val()),
        pblntfPclndByPyung = removeComma($frmLand.find('input[name="pblntfPclndByPyung"]').val()),
        $frmConstruct = $('form[name="frmConstructRegister"]'),
        totAreaByPyung = removeComma($frmConstruct.find('input[name="totAreaByPyung"]').val()),
        $frmPrice = $('form[name="frmPriceRegister"]'),
        salePrice = removeComma($frmPrice.find('input[name="salePrice"]').val());

    if (lndpclArByPyung === 0 || salePrice === 0) {
        return;
    }

    salePrice = Number(salePrice) * 100000000;
    lndpclArByPyung = Number(lndpclArByPyung);

    let landUnitPrice = salePrice / lndpclArByPyung;
    landUnitPrice = landUnitPrice / 10000;
    landUnitPrice = Math.round(landUnitPrice);

    $frmPrice.find('input[name="landUnitPrice"]').val(landUnitPrice.toLocaleString());

    if (pblntfPclndByPyung > 0) {
        console.log("pblntfPclndByPyung", pblntfPclndByPyung);
        let landPriceDiff = landUnitPrice / (pblntfPclndByPyung / 10000);
        landPriceDiff = Number(landPriceDiff).toFixed(1);
        console.log("landPriceDiff", landPriceDiff);
        $frmBasic.find('input[name="landPriceDiff"]').val(landPriceDiff);
    }

    if (totAreaByPyung === 0 || salePrice === 0) {
        return;
    }

    totAreaByPyung = Number(totAreaByPyung);

    let totalAreaUnitPrice = salePrice / totAreaByPyung;
    totalAreaUnitPrice = totalAreaUnitPrice / 10000;
    totalAreaUnitPrice = Math.round(totalAreaUnitPrice);

    $frmPrice.find('input[name="totalAreaUnitPrice"]').val(totalAreaUnitPrice.toLocaleString());

    calculateRevenueRateRate()
}

// 수익률=((월임대료+관리비의 XX%)*12)/(매매가-보증금)
let calculateRevenueRateRate = function() {
    // 수익률 = 임대료 수익 - 연간 비용 / 보증금   * 100

    let $frm = $('form[name="frmPriceRegister"]');
    let rentMonth = Number($frm.find('input[name="rentMonth"]').val()),
        managementExpense = Number($frm.find('input[name="managementExpense"]').val()),
        management = Number($frm.find('input[name="management"]').val()),
        guaranteePrice = Number($frm.find('input[name="guaranteePrice"]').val()),
        salePrice = Number($frm.find('input[name="salePrice"]').val());

    if (salePrice === 0 || guaranteePrice === 0) {
        return;
    }

    let rentYear = rentMonth * 12 * 10000,
        managementYear = management * 12 * 10000;
    salePrice = salePrice * 100000000;
    guaranteePrice = guaranteePrice * 10000;

    console.log("revenueRate rentYear : ", rentYear);
    console.log("revenueRate managementYear : ", managementYear);
    console.log("revenueRate salePrice : ", salePrice);
    console.log("revenueRate guaranteePrice : ", guaranteePrice);

    let revenue = rentYear + managementYear - managementExpense;
    let bunmo = salePrice - guaranteePrice;

    console.log("revenueRate revenue : ", revenue);
    console.log("revenueRate bunmo : ", bunmo);

    let revenueRate = (revenue / bunmo) * 100;
    revenueRate = revenueRate.toFixed(2);

    console.log("revenueRate : ", revenueRate);

    $frm.find('input[name="revenueRate"]').val(revenueRate);
}

let priceInfoSave = function(e) {
    e.preventDefault();

    let isModify = dto.existPriceInfo;
    let $frmBasic = $('form[name="frmBasicRegister"]'),
        detailParams = serializeObject({form:$frmBasic[0]}).json();

    let $frmPrice = $('form[name="frmPriceRegister"]'),
        httpMethod = isModify ? 'put' : 'post',
        params = serializeObject({form:$frmPrice[0]}).json();

    params.legalCode = detailParams.legalCode;
    params.landType = detailParams.landType;
    params.bun = detailParams.bun;
    params.ji = detailParams.ji;
    params.address = detailParams.address;
    params.imgUrl = $frmPrice.find('.thumbnailInfo').find('img').attr('src');
    params.realEstateId = dto.realEstateId;

    let floorInfo = [];
    $('.construct-floor-table tbody tr').each(function(idx, item) {
        let param = {
            "flrNo" : $(item).find('.flrNoNm').attr('flrNo'),
            "flrNoNm" : $(item).find('.flrNoNm').attr('data'),
            "area" : $(item).find('.area').attr('data'),
            "mainPurpsCdNm" : $(item).find('.mainPurpsCdNm').attr('data'),
            "etcPurps" : $(item).find('.etcPurps').attr('data'),
            "guaranteePrice" : $(item).find('input[name="guaranteePrice"]').val(),
            "rent" : $(item).find('input[name="rent"]').val(),
            "management" : $(item).find('input[name="management"]').val(),
        }
        floorInfo.push(param);
    });

    params.floorInfo = floorInfo;

    console.log("params", params);

    $.ajax({
        url: "/real-estate/price",
        method: httpMethod,
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            location.href = '/real-estate/' + result.data + '/edit';
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}