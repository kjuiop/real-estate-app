let onReady = function() {

    console.log("dto", dto);
    console.log("landInfoList", landInfoList);

    if (constructFloorList.length > 0) {
        calculateFloorInfo(constructFloorList);
    }

    if (landInfoList.length > 0) {
        calculateLandInfo(landInfoList);
    }

    if (checkNullOrEmptyValue(landPriceList) && landPriceList.length > 0) {
        loadLandPriceTable(landPriceList);
    }

    loadKakaoMap(dto.address);
    loadLandMap(dto.address);
    if (checkNullOrEmptyValue(dto.characterInfo)) {
        $('#characterInfo').html(dto.characterInfo.replace(/\n/g, '<br>'));
    }
}

let loadLandPriceTable = function(landPriceList) {
    let $table = $('.pblnt-table tbody');
    let tag = drawPriceTable(landPriceList);
    $table.html(tag);
}

let drawPriceTable = function(priceInfo) {
    let tag = '';

    $.each(priceInfo, function(idx, item) {
        tag += '<tr>';
        tag += '<th class="text-alien-center table-head">' + item.pclndStdrYear + '</th>';
        tag += '<td class="text-alien-center">' + addCommasToNumber(item.pblntfPclnd) + '</td>';
        tag += '<td class="text-alien-center">' + addCommasToNumber(item.pblntfPclndPy) + '</td>';
        if (item.changeRate > 0) {
            tag += '<td class="text-alien-center">' + item.changeRate + '%<span style="color: darkred;">▲</span></td>';
        } else if (item.changeRate < 0) {
            tag += '<td class="text-alien-center">' + item.changeRate + '%<span style="color: darkblue;">▼</span></td>';
        } else {
            tag += '<td class="text-alien-center">' + item.changeRate + '%</td>';
        }
        tag += '</tr>';
    });

    return tag;
}

let calculateFloorInfo = function(constructFloorList) {

    if (constructFloorList.length === 0) {
        return;
    }

    let $section = $('.floor-price-section'),
        totalLndpclAr = 0,
        totalLndpclArByPyung = 0;

    $.each(constructFloorList, function(idx, item) {
        totalLndpclAr += item.area;
        totalLndpclArByPyung += item.areaPy;
    });

    totalLndpclAr = Number(totalLndpclAr);
    totalLndpclArByPyung = Number(totalLndpclArByPyung);
    console.log("calculateFloorInfo totalLndpclAr", totalLndpclAr);
    console.log("calculateFloorInfo totalLndpclArByPyung", totalLndpclArByPyung);

    totalLndpclAr = totalLndpclAr.toFixed(2);
    totalLndpclArByPyung = totalLndpclArByPyung.toFixed(2);

    $section.find('.totalLndpclAr').text(addCommasToNumber(totalLndpclAr));
    $section.find('.totalLndpclArByPyung').text(addCommasToNumber(totalLndpclArByPyung));
}

let calculateLandInfo = function(landInfoList) {

    if (landInfoList.length === 0) {
        return;
    }

    let $section = $('.land-info-section'),
        totalLndpclAr = 0,
        totalLndpclArByPyung = 0;

    $.each(landInfoList, function(idx, item) {
       totalLndpclAr += item.lndpclAr;
       totalLndpclArByPyung += item.lndpclArByPyung;
    });

    if (totalLndpclAr > 0) {
        totalLndpclAr = totalLndpclAr.toFixed(2);
    }
    if (totalLndpclArByPyung > 0) {
        totalLndpclArByPyung = totalLndpclArByPyung.toFixed(2);
    }

    $section.find('.totalLndpclAr').text(totalLndpclAr);
    $section.find('.totalLndpclArByPyung').text(totalLndpclArByPyung);
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

        if (result.length > 0 && checkNullOrEmptyValue(result[0].address)) {
            let address = result[0].address;
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

let loadLandMap = function(searchAddress) {

    if (typeof kakao === undefined) {
        console.error('Kakao Maps API가 로드되지 않았습니다.');
        return
    }

    if (!checkNullOrEmptyValue(searchAddress)) {
        console.error('검색하고자 하는 주소가 올바르지 않습니다.', searchAddress);
        return
    }
    let mapContainer = document.getElementById('landMap'); // 지도를 표시할 div
    let mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    let map = new kakao.maps.Map(mapContainer, mapOption);
    let geocoder = new kakao.maps.services.Geocoder();


    geocoder.addressSearch(searchAddress, function(result, status) {

        if (result.length > 0 && checkNullOrEmptyValue(result[0].address)) {
            let address = result[0].address;
        }

        // 정상적으로 검색이 완료됐으면
        if (status !== kakao.maps.services.Status.OK) {
            return
        }

        let changeMapType = kakao.maps.MapTypeId.USE_DISTRICT;

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

        map.addOverlayMapTypeId(changeMapType);
        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    });

}

let print = function(e) {
    e.preventDefault();
    window.print();
}

let removeTable = function(e) {
    e.preventDefault();
    $(this).parents('.table-unit').remove();
}

$(document).ready(onReady)
    .on('click', '.btnPrint', print)
    .on('click', '.btnRemoveTable', removeTable);