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
            $modal.find('.landAreaPy').text(landAreaPy);
            $modal.find('.totalAreaPy').text(totalAreaPy);
            $modal.find('.exclusiveAreaPy').text(exclusiveAreaPy);
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
            $modal.modal('show');
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });


}

let drawEmptyHistoryTable = function() {
    let tag = '';
    tag += '<tr>';
    tag += '<td colSpan="4" class="text-alien-center">';
    tag += '등록된 메모가 없습니다.';
    tag += '</td>';
    tag += '</tr>';
    return tag;
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

    let params = {
        "processCds" : convertNullOrEmptyValue(processCd),
        "processName" : processName,
        "memo" : memo
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
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    let tag = drawHistoryTable(result.data);
                    $modal.find('.historyTable tbody').html(tag);
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let convertDoubleValue = function(doubleValue) {
    if (doubleValue % 1 === 0) {
        return doubleValue.toFixed(0);
    }
    return doubleValue.toFixed(1);
}

let drawHistoryTable = function(histories) {
    let tag = '';
    $.each(histories, function(idx, item) {
        tag += '<tr>';
        tag += '<td>' + item.processName + '</td>';
        tag += '<td style="white-space: normal;">' + item.memo + '</td>';
        tag += '<td>' + item.createdByName + '</td>';
        tag += '<td>' + moment(item.createdAt).format("YYYY-MM-DD") + '</td>';
        tag += '</tr>';
    })
    return tag;
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
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    location.href = '/buyer'
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let showSearchRealEstateModal = function(e) {
    e.preventDefault();
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
    .on('click', '.btnSearchRealEstate', showSearchRealEstateModal)
;
