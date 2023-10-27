let loadImageInfo = function() {
    if (!checkNullOrEmptyValue(dto)) {
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
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}