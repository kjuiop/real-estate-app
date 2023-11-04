let loadImageInfo = function() {
    if (!checkNullOrEmptyValue(dto.realEstateId)) {
        return;
    }

    $.ajax({
        url: "/real-estate/image/" + dto.realEstateId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(res) {
            console.log("result", res);

            let images = res.data;
            let $imagePanel = $('.image-sub-section');
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


    documentUpload({
        multiple: true,
        accept: '.jpg, .png, .gif',
        sizeCheck: false,
        usageType: `RealEstate`,
        fileType: `Image`,
        callback: function (res) {
            console.log("res", res);
            let attachments = res.data;
            let $imagePanel = $('.image-sub-section');
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
