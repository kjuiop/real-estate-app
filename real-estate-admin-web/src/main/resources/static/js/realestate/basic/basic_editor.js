let loadBasicInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    loadKakaoMap(dto.address);
    loadImg(dto.imgUrl);

    let $frm = $('form[name="frmBasicRegister"]'),
        usageCodeId = $frm.find('.usageCode').val();

    if (dto.ownExclusiveYn === 'Y') {
        $frm.find('input[name="ownExclusiveYn"]').iCheck('check');
    }
    if (dto.otherExclusiveYn === 'Y') {
        $frm.find('input[name="otherExclusiveYn"]').iCheck('check');
    }

    $.ajax({
        url: "/settings/category-manager/children-categories?parentId=" + usageCodeId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let categories = result.data;

            let tags = drawBtnUsageCode(categories);
            $frm.find('.usageCdsSection').html(tags);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let loadImg = function(imgUrl) {
    if (!checkNullOrEmptyValue(imgUrl)) {
        return;
    }
    let $imagePanel = $('.image-section');
    let tag = imgDraw(imgUrl);
    $imagePanel.html(tag);
}

let imgDraw = function (fullPath) {

    let tag = '' +
        '<div class="thumbnailInfo ui-state-default">' +
        '<div class="col-md-12 no-left-padding right-margin">' +
        '<div class="image-panel" style="width:100%;">' +
        '<button type="button" class="btn btn-danger pull-right remove-image">' +
        '<i class="fa fa-times" aria-hidden="true"></i>' +
        '</button>' +
        '<a href="#"><img src="' + fullPath + '" class="btnImageUpload"></a>' +
        '</div>' +
        '</div>' +
        '</div>';

    return tag;
};