let loadImageInfo = function() {
    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    let $frm = $('form[name="frmPriceRegister"]');

    $.ajax({
        url: "/real-estate/image/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(res) {
            console.log("result", res);

            let images = res.data;
            let $imagePanel = $frm.find('.image-sub-section');
            $.each(images, function(idx, item) {
                let tag = drawSubImageTag(idx, item.fullPath);
                $imagePanel.append(tag);
            });
            $( ".sortable-section" ).sortable().disableSelection();
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let multiImgUpload = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmPriceRegister"]');

    documentUpload({
        multiple: true,
        accept: '.jpg, .png, .gif',
        sizeCheck: false,
        usageType: `RealEstate`,
        fileType: `Image`,
        callback: function (res) {
            console.log("res", res);
            let attachments = res.data;
            let $imagePanel = $frm.find('.image-sub-section');
            $.each(attachments, function(idx, item) {
                let tag = drawSubImageTag(idx, item.fullPath);
                $imagePanel.append(tag);
            });

            $( ".sortable-section" ).sortable().disableSelection();
        }
    });
}

let drawSubImageTag = function(idx, fullPath) {
    let tag = '';
    tag += '<div class="display-inline-block sub-img-unit">';
    tag += '<a href="#"><img id="sub-image-' + idx + '" src="' + fullPath + '" class="col-sm-12 no-left-padding thumbnailInfo sub-image" style="cursor: pointer; width: 50px; height: 50px;"/></a>';
    tag += '</div>';
    return tag;
}

let removeSubImg = function(e) {
    e.preventDefault();

    let $imgModal = $('#image-modal');
    let targetId = $imgModal.find('#targetSubImg').val();
    let targetSubImgId = '#' + targetId;
    $(targetSubImgId).parents('.sub-img-unit').remove();
    $imgModal.find('.close').trigger('click');
}

let uploadImage = function(e) {
    e.preventDefault();

    let $this = $(this);

    documentUpload({
        multiple: false,
        accept: '.jpg, .png, .gif',
        sizeCheck: false,
        usageType: `RealEstate`,
        fileType: `Image`,
        callback: function (res) {
            console.log("res", res);
            let image = res.data[0];
            let $imagePanel = $this.parents('.image-section');
            let tag = imgDraw(image.fullPath);
            $imagePanel.html(tag);
            $imagePanel.find('.thumbnailInfo').last().data('thumbnail-data', image);
        }
    });
}

let removeImage = function() {
    let $this = $(this),
        $imagePanel = $this.parents('.image-section');
    let tag = '<img src="/images/no-image-found.jpeg" class="col-sm-12 no-left-padding btnImageUpload thumbnailInfo" style="cursor: pointer;"/>';
    $imagePanel.html(tag);
}

let loadImg = function(imgUrl) {
    if (!checkNullOrEmptyValue(imgUrl)) {
        return;
    }

    let $frm = $('form[name="frmPriceRegister"]');

    let $imagePanel = $frm.find('.image-section');
    let tag = imgDraw(imgUrl);
    $imagePanel.html(tag);
}

let imgDraw = function (fullPath) {

    let tag = '' +
        '<div class="thumbnailInfo ui-state-default">' +
        '<div class="right-margin">' +
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

