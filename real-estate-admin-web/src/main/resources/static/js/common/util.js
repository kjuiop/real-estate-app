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

const isModify = function($form, id) {
    if ($form.find('input[name=' + id + ']').val() === null || $form.find('input[name=' + id + ']').val() === '') return false;
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

const drawErrorMessage = function($field, errorMsg) {
    $field.after('<small class="error-message text-small text-danger">' + errorMsg + '</small>');
}

const drawSuccessMessage = function($field, errorMsg) {
    $field.after('<small class="error-message text-small text-blue">' + errorMsg + '</small>');
}
