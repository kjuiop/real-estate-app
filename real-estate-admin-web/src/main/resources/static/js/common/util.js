function singleDateRangePickerWithTimeInit(options) {
    if (typeof (options) != "object") {
        return false;
    }

    var target;
    if (typeof (options.targetId) === "string") {
        target = $("#" + options.targetId);
    } else if (typeof (options.target) === "object") {
        target = options.target;
    } else {
        return false;
    }
    var st_dt;
    if (typeof (options.startName) === "string") {
        st_dt = $("input[name='" + options.startName + "']");
    } else if (typeof (options.startTarget) === "object") {
        st_dt = options.startTarget;
    } else {
        return false;
    }
    var timeIncrement = 1;
    var format = 'YYYY-MM-DD HH:mm';
    var formatT= 'YYYY-MM-DDTHH:mm';
    if (typeof (options.daterangepicker) == "object") {
        if (options.daterangepicker.timePickerIncrement !== undefined) {
            timeIncrement = options.daterangepicker.timePickerIncrement;
        }
        if (options.daterangepicker.format !== undefined) {
            format = options.daterangepicker.format;
        }
        if (options.daterangepicker.formatT !== undefined) {
            formatT = options.daterangepicker.formatT;
        }
    }
    target.daterangepicker({
        startDate: moment(),
        endDate: moment(),
        timePicker: true,
        timePicker24Hour: true,
        singleDatePicker: true,
        timePickerIncrement: timeIncrement,
        format: 'YYYY-MM-DD HH:mm',
        showDropdowns: true,
        showWeekNumbers: false,
        opens: 'right',
        locale: {
            applyLabel: 'Apply',
            cancelLabel: 'Clear',
            fromLabel: 'From',
            toLabel: 'To',
            daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            firstDay: 1
        },
        autoUpdateInput: true
    });

    var setShowDateRange = function () {
        var targetStartDate = target.data('daterangepicker').startDate.format(format);
        var startDate = target.data('daterangepicker').startDate.format(formatT);
        target.val(targetStartDate);
        st_dt.val(startDate);
    };

    //init search date start
    if (st_dt.val().length > 0) {
        target.data("daterangepicker").setStartDate(moment(st_dt.val(), formatT));
        setShowDateRange();
    } else {
        target.val('');
    }

    //date range picker apply event
    target.on('apply.daterangepicker', function (e, picker) {
        setShowDateRange();
    });

    //date range picker cancel event
    target.on('cancel.daterangepicker', function (e, picker) {
        $(this).val('');
        st_dt.val('');
    });
}

let selectedChkAll = function(e) {
    e.preventDefault();

    let $this = $(this);
    let childenClass = $this.data('children');
    let checked = $this.prop('checked');
    if (checked) {
        $(':checkbox[name=' + childenClass + ']').iCheck('check');
    } else {
        $(':checkbox[name=' + childenClass + ']').iCheck('uncheck');
    }

};

let selectedChkBox = function(e) {
    e.preventDefault();

    if($(this).attr('class') === 'chkAll') return;

    let $chkAll;
    let childrenName = $(this).attr('name');

    $(".chkAll").each(function() {
        if(childrenName === $(this).data("children")) $chkAll = $(this);
    });

    if ($(':checkbox[name=' + childrenName + ']').length === $(':checkbox[name=' + childrenName + ']:checked').length) {
        $chkAll.prop('checked', 'checked');
    } else {
        $chkAll.prop('checked', false);
    }
    $chkAll.iCheck('update');
};

const isModify = function($form, id) {
    if ($form.find('input[name="' + id + '"]').val() === null || $form.find('input[name="' + id + '"]').val() === '') return false;
    return true;
};

const checkNullOrEmptyValue = function (parameter) {
    if (parameter === null || parameter === '' || parameter === undefined || parameter === 'null') return false;
    return true;
};

const convertNullOrEmptyValue = function (parameter) {
    if (parameter === null || parameter === '' || parameter === undefined || parameter === 'null') return '';
    return parameter;
};

const convertNullOrZero = function (parameter) {
    if (parameter === null || parameter === '' || parameter === undefined || parameter === 'null') return 0;
    return parameter;
};

const jQueryErrorField = function (errorList) {
    console.log("errorList", errorList);
    let $field;
    $.each(errorList, function(idx, error){
        $field = $(error.element);

        if ($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            drawErrorMessage($field, error.message);
        }
    });
};

const ajaxErrorFieldByText = function (response) {
    const errorFields = response.responseJSON;

    let $field, error;
    $.each(errorFields, function(idx, error){
        $field = $('#'+ error['field']);

        if ($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            drawErrorMessage($field, error.defaultMessage);
        }
    });
};

let onlyNumberKeyEvent = function (options) {
    if (typeof (options) !== 'object') return false;

    let option = {};
    option.className = "only-number";
    option.formId = "";

    $.extend(options, option);

    let target = "";
    if (option.formId === "") {
        target = $("." + option.className);
    } else {
        target = $("." + option.className, $("#" + option.formId));
    }

    target.each(function () {
        $(this).unbind("keydown").keydown(function (e) {
            // Allow: backspace, delete, tab, escape, enter, and, -, .
            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 189, 190]) !== -1 ||
                // Allow: Ctrl+A
                (e.keyCode == 65 && e.ctrlKey === true) ||
                // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                return;
            }
            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                e.preventDefault();
            }
        });
    });

    target.keyup(function (e) {
        let inputValue = $(this).val();
        if (e.keyCode == 8) return;
        $(this).val(onlyNumber(inputValue));
    });
};

let ajaxErrorFieldByModal = function (response) {
    const errorFields = response.responseJSON;

    let $field, error;
    $.each(errorFields, function(idx, error){
        $field = $('#'+ error['field']);

        if ($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            oneBtnModal(error.defaultMessage);
        }
    });
};

let checkEmailValidCheck = function (email) {
    let reg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    return reg.test(email)
};

const drawErrorMessage = function($field, errorMsg) {
    $field.html('<small class="error-message text-small text-danger margin-left-3">' + errorMsg + '</small>');
}

const drawSuccessMessage = function($field, errorMsg) {
    $field.html('<small class="error-message text-small text-blue margin-left-3">' + errorMsg + '</small>');
}
