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
    location.href = '/buyer';
};

let movePage = function(e) {
    e.preventDefault();

    let id = $(this).attr('id');
    location.href = '/buyer/' + id + '/edit';
}


$(document).ready(onReady)
    .on('click', '#btnReset', reset)
    .on('click', '#btnSearch', search)
    .on('click', '.moveEditor', movePage)
;
