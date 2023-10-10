let onReady = function() {
    console.log("condition", condition);
};

let search = function(e) {
    e.preventDefault();

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='size']").val($("#limit :selected").val());
    $frm.find("input[name='page']").val(0);

    let processType = $(this).attr('processType');
    if (checkNullOrEmptyValue(processType)) {
        $frm.find('input[name="processType"]').val(processType);
    }
    $frm.submit();
};

let reset = function(e) {

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='page']").val(0);
    $frm.find("input[name='size']").val(10);
    $frm.find('input[name="processType"]').val('');
    $frm.submit();
};

let realEstateModal = function(e) {
    e.preventDefault();

    $('#realEstateModal').modal('show');
}

let moveRegister = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmMoveRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    console.log("params : ", params);

    location.href = "/real-estate/new"
        + "?bCode=" + params.bCode
        + "&landType=" + params.landType
        + "&bun=" + params.bun
        + "&ji=" + params.ji
        + "&address=" + encodeURIComponent(params.address);

}

let searchAddress = function(e) {
    e.preventDefault();

    let $frm = $(this).parents(`form[name="frmRegister"]`);
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
            $frm.find(`input[name="address"]`).val(data.address);
            $frm.find(`select[name="landType"]`).focus();
            loadKakaoMap(data.address)
        }
    }).open();
}

let loadKakaoMap = function(searchAddress) {

    if (typeof kakao === undefined) {
        console.error('Kakao Maps API가 로드되지 않았습니다.');
        return
    }

    if (!checkNullOrEmptyValue(searchAddress)) {
        console.error('검색하고자 하는 주소가 올바르지 않습니다.', searchAddress);
        return
    }

    let mapContainer = document.getElementById('map'); // 지도를 표시할 div
    let mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    let map = new kakao.maps.Map(mapContainer, mapOption);
    let geocoder = new kakao.maps.services.Geocoder();


    geocoder.addressSearch(searchAddress, function(result, status) {

        console.log("data", result);
        if (result.length > 0 && checkNullOrEmptyValue(result[0].address)) {
            let address = result[0].address;
            $('input[name="address"]').val(address.address_name)
            $('input[name="bun"]').val(address.main_address_no);
            $('input[name="ji"]').val(address.sub_address_no);
            $('input[name="bCode"]').val(address.b_code);
            $('input[name="hCode"]').val(address.h_code);
        }

        // 정상적으로 검색이 완료됐으면
        if (status !== kakao.maps.services.Status.OK) {
            return
        }

        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        // 결과값으로 받은 위치를 마커로 표시합니다
        let marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        /**
         *
         * @type {kakao.maps.InfoWindow}
         */
        let infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">매물위치</div>'
        });
        // infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    });

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
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let drawAreaOption = function(depth, areaList) {
    let tag = '';
    if (depth === 'gungu') {
        tag += '<option value="">구군 선택</option>';
    } else if (depth === 'dong') {
        tag += '<option value="">동 선택</option>';
    }
    $.each(areaList, function(idx, item) {
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '">' + item.name + '</option>';
    });
    return tag;
}

$(document).ready(onReady)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch, .btnProcessType', search)
    .on('change', '#limit', search)
    .on('click', '#btnMoveRegister', moveRegister)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox)
    .on('click', '#btnRealEstateModal', realEstateModal)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData);
