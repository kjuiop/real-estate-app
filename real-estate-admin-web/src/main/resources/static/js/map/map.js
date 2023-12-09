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
    salePriceSlider($('.priceAmountUnit'));
    depositPriceSlider($('.depositAmountUnit'));
    rentPriceSlider($('.rentAmountUnit'));
    areaSlider($('.areaAmountUnit'));
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

    let $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.slider-range').slider({
        range: true,
        min: 0,
        max: 500,
        step: 20,
        values: [ 0, 500 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "억원 - " + ui.values[ 1 ] + "억원");
        }
    });

    $priceSlider.find('.amount').val($sliderRange.slider( "values", 0 ) +
        "억원 - " + $sliderRange.slider( "values", 1 ) + "억원");

}

let depositPriceSlider = function($priceSlider) {

    let $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.slider-range').slider({
        range: true,
        min: 0,
        max: 30000,
        step: 500,
        values: [ 0, 30000 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - " + ui.values[ 1 ] + "만원");
        }
    });

    $priceSlider.find('.amount').val($sliderRange.slider( "values", 0 ) +
        "만원 - " + $sliderRange.slider( "values", 1 ) + "만원");

}

let rentPriceSlider = function($priceSlider) {

    let $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.slider-range').slider({
        range: true,
        min: 0,
        max: 500,
        step: 10,
        values: [ 0, 500 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "만원 - " + ui.values[ 1 ] + "만원");
        }
    });

    $priceSlider.find('.amount').val($sliderRange.slider( "values", 0 ) +
        "만원 - " + $sliderRange.slider( "values", 1 ) + "만원");

}

let areaSlider = function($priceSlider) {

    let $sliderRange = $priceSlider.find('.slider-range');

    $priceSlider.find('.slider-range').slider({
        range: true,
        min: 0,
        max: 500,
        values: [ 0, 500 ],
        slide: function( event, ui ) {
            $priceSlider.find('.amount').val(ui.values[ 0 ] + "평 - " + ui.values[ 1 ] + "평");
        }
    });

    $priceSlider.find('.amount').val($sliderRange.slider( "values", 0 ) +
        "평 - " + $sliderRange.slider( "values", 1 ) + "평");

}

let applyPriceRange = function(e) {
    e.preventDefault();
    $('.searchPriceBox').addClass('hidden');
}

let applyAreaRange = function(e) {
    e.preventDefault();
    $('.searchAreaBox').addClass('hidden');
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
;