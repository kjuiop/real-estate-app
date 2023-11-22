let mapContainer = document.getElementById('map');
let mapOption = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};
let map = new kakao.maps.Map(mapContainer, mapOption);
let geocoder = new kakao.maps.services.Geocoder();

let loadKakaoMap = function(searchAddress, addressList) {

    console.log("address", searchAddress)

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
    let firstCoords;
    for (let i=0; i<addressList.length; i++) {
        geocoder.addressSearch(addressList[i], function(result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status !== kakao.maps.services.Status.OK) {
                console.error('주소 검색 실패:', searchAddress);
                return;
            }

            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            if (i === 0) {
                firstCoords = coords;
            }

            // 결과값으로 받은 위치를 마커로 표시합니다
            let marker = new kakao.maps.Marker({
                position: coords,
            });

            // 마커를 지도에 표시합니다.
            marker.setMap(map);

            // 마커를 배열에 추가합니다.
            markers.push(marker);

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            let infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">매물위치</div>'
            });

            // 마커를 클릭하면 인포윈도우를 엽니다
            kakao.maps.event.addListener(marker, 'click', function() {
                infowindow.open(map, marker);
            });
            if (i === 0) {
                map.setCenter(coords);
            }
        });
    }

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