/**
 * Attachment JS
 * @author Jake
 * @date 22.02.02
 */

function initialization($form) {
    const   $file = $form.find('input[name="file"]'),
        $files = $form.find('#files');

    $file.val('');
    $files.val('');
}

function ajaxForm($form, uploadSuccess) {
    $form.ajaxForm({
        beforeSend: function () {
            $("body").spin("modal");
        },
        uploadProgress: function (event, position, total, percentComplete) {},
        success: function () {},
        complete: uploadSuccess,
        error: function () {}
    });
}

function documentUpload(options) {

    const $form = $("form[name='frmUpload']");

    $.ajaxSetup({
        dataType: "json",
        beforeSend: function () {
            $("body").spin("modal");
        },
        complete: function () {
            $("body").spin("modal");
        },
        error: function (jqxhr, textStatus, errorThrown) {
        }
    });

    /**
     * init
     */
    initialization($form);
    let $inputFile = $form.find('#file');
    if (options !== null && options.multiple !== undefined && options.multiple) $inputFile = $form.find('#files');
    if (options !== null && options.accept !== undefined) $inputFile.attr('accept', options.accept);
    if (options !== null && options.usageType !== undefined) $form.find(`#usageType`).val(options.usageType);
    if (options !== null && options.fileType !== undefined) $form.find(`#fileType`).val(options.fileType);


    console.log("$inputFile ", $inputFile);
    console.log("options ", options);


    const uploadSuccess = function(responseText, statusText) {

        console.log("responseText", responseText);
        console.log("statusText", statusText);

        $("body").spin("modal");
        if (statusText === 'success') {
            const responseStatus = responseText.status;
            const $file = $("input:file", $form);
            if (responseStatus !== 200) return false;
            const data = responseText.responseJSON;
            if (typeof(options.callback) === 'function') options.callback(data, options);
            $file.val('');
            $file.attr("accept", "");
        }
    };

    $inputFile.one("change", function(e) {
        e.preventDefault();
        ajaxForm($form, uploadSuccess);

        // // 바이트 기준 1048576(Byte) = 1024(KB) = 1(MB)
        // const fileSize = document.getElementById("file").size;
        // const fileType = document.getElementById("file").type.split('/');
        //
        // //3MB
        // if (fileType[0] === 'image' && (fileType[1] === 'png' || fileType[1] === 'jpg' || fileType[1] === 'jpeg') && fileSize > 3145728) {
        //     oneBtnModal("이미지는 최대 3MB 용량을 넘기지 못합니다.");
        //     return false;
        // }

        $form.submit();
    });

    /**
     * Hidden input File Button Click.
     */
    $inputFile.click();
}

