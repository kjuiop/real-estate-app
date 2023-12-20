let onReady = function() {
};

let fileSearch = function(e) {
    e.preventDefault();

    let $frm = $(`form[name="frmUpload"]`);
    $frm.find('input[name="file"]').trigger('click');
}

let applyFilename = function(e) {
    e.preventDefault();

    let $frm = $(`form[name="frmUpload"]`);
    let filename = $(this).val().split('\\').pop();
    $frm.find('#filename').val(filename);
}

let fileUpload = function(e) {
    e.preventDefault();

    let formData = new FormData();
    formData.append('file', $('#file')[0].files[0]);

    $.ajax({
        url: '/settings/coordinate-manager/file/read',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            twoBtnModal("좌표 데이터를 성공적으로 업로드하였습니다.", function() {
                location.reload();
            });
        },
        error: function() {
            alert("실패")
        }
    });
}


$(document).ready(onReady)
    .on('click', '#btnSearchFile', fileSearch)
    .on('change', '#file', applyFilename)
    .on('click', '#btnFileUpload', fileUpload);