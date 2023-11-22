let onReady = function() {
    loadKakaoMap('서울특별시 강남구 도산대로 149');
}

let cursorItem = function(e) {
    alert("in");

}
$(document).ready(onReady)
    .on('cursor', '.real-estate-unit', cursorItem);