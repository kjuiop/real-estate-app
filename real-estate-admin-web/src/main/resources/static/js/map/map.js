let onReady = function() {
    let address = '서울특별시 강남구 도산대로 149';
    if (checkNullOrEmptyValue(condition.address)) {
        address = condition.address;
    }

    if (coordinateList.length > 0) {
        address = coordinateList[0].address;
    }

    $(".slider-red .slider").slider();

    loadKakaoMap(address, coordinateList);
    executeSlider();
    searchBoxHide();
}

let searchBoxHide = function() {
    $(document).mouseup(function(event) {
        let $searchPriceBox = $('.searchPriceBox'),
            $searchAreaBox = $('.searchAreaBox');

        if (!$searchPriceBox.is(event.target) && !$searchPriceBox.has(event.target).length) {
            $searchPriceBox.addClass('hidden');
        }

        if (!$searchAreaBox.is(event.target) && !$searchAreaBox.has(event.target).length) {
            $searchAreaBox.addClass('hidden');
        }
    });
}

let executeSlider = function() {
    salePriceSlider($('.priceAmountUnit'));
    depositPriceSlider($('.depositAmountUnit'));
    rentPriceSlider($('.rentAmountUnit'));

    lndpclArSlider($('.lndpclArAmountUnit'));
    totAreaSlider($('.totAreaAmountUnit'));
    archAreaSlider($('.archAreaAmountUnit'));
}

let searchData = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmSearch"]');
    $frm.submit();
}

let getChildAreaData = function() {
    let areaId = $(this).find('option:selected').attr('areaId'),
        name = $(this).attr('name');

    if (!checkNullOrEmptyValue(areaId)) {
        oneBtnModal("시/도, 군/구를 선택해주세요.");
        return;
    }

    $.ajax({
        url: "/settings/area-manager/" + areaId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function (result) {
            console.log("area result : ", result);
            let areaList = result.data;
            let $frm = $('form[name="frmSearch"]'),
                $gungu = $frm.find('select[name="gungu"]'),
                $dong = $frm.find('select[name="dong"');
            let tag;
            if (name === 'sido') {
                tag = drawAreaOption("gungu", areaList);
                $gungu.html(tag);
                $dong.html('<option>동 선택</option>');
                $frm.find('select[name="landType"]').val('general');
                $frm.find('input[name="bun"]').val('');
                $frm.find('input[name="ji"]').val('');
            } else if (name === 'gungu') {
                tag = drawAreaOption("dong", areaList);
                $dong.html(tag);
                $frm.find('select[name="landType"]').val('general');
                $frm.find('input[name="bun"]').val('');
                $frm.find('input[name="ji"]').val('');
            }

            setSearchAddress();
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let setSearchAddress = function() {
    let $frm = $('form[name="frmSearch"]'),
        $address = $frm.find('input[name="address"]'),
        sido = $frm.find('select[name="sido"] option:selected').attr('name'),
        gungu = $frm.find('select[name="gungu"] option:selected').attr('name'),
        dong = $frm.find('select[name="dong"] option:selected').attr('name'),
        address = "";

    if (!checkNullOrEmptyValue(sido)) {
        $address.val(address);
        return;
    }

    address = sido;

    if (!checkNullOrEmptyValue(gungu)) {
        $address.val(address);
        return;
    }

    address = sido + " " + gungu;

    if (!checkNullOrEmptyValue(dong)) {
        $address.val(address);
        return;
    }

    address = sido + " " + gungu + " " + dong;
    $address.val(address);
}

let drawAreaOption = function(depth, areaList) {
    let tag = '';
    if (depth === 'gungu') {
        tag += '<option value="">구군 선택</option>';
    } else if (depth === 'dong') {
        tag += '<option value="">동 선택</option>';
    }
    $.each(areaList, function(idx, item) {
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '" name="' +  item.name + '">' + item.name + '</option>';
    });
    return tag;
}

let moveDetailPage = function(e) {
    e.preventDefault();

    let url = $(this).attr('href');
    window.open(url, '_blank');
}

let removeCoordinate = function(e) {
    e.preventDefault();

    let $this = $(this);
    $this.parents('.mark-unit').parent().parent().addClass('hidden');
}

let showSearchPriceBox = function(e) {
    e.preventDefault();
    $('.searchPriceBox').removeClass('hidden');
    $('.searchAreaBox').addClass('hidden');
}

let cancelPriceBox = function(e) {
    e.preventDefault();
    $('.searchPriceBox').addClass('hidden');
}

let showSearchAreaBox = function(e) {
    e.preventDefault();
    $('.searchAreaBox').removeClass('hidden');
    $('.searchPriceBox').addClass('hidden');
}

let cancelAreaBox = function(e) {
    e.preventDefault();
    $('.searchAreaBox').addClass('hidden');
}

let salePriceSlider = function($priceSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 5000,
        step: 50,
        values: [ 0, 5000 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "억원 - " + ui.values[ 1 ] + "억원");
            $frm.find('input[name="minSalePrice"]').val(ui.values[0]);
            $frm.find('input[name="maxSalePrice"]').val(ui.values[1]);
            if (ui.values[1] === 5000) {
                $priceSlider.find('.amount').val(ui.values[ 0 ] + "억원 - 제한없음");
                $frm.find('input[name="maxSalePrice"]').val('');
            }
        }
    });

    if (condition.minSalePrice > 0 || condition.maxSalePrice > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minSalePrice),
            displayMax = checkNullOrEmptyValue(condition.maxSalePrice) ? convertNullOrEmptyValue(condition.maxSalePrice) + '억원' : '제한없음';
        $priceSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minSalePrice, condition.maxSalePrice]);
    }
}

let depositPriceSlider = function($priceSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 50000,
        step: 500,
        values: [ 0, 50000 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - " + ui.values[ 1 ] + "만원");
            $frm.find('input[name="minDepositPrice"]').val(ui.values[0]);
            $frm.find('input[name="maxDepositPrice"]').val(ui.values[1]);
            if (ui.values[1] === 50000) {
                $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - 제한없음");
                $frm.find('input[name="maxDepositPrice"]').val('');
            }
        }
    });

    if (condition.minDepositPrice > 0 || condition.maxDepositPrice > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minDepositPrice),
            displayMax = checkNullOrEmptyValue(condition.maxDepositPrice) ? convertNullOrEmptyValue(condition.maxDepositPrice) + '만원' : '제한없음';
        $priceSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minDepositPrice, condition.maxDepositPrice]);
    }
}

let rentPriceSlider = function($priceSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 500,
        step: 10,
        values: [ 0, 500 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - " + ui.values[ 1 ] + "만원");
            $frm.find('input[name="minRentPrice"]').val(ui.values[0]);
            $frm.find('input[name="maxRentPrice"]').val(ui.values[1]);
            if (ui.values[1] === 500) {
                $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - 제한없음");
                $frm.find('input[name="maxRentPrice"]').val('');
            }
        }
    });

    if (condition.minRentPrice > 0 || condition.maxRentPrice > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minRentPrice),
            displayMax = checkNullOrEmptyValue(condition.maxRentPrice) ? convertNullOrEmptyValue(condition.maxRentPrice) + '만원' : '제한없음';
        $priceSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minRentPrice, condition.maxRentPrice]);
    }
}

let lndpclArSlider = function($areaSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $areaSlider.find('.slider-range');

    $areaSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 1000,
        step: 20,
        values: [ 0, 1000 ],
        slide: function( event, ui ) {
            $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - " + ui.values[ 1 ] + "평");
            $frm.find('input[name="minLndpclArByPyung"]').val(ui.values[0]);
            $frm.find('input[name="maxLndpclArByPyung"]').val(ui.values[1]);
            if (ui.values[1] === 1000) {
                $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - 제한없음");
                $frm.find('input[name="maxLndpclArByPyung"]').val('');
            }
        }
    });

    if (condition.minLndpclArByPyung > 0 || condition.maxLndpclArByPyung > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minLndpclArByPyung),
            displayMax = checkNullOrEmptyValue(condition.maxLndpclArByPyung) ? convertNullOrEmptyValue(condition.maxLndpclArByPyung) + '만원' : '제한없음';
        $areaSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minLndpclArByPyung, condition.maxLndpclArByPyung]);
    }
}

let totAreaSlider = function($areaSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $areaSlider.find('.slider-range');

    $areaSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 1000,
        step: 20,
        values: [ 0, 1000 ],
        slide: function( event, ui ) {
            $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - " + ui.values[ 1 ] + "평");
            $frm.find('input[name="minTotAreaByPyung"]').val(ui.values[0]);
            $frm.find('input[name="maxTotAreaByPyung"]').val(ui.values[1]);
            if (ui.values[1] === 1000) {
                $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - 제한없음");
                $frm.find('input[name="maxTotAreaByPyung"]').val('');
            }
        }
    });

    if (condition.minTotAreaByPyung > 0 || condition.maxTotAreaByPyung > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minTotAreaByPyung),
            displayMax = checkNullOrEmptyValue(condition.maxTotAreaByPyung) ? convertNullOrEmptyValue(condition.maxTotAreaByPyung) + '만원' : '제한없음';
        $areaSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minTotAreaByPyung, condition.maxTotAreaByPyung]);
    }
}

let archAreaSlider = function($areaSlider) {

    let $frm = $('form[name="frmSearch"]'),
        $sliderRange = $areaSlider.find('.slider-range');

    $areaSlider.find('.amount').val('0 - 제한없음');

    $sliderRange.slider({
        range: true,
        min: 0,
        max: 1000,
        step: 20,
        values: [ 0, 1000 ],
        slide: function( event, ui ) {
            $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - " + ui.values[ 1 ] + "평");
            $frm.find('input[name="minArchAreaByPyung"]').val(ui.values[0]);
            $frm.find('input[name="maxArchAreaByPyung"]').val(ui.values[1]);
            if (ui.values[1] === 1000) {
                $areaSlider.find('.amount').val(ui.values[ 0 ] + "평 - 제한없음");
                $frm.find('input[name="maxArchAreaByPyung"]').val('');
            }
        }
    });

    if (condition.minArchAreaByPyung > 0 || condition.maxArchAreaByPyung > 0) {
        let displayMin = convertNullOrEmptyValue(condition.minArchAreaByPyung),
            displayMax = checkNullOrEmptyValue(condition.maxArchAreaByPyung) ? convertNullOrEmptyValue(condition.maxArchAreaByPyung) + '만원' : '제한없음';
        $areaSlider.find('.amount').val(displayMin + " - " + displayMax);
        $sliderRange.slider("values", [condition.minArchAreaByPyung, condition.maxArchAreaByPyung]);
    }
}

let applyPriceRange = function(e) {
    e.preventDefault();
    $('.searchPriceBox').addClass('hidden');
    let $frm = $('form[name="frmSearch"]');
    $frm.submit();
}

let applyAreaRange = function(e) {
    e.preventDefault();
    $('.searchAreaBox').addClass('hidden');
    let $frm = $('form[name="frmSearch"]');
    $frm.submit();
}

let searchById = function(e) {
    e.preventDefault();
    let realEstateId = $(this).attr('realEstateId');
    let condition = {
        "realEstateId" : realEstateId,
    }
    $.ajax({
        url: '/map/real-estate',
        type: "post",
        data: condition,
        dataType: "html",
        cache: false,
        async : false,
        success: function (data) {
            console.log("data", data);
            // 서버에서 받은 HTML을 적절한 위치에 삽입
            $('#realEstateSection').html(data);
        },
        error: function () {
            alert('Ajax request failed');
        }
    });
}

$(document).ready(onReady)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData)
    .on('change', 'select[name="dong"]', setSearchAddress)
    .on('click', '.real-estate-unit', moveMapFocus)
    .on('change', 'select[name="usageCd"], select[name="processType"]', searchData)
    .on('click', '.btnMoveDetail', moveDetailPage)
    .on('click', '.btnCadastral', showCadastral)
    .on('click', '.btnRemove', removeCoordinate)
    .on('click',  '.btnSearchPrice', showSearchPriceBox)
    .on('click', '.btnCancelPrice', cancelPriceBox)
    .on('click', '.btnSearchArea', showSearchAreaBox)
    .on('click', '.btnCancelArea', cancelAreaBox)
    .on('click', '.btnApplyPriceRange', applyPriceRange)
    .on('click', '.btnApplyAreaRange', applyAreaRange)
    .on('click', '.btnSearchById', searchById)
;