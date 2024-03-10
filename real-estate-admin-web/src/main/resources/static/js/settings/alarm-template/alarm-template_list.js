let onReady = function() {
};

let search = function(e) {
    let $frm = $("form[name='frmSearch']");
    $frm.find("input[name='size']").val($("#limit :selected").val());
    $frm.find("input[name='page']").val(0);
    $frm.submit();
};

let reset = function(e) {
    e.preventDefault();
    location.href = '/settings/alarm-template';
};

$(document).ready(onReady)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch', search)
;
