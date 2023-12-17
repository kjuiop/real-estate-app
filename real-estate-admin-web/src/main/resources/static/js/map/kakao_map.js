let mapContainer = document.getElementById('map');
let mapOption = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};
let map = new kakao.maps.Map(mapContainer, mapOption);
let mapTypes = {
    terrain : kakao.maps.MapTypeId.TERRAIN,
    traffic :  kakao.maps.MapTypeId.TRAFFIC,
    bicycle : kakao.maps.MapTypeId.BICYCLE,
    useDistrict : kakao.maps.MapTypeId.USE_DISTRICT
};

let geocoder = new kakao.maps.services.Geocoder();

let loadKakaoMap = function(searchAddress, addressList) {

    console.log("address", searchAddress);
    console.log("coordinate", addressList);

    if (typeof kakao === undefined) {
        console.error('Kakao Maps API가 로드되지 않았습니다.');
        return
    }

    if (!checkNullOrEmptyValue(searchAddress)) {
        console.error('검색하고자 하는 주소가 올바르지 않습니다.', searchAddress);
        return
    }

    if (addressList.length === 0) {
        searchMapByAddress(searchAddress);
        return;
    }

    let markers = [];
    for (let i=0; i<addressList.length; i++) {
        geocoder.addressSearch(addressList[i].address, function(result, status) {

            let data = addressList[i];

            // 정상적으로 검색이 완료됐으면
            if (status !== kakao.maps.services.Status.OK) {
                console.error('주소 검색 실패:', searchAddress);
                return;
            }

            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            if (i === 0) {
                map.setCenter(coords);
            }

                // let polygonPath = [];
            let polygonPath = [
                new kakao.maps.LatLng(37.511602562001109, 127.05009673359973),
                new kakao.maps.LatLng(37.511691793941253, 127.05023478115433),
                new kakao.maps.LatLng(37.511559383629383, 127.050365219573536),
                new kakao.maps.LatLng(37.511470782817113, 127.050226493881937),
                new kakao.maps.LatLng(37.511602562001109, 127.05009673359973),
            ];

            // $.each(data.vertexInfoList, function(idx, item) {
            //     polygonPath.push(new kakao.maps.LatLng(item.x, item.y));
            // })

            let polygon = new kakao.maps.Polygon({
                path:polygonPath, // 그려질 다각형의 좌표 배열입니다
                strokeWeight: 3, // 선의 두께입니다
                strokeColor: '#39DE2A', // 선의 색깔입니다
                strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle: 'solid', // 선의 스타일입니다
                fillColor: '#A2FF99', // 채우기 색깔입니다
                fillOpacity: 0.7 // 채우기 불투명도 입니다
            });

            polygon.setMap(map);

            // 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
            let mouseoverOption = {
                fillColor: '#EFFFED', // 채우기 색깔입니다
                fillOpacity: 0.8 // 채우기 불투명도 입니다
            };

            // 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
            let mouseoutOption = {
                fillColor: '#A2FF99', // 채우기 색깔입니다
                fillOpacity: 0.7 // 채우기 불투명도 입니다
            };

            // 다각형에 마우스오버 이벤트를 등록합니다
            kakao.maps.event.addListener(polygon, 'mouseover', function() {

                // 다각형의 채우기 옵션을 변경합니다
                polygon.setOptions(mouseoverOption);

            });

            kakao.maps.event.addListener(polygon, 'mouseout', function() {

                // 다각형의 채우기 옵션을 변경합니다
                polygon.setOptions(mouseoutOption);

            });

            let downCount = 0;
            kakao.maps.event.addListener(polygon, 'mousedown', function() {
                console.log(event);
                let resultDiv = document.getElementById('result');
                resultDiv.innerHTML = '다각형에 mousedown 이벤트가 발생했습니다!' + (++downCount);
            });


            // 결과값으로 받은 위치를 마커로 표시합니다
            let marker = new kakao.maps.Marker({
                position: coords,
            });

            // 마커를 지도에 표시합니다.
            marker.setMap(map);
            // 마커를 배열에 추가합니다.
            markers.push(marker);

            let tag = '';
            tag += '<div class="mark-unit" style="padding: 8px; width:400px; height:150px;">';
            tag += '<a href="/real-estate/' + data.realEstateId + '/edit" target="_blank">' + data.address + '</a>';
            tag += '<i class="fa fa-times btnCloseInfo" onclick="closeOverlay()" aria-hidden="true" style="position: absolute; top: 5px; right: 10px;"></i>';
            tag += '</div>';

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            let infowindow = new kakao.maps.InfoWindow({
                content: tag
            });

            // 마커를 클릭하면 인포윈도우를 엽니다
            kakao.maps.event.addListener(marker, 'click', function() {
                // infowindow.open(map, marker);
            });
        });
    }

    let clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 5, // 클러스터 할 최소 지도 레벨
        markers: markers // 클러스터에 마커 추가
    });


}

let searchMapByAddress = function(address) {

    geocoder.addressSearch(address, function(result, status) {

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

let moveMapFocus = function(e) {
    e.preventDefault();

    let address = $(this).attr('address');
    geocoder.addressSearch(address, function(result, status) {

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

let showCadastral = function(e) {
    e.preventDefault();

    let toggleYn = $(this).attr('toggleYn');

    for (let type in mapTypes) {
        map.removeOverlayMapTypeId(mapTypes[type]);
    }

    if (toggleYn === 'N') {
        map.addOverlayMapTypeId(mapTypes.useDistrict);
        $(this).removeClass('btn-default');
        $(this).addClass('btn-primary');
        $(this).attr('toggleYn', 'Y');
        return;
    }

    $(this).addClass('btn-default');
    $(this).removeClass('btn-primary');
    $(this).attr('toggleYn', 'N');
}

let closeOverlay = function() {
    overlay.setMap(null);
}