let onReady = function() {
    initDate();
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
        purposeNameStr = $(this).attr('purposeNameStr'),
        preferBuildingNameStr = $(this).attr('preferBuildingNameStr'),
        investmentTimingNameStr = $(this).attr('investmentTimingNameStr'),
        loanCharacterNameStr = $(this).attr('loanCharacterNameStr')
    ;

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
    $modal.find('.purposeNameStr').text(purposeNameStr);
    $modal.find('.preferBuildingNameStr').text(preferBuildingNameStr);
    $modal.find('.investmentTimingNameStr').text(investmentTimingNameStr);
    $modal.find('.loanCharacterNameStr').text(loanCharacterNameStr);
    $modal.find('.createdAt').text(createdAt);
    $modal.modal('show');
}

let convertDoubleValue = function(doubleValue) {
    if (doubleValue % 1 === 0) {
        return doubleValue.toFixed(0);
    }
    return doubleValue.toFixed(1);
}


$(document).ready(onReady)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch', search)
    .on('click', '.btnAllSelect', selectAllButton)
    .on('click', '.selected-button-checkbox-section .btnCode', toggleSelectButton)
    .on('ifToggled', 'input[name=searchDateUnit]', inputDateData)
    .on('click', '#targetStartDate, #targetEndDate', resetDateRadio)
    .on('click', '.btnHistoryModal', showHistoryModal)
;
