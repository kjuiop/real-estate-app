const historyRealEstateMap = new Map();

let onReady = function() {
    initDate();
    minicolors();
};

let search = function(e) {
    e.preventDefault();

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='size']").val($("#limit :selected").val());
    $frm.find("input[name='page']").val(0);
    $frm.find('input[name="purposeCds"]').val(extractCodeId($('.purposeSection')));
    $frm.find('input[name="buyerGradeCds"]').val(extractCodeId($('.buyerGradeSection')));
    $frm.submit();
};

let reset = function(e) {
    e.preventDefault();
    location.href = '/buyer';
};

let initDate = function() {
    singleDateRangePickerWithTimeInit({
        targetId: 'targetStartDate',
        startName: 'beforeCreatedAt'
    });

    singleDateRangePickerWithTimeInit({
        targetId: 'targetEndDate',
        startName: 'afterCreatedAt'
    });
}

let inputDateData = function (e) {
    e.preventDefault();

    let unit = $(this).val();

    if (unit === 'day') {
        $('input[name=beforeCreatedAt]').val(moment().startOf('day').format('YYYY-MM-DDTHH:mm'));
        $('input[name=afterCreatedAt]').val(moment().endOf('day').format('YYYY-MM-DDTHH:mm'));
    } else if (unit === 'week') {
        $('input[name=beforeCreatedAt]').val(moment().subtract( 'weeks', 1).startOf('day').format('YYYY-MM-DDTHH:mm'));
        $('input[name=afterCreatedAt]').val(moment().endOf('day').format('YYYY-MM-DDTHH:mm'));
    } else if (unit === 'month') {
        $('input[name=beforeCreatedAt]').val(moment().subtract( 'month', 1).startOf('day').format('YYYY-MM-DDTHH:mm'));
        $('input[name=afterCreatedAt]').val(moment().endOf('day').format('YYYY-MM-DDTHH:mm'));
    }

    initDate();
};

let resetDateRadio = function (e){
    e.preventDefault();

    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name=searchDateUnit]").each(function(idx){
        $(this).iCheck("uncheck");
    });
};

let toggleSelectButton = function(e) {
    e.preventDefault();

    let $this = $(this),
        $section = $this.parents('.selected-button-checkbox-section'),
        $allButton = $section.find('.btnAllSelect');

    if ($this.hasClass('selected')) {
        offButton($this);
    } else {
        onButton($this);
        offButton($allButton)
    }
}

let selectAllButton = function(e) {
    e.preventDefault();

    let $this = $(this),
        $section = $this.parents('.selected-button-checkbox-section');

    if ($this.hasClass('selected')) {
        offButton($this);
        $section.find('.btnCode').each(function(idx, item) {
           offButton($(item));
        });
    } else {
        onButton($this);
        $section.find('.btnCode').each(function(idx, item) {
            offButton($(item));
        });
    }

}

let extractCodeId = function(section) {
    let extractCds = '';
    $(section).find('.btnCode').each(function(idx, item) {
        if ($(item).hasClass('selected')) {
            extractCds += $(item).attr('code');
            extractCds += ',';
        }
    });

    if (extractCds.endsWith(',')) {
        extractCds = extractCds.slice(0, -1);
    }
    return extractCds;
}

let showHistoryModal = function(e) {
    e.preventDefault();

    let $modal = $('#historyModal'),
        buyerId = $(this).attr('buyerId'),
        title = $(this).attr('title'),
        name = $(this).attr('name'),
        gradeName = $(this).attr('gradeName'),
        customerName = $(this).attr('customerName'),
        salePrice = $(this).attr('salePrice'),
        preferArea = $(this).attr('preferArea'),
        preferSubway = $(this).attr('preferSubway'),
        preferRoad = $(this).attr('preferRoad'),
        createdAt = $(this).attr('createdAt'),
        landAreaPy = $(this).attr('landAreaPy'),
        totalAreaPy = $(this).attr('totalAreaPy'),
        exclusiveAreaPy = $(this).attr('exclusiveAreaPy'),
        processCd = $(this).attr('processCode')
    ;

    $.ajax({
        url: "/buyer/" + buyerId + "/history",
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let data = result.data,
                detail = data.buyerDetail;

            $modal.find('.modal-title').text('[' + gradeName + '] ' + title);
            $modal.find('#processName').text('[' + name + ']');
            $modal.find('.customerName').text(customerName);
            $modal.find('.salePrice').text(salePrice);
            $modal.find('.preferArea').text(preferArea);
            $modal.find('.preferSubway').text(preferSubway);
            $modal.find('.preferRoad').text(preferRoad);
            $modal.find('.landAreaPy').text(convertDoubleValue(landAreaPy));
            $modal.find('.totalAreaPy').text(convertDoubleValue(totalAreaPy));
            $modal.find('.exclusiveAreaPy').text(convertDoubleValue(exclusiveAreaPy));
            $modal.find('.createdAt').text(createdAt);
            $modal.find('input[name="processCds"]').val(processCd);
            $modal.find('input[name="processName"]').val(name);

            $modal.find('.purposeNameStr').text(detail.purposeNameStr);
            $modal.find('.preferBuildingNameStr').text(detail.preferBuildingNameStr);
            $modal.find('.investmentTimingNameStr').text(detail.investmentTimingNameStr);
            $modal.find('.loanCharacterNameStr').text(detail.loanCharacterNameStr);
            $modal.find('.requestDetail').text(detail.requestDetail);
            $modal.find('input[name="buyerId"]').val(detail.buyerId);
            if (detail.histories.length > 0) {
                $modal.find('.historyTable tbody').html(drawHistoryTable(detail.histories));
            } else {
                $modal.find('.historyTable tbody').html(drawEmptyHistoryTable());
            }
            initHistoryModal();
            $modal.modal('show');
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });


}

let initHistoryModal = function() {
    let $modal = $('#historyModal');
    $modal.find('textarea[name="memo"]').val('');
    $modal.find('.realEstateTable tbody').html(drawEmptyTableBody());
}

let addHistory = function(e) {
    e.preventDefault();

    let $modal = $('#historyModal'),
        buyerId = $modal.find('input[name="buyerId"]').val(),
        processCd = $modal.find('input[name="processCds"]').val(),
        processName = $modal.find('input[name="processName"]').val(),
        memo = $modal.find('textarea[name="memo"]').val();

    if (!checkNullOrEmptyValue(memo)) {
        twoBtnModal("메모를 입력해주세요.");
        return;
    }

    let realEstateIds = [];
    $modal.find('.realEstateTable tbody tr').each(function(idx, item) {
        let realEstateId = $(item).find('.realEstateId').attr('realEstateId');
        if (checkNullOrEmptyValue(realEstateId)) {
            realEstateIds.push(realEstateId);
        }
    });


    let params = {
        "processCds" : convertNullOrEmptyValue(processCd),
        "processName" : processName,
        "memo" : memo,
        "realEstateIds" : realEstateIds
    }

    console.log("buyerId : ",  buyerId);
    console.log("params : ", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer/" + buyerId + "/history",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                let tag = drawHistoryTable(result.data);
                $modal.find('.historyTable tbody').html(tag);
                initHistoryModal();
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let convertDoubleValue = function(doubleValue) {
    doubleValue = parseInt(doubleValue);
    if (doubleValue % 1 === 0) {
        return doubleValue.toFixed(0);
    }
    return doubleValue.toFixed(1);
}

let showHistoryMapModal = function(e) {
    e.preventDefault();

    let $modal = $('#historyMapModal'),
        buyerId = $(this).attr('buyerId'),
        title = $(this).attr('title'),
        gradeName = $(this).attr('gradeName')
    ;

    $('#colorCode').val('');
    $('.minicolors-swatch-color').css('background-color', '');

    $modal.find('.modal-title').text('[' + gradeName + '] ' + title);
    $modal.find('input[name="buyerId"]').val(buyerId);
    $modal.modal('show');
}

let minicolors = function() {
    $('.color-code').minicolors({
        control: $(this).attr('data-control') || 'hue',
        defaultValue: $(this).attr('data-defaultValue') || '',
        format: $(this).attr('data-format') || 'hex',
        keywords: $(this).attr('data-keywords') || '',
        inline: $(this).attr('data-inline') === 'true',
        letterCase: $(this).attr('data-letterCase') || 'lowercase',
        opacity: $(this).attr('data-opacity'),
        position: $(this).attr('data-position') || 'bottom',
        swatches: $(this).attr('data-swatches') ? $(this).attr('data-swatches').split('|') : [],
        change: function(value, opacity) {
            if( !value ) return;
            if( opacity ) value += ', ' + opacity;
            if( typeof console === 'object' ) {
                console.log(value);
            }
        },
        theme: 'bootstrap'
    });
}

let addHistoryMap = function(e) {
    e.preventDefault();

    let $modal = $('#historyMapModal'),
        buyerId = $modal.find('input[name="buyerId"]').val(),
        processName = $modal.find('input[name="processName"]').val(),
        colorCode = $modal.find('input[name="colorCode"]').val(),
        sortOrder = $modal.find('input[name="sortOrder"]').val();

    if (!checkNullOrEmptyValue(processName)) {
        twoBtnModal("단계이름을 입력해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(colorCode)) {
        twoBtnModal("색상코드를 입력해주세요.");
        return;
    }

    console.log("in ", sortOrder);

    if (isNaN(sortOrder)) {
        twoBtnModal("정렬순서에 숫자를 입력해주세요.");
        return;
    }

    let params = {
        "colorCode" : colorCode,
        "processName" : processName,
        "sortOrder" : sortOrder
    }

    console.log("params : ", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer/" + buyerId + "/history-map",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                location.href = '/buyer'
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let searchRealEstate = function(e) {
    e.preventDefault();

    let $modal = $('#searchRealEstateModal'),
        address = $modal.find('input[name="address"]').val(),
        $tbody = $modal.find('.searchTable').find('tbody');

    if (!checkNullOrEmptyValue(address)) {
        twoBtnModal("주소를 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/real-estate/address/" + address,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let tag = '';
            if (result.data.length > 0) {
                tag = drawRealEstateTable(result.data);
                $tbody.html(tag);
                $modal.find('input[name="address"]').val('');
            } else {
                tag = drawEmptyTableBodyRealSearchModal();
                $tbody.html(tag);
            }
            $tbody.html(tag);
            initICheck();

        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let applyRealEstate = function(e) {
    e.preventDefault();

    let $modal = $('#searchRealEstateModal'),
        $tbody = $modal.find('.searchTable tbody');

    let tag = '';
    $tbody.find('tr').each(function(idx, item){
        let isChecked = $(item).find('input[name="realEstateId"]').prop('checked');
        if (isChecked) {
            let realEstate = {
                "realEstateId": $(item).find('input[name="realEstateId"]').val(),
                "salePrice": $(item).find('.salePrice').attr('salePrice'),
                "address": $(item).find('.address').attr('address'),
                "managerName": $(item).find('.managerName').attr('managerName'),
                "lndpclArByPyung": $(item).find('input[name="lndpclArByPyung"]').val(),
                "totAreaByPyung": $(item).find('input[name="totAreaByPyung"]').val(),
                "archAreaByPyung": $(item).find('input[name="archAreaByPyung"]').val(),
                "createdAt": $(item).find('.createdAt').attr('createdAt')
            }
            tag += drawSelectedItem(realEstate);
        }
    });
    $('#historyModal').find('.realEstateTable tbody').html(tag);
    $modal.find('.btnClose').trigger('click');
    $modal.find('input[name="address"]').val('');
    $tbody.html(drawEmptyTableBodyRealSearchModal());
}

let initRealEstateModal = function(e) {
    e.preventDefault();

    let historyId = $(this).attr('historyId'),
        $modal = $('#realEstateListModal'),
        $tbody = $modal.find('.searchTable tbody')
    ;

    let data = historyRealEstateMap.get(historyId);
    console.log("data", data);
    let tag = "";
    if (data.length > 0) {
        tag = drawDetailRealEstateTable(data);
    } else {
        tag = drawEmptyTableBody();
    }
    $tbody.html(tag);
}

let drawHistoryTable = function(histories) {
    let tag = '';
    $.each(histories, function(idx, item) {
        tag += '<tr>';
        tag += '<td>' + item.processName + '</td>';
        tag += '<td style="white-space: normal;">' + item.memo + '</td>';
        tag += '<td>' + item.createdByName + '</td>';
        tag += '<td>' + moment(item.createdAt).format("YYYY-MM-DD") + '</td>';
        tag += '<td>';
        tag += '<button class="btn btn-xs btn-default btnRealEstateList" historyId="' + item.historyId + '" data-toggle="modal" data-target="#realEstateListModal">매물정보</button>';
        tag += '</td>';
        tag += '</tr>';
        historyRealEstateMap.set(item.historyId.toString(), item.realEstateList);
    })
    return tag;
}


$(document).ready(onReady)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch', search)
    .on('click', '.btnAllSelect', selectAllButton)
    .on('click', '.selected-button-checkbox-section .btnCode', toggleSelectButton)
    .on('ifToggled', 'input[name=searchDateUnit]', inputDateData)
    .on('click', '#targetStartDate, #targetEndDate', resetDateRadio)
    .on('click', '.btnHistoryModal', showHistoryModal)
    .on('click', '.btnHistoryAdd', addHistory)
    .on('click', '.btnHistoryMapModal', showHistoryMapModal)
    .on('click', '.btnHistoryMapAdd', addHistoryMap)
    .on('click', '.btnSearch', searchRealEstate)
    .on('click', '.btnApply', applyRealEstate)
    .on('click', '.btnRealEstateList', initRealEstateModal)
;
