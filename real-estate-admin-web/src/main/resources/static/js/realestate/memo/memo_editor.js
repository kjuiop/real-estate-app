let preventDoubleEnter = false;

let loadMemoInfo = function() {
    preventDoubleEnter = false;

    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    $.ajax({
        url: "/real-estate/memo/" + dto.realEstateId + "?all_memo=false",
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("memo result", result);
            let memoInfoList = result.data;
            let tag = '';
            if (memoInfoList.length === 0) {
                tag = drawEmptyMemoInfo();
            } else {
                tag = drawMemoInfoList(memoInfoList);
            }
            $('.memoInfo').html(tag);
            initICheck();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}



let addMemo = function(e) {

    if (e.key !== "Enter") {
        return;
    }

    if (dto.realEstateId === null) {
        return;
    }

    e.preventDefault();

    if (preventDoubleEnter) {
        return;
    }

    preventDoubleEnter = true;

    let params = {
        "realEstateId" : dto.realEstateId,
        "memo" : $(this).val(),
    }

    console.log("memo", params);

    $.ajax({
        url: "/real-estate/memo",
        method: "post",
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("result : ", result);
            let message = '정상적으로 저장되었습니다.';
            twoBtnModal(message, function() {
                location.href = '/real-estate/' + result.data + '/edit';
            });
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

    setTimeout(function() {
        preventDoubleEnter = false;
    }, 1000);

}

let drawEmptyMemoInfo = function() {
    let tag = '';
    tag += '<tr>';
    tag +=    '<td class="text-center" colSpan="5">등록된 메모가 없습니다.</td>';
    tag += '</tr>';
    return tag;
}

let drawMemoInfoList = function(memoInfoList) {
    let tag = '';

    $.each(memoInfoList, function(idx, item) {
        tag += '<tr class="memoUnit">';
        tag +=  '<td style="width:10%">';
        tag +=      '<input type="checkbox" class="checkElement" name="memoId" value="' + item.memoId +'">';
        tag +=  '</td>';
        tag +=  '<td style="width:20%">' + item.createdAtFormat + '</td>';
        tag +=  '<td style="width:20%">' + item.createdByName + '</td>';
        if (item.deleteYn === 'Y') {
            tag +=  '<td style="width:40%;">' + item.memo + ' <span style="color: darkred; font-style: italic;">(삭제됨)</span></td>';
        } else {
            tag +=  '<td style="width:40%">' + item.memo + '</td>';
        }
        tag +=  '<td style="width:10%"><button type="button" class="btn btn-xs btn-danger btnRemoveMemo pull-right"><i class="fa fa-times" aria-hidden="true" style="font-size: 15px;"></i></button></td>';
        tag += '</tr>';
    });
    return tag;
}

let removeMemo = function(e) {
    e.preventDefault();

    let memoId = $(this).parents('.memoUnit').find('input[name="memoId"]').val();

    let params = {
        "realEstateId" : dto.realEstateId,
        "memoId" : memoId,
    }

    twoBtnModal("메모를 삭제하시겠습니까?", function() {
        $.ajax({
            url: "/real-estate/memo",
            method: "delete",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("result : ", result);
                let message = '정상적으로 삭제되었습니다.';
                twoBtnModal(message, function() {
                    location.reload();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let removeAllMemo = function(e) {
    e.preventDefault();

    let memoIds = [];
    $("input[name='memoId']:checked").each(function (idx, item) {
        memoIds.push($(item).val());
    });

    let params = {
        "realEstateId" : dto.realEstateId,
        "memoIds" : memoIds
    }

    twoBtnModal("메모를 삭제하시겠습니까?", function() {
        $.ajax({
            url: "/real-estate/memo/list",
            method: "delete",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("result : ", result);
                let message = '정상적으로 삭제되었습니다.';
                twoBtnModal(message, function() {
                    location.reload();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let showDelMemo = function(e) {
    e.preventDefault();

    $.ajax({
        url: "/real-estate/memo/" + dto.realEstateId + "?all_memo=true",
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("memo result", result);
            let memoInfoList = result.data;
            let tag = '';
            if (memoInfoList.length === 0) {
                tag = drawEmptyMemoInfo();
            } else {
                tag = drawMemoInfoList(memoInfoList);
            }
            $('.memoInfo').html(tag);
            initICheck();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}
