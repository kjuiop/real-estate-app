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
            $frm.find('.salePrice').val(priceInfo.salePrice);
            $frm.find('.depositPrice').val(priceInfo.depositPrice);
            $frm.find('.revenueRate').val(priceInfo.revenueRate);
            $frm.find('.averageUnitPrice').val(priceInfo.averageUnitPrice);
            $frm.find('.guaranteePrice').val(priceInfo.guaranteePrice);
            $frm.find('.rentMonth').val(priceInfo.rentMonth);
            $frm.find('.management').val(priceInfo.management);
            $frm.find('.managementExpense').val(priceInfo.managementExpense);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
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