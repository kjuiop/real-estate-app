let loadBasicInfo = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    loadKakaoMap(dto.address);
    loadImg(dto.imgUrl);

    let $frm = $('form[name="frmBasicRegister"]');
    if (dto.ownExclusiveYn === 'Y') {
        $frm.find('input[name="ownExclusiveYn"]').iCheck('check');
    }
    if (dto.otherExclusiveYn === 'Y') {
        $frm.find('input[name="otherExclusiveYn"]').iCheck('check');
    }

}

let changeUsageCode = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmBasicRegister"]'),
        usageCodeId = $(this).val();

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