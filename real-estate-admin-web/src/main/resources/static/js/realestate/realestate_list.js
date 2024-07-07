let preventDoubleEnter = false;

let onReady = function() {
    console.log("condition", condition);
};

let search = function(e) {
    e.preventDefault();

    console.log("preventDoubleEnter", preventDoubleEnter);

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='size']").val($("#limit :selected").val());
    $frm.find("input[name='page']").val(0);
    $frm.find('input[name="processTypeCds"]').val(extractCodeArray($('.processUnitSection')));
    $frm.find('input[name="usageCds"]').val(extractCodeIdForSearch($('.usageUnitSection')));
    $frm.submit();
};

let reset = function(e) {
    e.preventDefault();
    location.href = '/real-estate';
};

let realEstateModal = function(e) {
    e.preventDefault();

    $('#realEstateModal').modal('show');
}

let moveRegister = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmMoveRegister"]'),
        params = serializeObject({form:$frm[0]}).json(),
        dongCode = $frm.find('select[name="dong"] option:selected').val();

    let usageCdId = $('select[name="usageCd"] option:selected').val();
    if (!checkNullOrEmptyValue(usageCdId)) {
        twoBtnModal("매물 용도를 선택해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.bun)) {
        twoBtnModal("지번을 입력해주세요.");
        return;
    }

    if (checkNullOrEmptyValue(dongCode)) {
        let sido = $frm.find('select[name="sido"] option:selected').attr('name');
        let gungu = $frm.find('select[name="gungu"] option:selected').attr('name');
        let dong = $frm.find('select[name="dong"] option:selected').attr('name');
        let bun = $frm.find('input[name="bun"]').val();
        let ji = $frm.find('input[name="ji"]').val();
        params.address = sido + " " + gungu + " " + dong + " " + bun;
        if (checkNullOrEmptyValue(ji)) {
            params.address += "-" + ji;
        }
    }

    if (!checkNullOrEmptyValue(params.address) && !checkNullOrEmptyValue(dongCode)) {
        twoBtnModal("주소 검색 또는 주소를 입력해주세요.");
        return;
    }

    console.log("params : ", params);

    location.href = "/real-estate/new"
        + "?bCode=" + params.bCode
        + "&landType=" + params.landType
        + "&bun=" + params.bun
        + "&ji=" + params.ji
        + "&address=" + encodeURIComponent(params.address)
        + "&usageCdId=" + usageCdId
        + "&dongCode=" + dongCode
    ;

    // $.ajax({
    //     url: "/real-estate/check-duplicate/" + params.address,
    //     method: "get",
    //     type: "json",
    //     contentType: "application/json",
    //     success: function (result) {
    //         console.log("result : ", result);
    //         if (result.data) {
    //             twoBtnModal("이미 등록된 매물 정보입니다.");
    //         } else {
    //             location.href = "/real-estate/new"
    //                 + "?bCode=" + params.bCode
    //                 + "&landType=" + params.landType
    //                 + "&bun=" + params.bun
    //                 + "&ji=" + params.ji
    //                 + "&address=" + encodeURIComponent(params.address)
    //                 + "&usageCdId=" + usageCdId
    //                 + "&dongCode=" + $frm.find('select[name="dong"] option:selected').val()
    //             ;
    //         }
    //     },
    //     error:function(error){
    //         ajaxErrorFieldByText(error);
    //     }
    // });


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

    let $frm = $(this).parents('form');

    $.ajax({
        url: "/settings/area-manager/" + areaId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function (result) {
            console.log("area result : ", result);
            let areaList = result.data;

            let $gungu = $frm.find('select[name="gungu"]'),
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
        tag += '<option id="' + item.areaId + '" value="' + item.legalAddressCode + '" areaId="' + item.areaId + '" legalCode="' + item.legalAddressCode + '" name="' + item.name + '">' + item.name + '</option>';
    });
    return tag;
}

let toggleAddressType = function(e) {
    e.preventDefault();

    let type = $(this).val();
    let $frm = $('form[name="frmMoveRegister"]');

    if (type === 'kakao') {
        $frm.find('.kakao-section').removeClass('hidden');
        $frm.find('.direct-section').addClass('hidden');
    } else if (type === 'direct') {
        $frm.find('.kakao-section').addClass('hidden');
        $frm.find('.direct-section').removeClass('hidden');
    }

    $frm.find('input[name="address"]').val('');
}

let showExcelModal = function(e) {
    e.preventDefault();

    let $modal = $('#excelUploadModal'),
        $tbody = $modal.find('tbody');

    $modal.find('#filename').val('');
    $modal.find('#file').val('');
    let tag = '';
    tag += '<tr>';
    tag += '<td class="text-alien-center" colSpan="4">매물 데이터를 업로드 해주세요.</td>';
    tag += '</tr>';
    $tbody.html(tag);

    $modal.modal('show');
}

let fileSearch = function(e) {
    e.preventDefault();

    let $frm = $(`form[name="frmExcelUpload"]`);
    $frm.find('input[name="file"]').trigger('click');
}

let applyFilename = function(e) {
    e.preventDefault();

    let $frm = $(`form[name="frmExcelUpload"]`);
    let filename = $(this).val().split('\\').pop();
    $frm.find('#filename').val(filename);
}

let excelUpload = function(e) {
    e.preventDefault();

    let formData = new FormData();
    formData.append('file', $('#file')[0].files[0]);

    $.ajax({
        url: '/real-estate/excel/read',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            console.log("response", response);
            let result = response.data;
            if (!checkNullOrEmptyValue(result)) {
                return;
            }

            let estateList = result.estateList;

            if (estateList.length === 0) {
                return;
            }

            let tag = drawExcelUploadData(estateList);
            $('.excelProgressSection').html(tag);

            let uploadId = estateList[0].uploadId;
            let timeoutLimit = result.timeout;
            checkUploadProgress(uploadId, timeoutLimit);
        },
        error: function() {
            twoBtnModal("excel 데이터 업로드를 실패하였습니다.");
        }
    });
}

let checkUploadProgress = function (uploadId, timeoutLimit) {
    let startTime = Date.now();
    let endTime = startTime + timeoutLimit;

    console.log("endTime", endTime);

    function pollUploadStatus() {
        $.ajax({
            url: "/real-estate/excel/upload-check/" + uploadId,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                console.log("업로드 프로세스 결과", res);

                let result = res.data;
                if (!checkNullOrEmptyValue(result)) {
                    return;
                }

                let tag = drawExcelUploadData(result.data);
                $('.excelProgressSection').html(tag);

                // 업로드가 완료되었는지 확인
                if (result.completeYn === 'Y') {
                    twoBtnModal("매물정보 업데이트가 모두 완료되었습니다.", function() {
                        location.reload();
                    });
                } else if (Date.now() < endTime) {
                    // 아직 완료되지 않았고 타임아웃 시간 내라면 계속 체크
                    setTimeout(pollUploadStatus, 5000); // 5초마다 체크
                } else {
                    twoBtnModal("매물정보 업데이트가 모두 완료되었습니다.", function() {
                        location.reload();
                    });
                    // 타임아웃 시간이 초과되었을 때 처리
                    let tag = drawProgressFail(result.data);
                    $('.excelProgressSection').html(tag);
                }
            },
            error: function (error) {
                ajaxErrorFieldByText(error);
            }
        });
    }

    // 최초 체크 수행
    pollUploadStatus();
};

let drawProgressFail = function(excelList) {
    let tag = '';
    $.each(excelList, function(idx, item) {
        tag += '<tr>';
        tag += '<td>' + item.rowIndex + '</td>';
        tag += '<td>' + item.address + '</td>';
        if (item.uploadStatus === '완료') {
            tag += '<td style="color: #206724; font-weight: bold;">' + item.uploadStatus + '</td>';
        } else if (item.uploadStatus === '실패') {
            tag += '<td style="color: darkred; font-weight: bold;">' + item.uploadStatus + '</td>';
        } else {
            tag += '<td style="color: darkred; font-weight: bold;">' + item.uploadStatus + '</td>';
        }
        tag += '<td>' + convertNullOrEmptyValue(item.skipReason) + '</td>';
        tag += '</tr>';
    });
    return tag;
}


let drawExcelUploadData = function(excelList) {

    let tag = '';
    $.each(excelList, function(idx, item) {
        tag += '<tr>';
        tag += '<td>' + item.rowIndex + '</td>';
        tag += '<td>' + item.address + '</td>';
        if (item.uploadStatus === '완료') {
            tag += '<td style="color: #206724; font-weight: bold;">' + item.uploadStatus + '</td>';
        } else if (item.uploadStatus === '실패') {
            tag += '<td style="color: darkred; font-weight: bold;">' + item.uploadStatus + '</td>';
        } else {
            tag += '<td>' + item.uploadStatus + '</td>';
        }
        tag += '<td>' + convertNullOrEmptyValue(item.skipReason) + '</td>';
        tag += '</tr>';
    });
    return tag;
}

$(document).ready(onReady)
    .on('click', '.btnAddress', searchAddress)
    .on('click', '#btnReset', reset)
    .on('change', '#limit', search)
    .on('click', '#btnSearch', search)
    .on('click', '#btnMoveRegister', moveRegister)
    .on('click', '.btnAllSelect', selectAllButtonForSearch)
    .on('ifToggled', '.chkAll', selectedChkAll)
    .on('ifToggled', 'input[name=numbers]', selectedChkBox)
    .on('click', '#btnRealEstateModal', realEstateModal)
    .on('change', 'select[name="sido"], select[name="gungu"]', getChildAreaData)
    .on('ifToggled', 'input[name="toggleAddress"]', toggleAddressType)
    .on('click', '.btnShowExcelModal', showExcelModal)
    .on('click', '#btnSearchFile', fileSearch)
    .on('change', '#file', applyFilename)
    .on('click', '.btnExcelUpload', excelUpload)
    .on('click', '.selected-button-checkbox-section .btnCode', toggleCheckboxSelectButton)
;
