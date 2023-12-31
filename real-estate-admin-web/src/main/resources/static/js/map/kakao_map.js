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

            let imageSrc = '/images/marker/marker.png', // 마커이미지의 주소입니다
                imageSize = new kakao.maps.Size(40, 40), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

            // 결과값으로 받은 위치를 마커로 표시합니다
            let marker = new kakao.maps.Marker({
                position: coords,
                image: markerImage,
            });

            // 마커를 지도에 표시합니다.
            marker.setMap(map);
            // 마커를 배열에 추가합니다.
            markers.push(marker);

            let overlayId = "overlay-" + i;

            let tag = '';
            tag += '<div id="' + overlayId + '" class="customoverlay" toggle="on">';
            tag += '<a href="#" class="btnSearchById" realEstateId="' + data.realEstateId + '">';
            if (checkNullOrEmptyValue(data.buildingName)) {
                tag += '<span class="title">' + data.buildingName + '</span>';
            } else {
                tag += '<span class="title">' + data.address + '</span>';
            }
            tag += '</a>';
            tag += '</div>';

            // let tag = drawMarkContent(data);

            // 커스텀 오버레이를 생성합니다
            let customOverlay = new kakao.maps.CustomOverlay({
                map: map,
                position: coords,
                content: tag,
                yAnchor: 1
            });

            kakao.maps.event.addListener(marker, 'click', function() {
                let $overlay = $('#' + overlayId),
                    toggle = $overlay.attr('toggle');
                if (toggle === 'on') {
                    $overlay.addClass('hidden');
                    $overlay.attr('toggle', 'off');
                } else {
                    $overlay.removeClass('hidden');
                    $overlay.attr('toggle', 'on');
                }
            });

            // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
            function closeOverlay() {
                customOverlay.setMap(null);
            }

            kakao.maps.event.addListener(map, 'zoom_changed', function() {

                const currentZoomLevel = map.getLevel();

                if (currentZoomLevel > 3) {
                    customOverlay.setMap(null);  // 오버레이 숨기기
                } else {
                    customOverlay.setMap(map);   // 오버레이 보이기
                }
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

let makePolygonVertex = function() {
    let polygonList = [];

    let polygonPath = [
        new kakao.maps.LatLng(37.511602562001109, 127.05009673359973),
        new kakao.maps.LatLng(37.511691793941253, 127.05023478115433),
        new kakao.maps.LatLng(37.511559383629383, 127.050365219573536),
        new kakao.maps.LatLng(37.511470782817113, 127.050226493881937),
        new kakao.maps.LatLng(37.511602562001109, 127.05009673359973),
    ];

    polygonList.push(polygonPath);

    let polygonPath1 = [
        new kakao.maps.LatLng(37.506471316736786, 127.05342856887178),
        new kakao.maps.LatLng(37.506517201807284, 127.05357811689069),
        new kakao.maps.LatLng(37.50656137724208, 127.05372212207114),
        new kakao.maps.LatLng(37.50625533295345, 127.05387006751465),
        new kakao.maps.LatLng(37.5062111577463, 127.05372594973724),
        new kakao.maps.LatLng(37.50616536291009, 127.05357651539924),
        new kakao.maps.LatLng(37.506471316736786, 127.05342856887178),
    ];

    polygonList.push(polygonPath1);

    let polygonPath2 = [
        new kakao.maps.LatLng(37.57604478457453, 126.89804998783433),
        new kakao.maps.LatLng(37.576116777200255, 126.89805431449109),
        new kakao.maps.LatLng(37.576598992669574, 126.898567821659),
        new kakao.maps.LatLng(37.576605393515045, 126.8986652591916),
        new kakao.maps.LatLng(37.576399963095504, 126.8989696758272),
        new kakao.maps.LatLng(37.57622199473788, 126.89923339043274),
        new kakao.maps.LatLng(37.57614460693191, 126.8992414092178),


        new kakao.maps.LatLng(37.57564709042266, 126.89871160930305),
        new kakao.maps.LatLng(37.575640767990194, 126.89862139525427),
        new kakao.maps.LatLng(37.5759728462171, 126.89811788490428),
        new kakao.maps.LatLng(37.575990784733456, 126.89809716650815),
        new kakao.maps.LatLng(37.57604478457453, 126.89804998783433),
    ];

    polygonList.push(polygonPath2);

    return polygonList;
}

let drawMarkContent = function(data) {
    let tag = '<div class="wrap">' +
        '    <div class="info">' +
        '        <div class="title">' +
        '            카카오 스페이스닷원' +
        '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
        '        </div>' +
        '        <div class="body">' +
        '            <div class="img">' +
        '                <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
        '           </div>' +
        '            <div class="desc">' +
        '                <div class="ellipsis">제주특별자치도 제주시 첨단로 242</div>' +
        '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
        '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '</div>';

    return tag;
}


let polygonFunc = function() {
    // let polygonList = makePolygonVertex();
    //
    //
    // $.each(polygonList, function(idx, item) {
    //
    //     let polygon = new kakao.maps.Polygon({
    //         path:item, // 그려질 다각형의 좌표 배열입니다
    //         strokeWeight: 3, // 선의 두께입니다
    //         strokeColor: '#39DE2A', // 선의 색깔입니다
    //         strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //         strokeStyle: 'solid', // 선의 스타일입니다
    //         fillColor: '#A2FF99', // 채우기 색깔입니다
    //         fillOpacity: 0.7 // 채우기 불투명도 입니다
    //     });
    //
    //     polygon.setMap(map);
    //
    // });

    // $.each(data.vertexInfoList, function(idx, item) {
    //     polygonPath.push(new kakao.maps.LatLng(item.x, item.y));
    // })



    // 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
    // let mouseoverOption = {
    //     fillColor: '#EFFFED', // 채우기 색깔입니다
    //     fillOpacity: 0.8 // 채우기 불투명도 입니다
    // };
    //
    // // 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
    // let mouseoutOption = {
    //     fillColor: '#A2FF99', // 채우기 색깔입니다
    //     fillOpacity: 0.7 // 채우기 불투명도 입니다
    // };
    //
    // // 다각형에 마우스오버 이벤트를 등록합니다
    // kakao.maps.event.addListener(polygon, 'mouseover', function() {
    //
    //     // 다각형의 채우기 옵션을 변경합니다
    //     polygon.setOptions(mouseoverOption);
    //
    // });
    //
    // kakao.maps.event.addListener(polygon, 'mouseout', function() {
    //
    //     // 다각형의 채우기 옵션을 변경합니다
    //     polygon.setOptions(mouseoutOption);
    //
    // });

    // let downCount = 0;
    // kakao.maps.event.addListener(polygon, 'mousedown', function() {
    //     console.log(event);
    //     let resultDiv = document.getElementById('result');
    //     resultDiv.innerHTML = '다각형에 mousedown 이벤트가 발생했습니다!' + (++downCount);
    // });
}