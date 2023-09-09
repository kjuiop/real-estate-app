let onReady = function() {
    console.log("categories", categories);


    $('#btn-add-lv2, btn-add-lv3').attr('disabled', true);
    getCategories(null, 1);
    minicolors();
};

let showCategoryAddModal = function(e) {
    e.preventDefault();

    let lv = parseInt($(this).data('lv'));
    let parentId = $('#parentId-lv' + lv).val();

    $('#parentId').val(parentId);
    $('#lv').val(lv);
    $('#colorCode').val('');
    $('#name').val('');
    $('#saveType').val("new");
    $('#sortOrder').val(0);
    $("#category-active").iCheck('check');

    showParentCategoryName(lv);
    $('.modal-title').text('카테고리 등록');
    $('.btnSave').text('등록');
    $('#category-editor').modal('show');
};

let getChildrenCategory = function(e) {
    e.preventDefault();

    let $this = $(this),
        checked = $this.prop('checked');

    if (checked) {

        let parentId = $(this).val();
        let level = parseInt($(this).attr('name').replace("lv", "")) + 1;
        let colorCode = $(this).attr('colorCode');

        console.log("level : ", level);

        $('#parentId-lv' + level).val(parentId);
        $('#parentColorCode').val(colorCode);
        $('.minicolors-swatch-color').css('background-color', colorCode);

        getCategories(parentId, level);
    }
};

/**
 * 코드값 갖고오기
 * @param parentId
 * @param level
 */
let getCategories = function (parentId, level) {
    $('#category-lv' + level).empty();
    $('#btn-add-lv' + level).attr('disabled', false);

    let ajaxUrl = '/settings/category-manager/parent-categories';
    if (checkNullOrEmptyValue(parentId)) {
        ajaxUrl = '/settings/category-manager/children-categories?parentId=' + parentId;
    }

    if (level === 1) {
        $('#category-lv2').empty();
        $('#btn-add-lv2').attr('disabled', true);
    }

    $.get(ajaxUrl, function (resp) {
        let data = resp.data;
        console.log("categories", resp);
        if (checkNullOrEmptyValue(data) && data.length > 0) {
            $(data).each(function (idx, c) {
                let clsInactive = c.activeYn === 'N' ? 'inactive' : '';
                let categoryUi = '';
                let colorCode = checkNullOrEmptyValue(c.colorCode) ? c.colorCode : 'white';

                categoryUi += '<li class="display-flex-row col-12 category-unit" style="color: grey;">';
                categoryUi += '<span class="col-md-1" style="padding-top: 3px;"><input type="radio" name="lv' + level + '" value="' + c.id + '" class="category-data" colorCode="' + colorCode + '"></span>';
                // categoryUi += '<span class="col-8 category-name text-break ' + clsInactive + '" style="padding-top: 5px;">' + c.name + '<i class="fa fa-bookmark ml-3" style="color: ' + colorCode + '"></i></span>';
                categoryUi += '<span class="col-md-8 category-name text-break ' + clsInactive + '" style="padding-top: 5px;">' + c.name + '</span>';
                categoryUi += '<span class="col-md-3" style="padding-top: 5px;">';
                categoryUi += '<button class="btn btn-danger btn-sm pull-right" onclick="removeCode(' + c.id + ',' + level + ')" style="margin-left: 3px;"><i class="fa fa-trash"></i></button>';
                categoryUi += '<button class="btn btn-warning btn-sm pull-right" onclick="editCode(' + c.id + ',' + level + ')"><i class="fa fa-pencil"></i></button>';
                categoryUi += '</span>';
                categoryUi += '</li>';
                $('#category-lv' + level).append(categoryUi);
            });
            $(document).trigger('icheck');
        }
    });
};








/**
 * modal codename show
 * @param level
 */
let showParentCategoryName = function (level) {

    console.log("level : ", level);

    $('#category-panel-lv1').hide();
    $('#category-panel-lv2').hide();
    $('#colorCode').attr('disabled', false);

    if (level <= 1) {
        return
    }

    for (let i = level-1; i>0; i--) {
        $('#category-panel-lv' + i).show();
        $('#colorCode').attr('disabled', true);
        $('#colorCode').val($('#parentColorCode').val());
        $('#category-name-lv' + i).text($('input[name="lv' + i + '"]:checked').parents('.category-unit').find('.category-name').text());
    }
};

/**
 * 코드값 수정
 * @param codeId
 * @param level
 */
let editCode = function (codeId, level) {
    $.get('/settings/category-manager/' + codeId, function (resp) {
        console.log("detail", resp);
        let data = resp.data;
        if (checkNullOrEmptyValue(data)) {
            $('#parentId').val(data.parentId);
            $('#name').val(data.name);
            $('#colorCode').val(data.colorCode);
            $('#lv').val(level);
            $('#saveType').val("modify");
            $('#sortOrder').val(data.sortOrder);
            $('#id').val(data.id);

            if (data.activeYn === "Y") {
                $("#category-active").iCheck('check');
            } else {
                $("#category-inactive").iCheck('check');
            }

            showParentCategoryName(data.level);

            $('.modal-title').text('카테고리 수정');
            $('.btnSave').text('수정');
            $('#category-editor').modal('show');
        }
    });
};

let removeCode = function(codeId) {

}

let categorySave = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        saveType = $('#saveType').val(),
        lv = parseInt($(`#lv`).val());

    let formMethod = saveType === "new" ? "post" : "put",
        message = saveType === "new" ? "카테고리가 추가되었습니다." :
        "카테고리가 수정되었습니다.";

    let params = serializeObject({form:$frm[0]}).json();
    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal("카테고리명은 필수입니다.");
        return false;
    }

    console.log("params", params);
    console.log("lv", lv);

    $.ajax({
        url: "/settings/category-manager",
        method: formMethod,
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            twoBtnModal(message, function() {
                if (lv === 1) {
                    location.reload();
                    return
                }
                console.log("level : ", lv)
                $(`.btnModalClose`).trigger(`click`);
                getCategories(params.parentId, lv)
            });
        },
        error:function(error){
            ajaxErrorFieldByModal(error);
        }
    });
}

let minicolors = function() {
    $('.color-code').minicolors({
        control: $(this).attr('data-control') || 'hue',
        defaultValue: $(this).attr('data-defaultValue') || '',
        format: $(this).attr('data-format') || 'hex',
        keywords: $(this).attr('data-keywords') || '',
        inline: $(this).attr('data-inline') === 'true',
        letterCase: $(this).attr('data-letterCase') || 'lowercase',
        opacity: $(this).attr('data-opacity'),
        position: $(this).attr('data-position') || 'bottom',
        swatches: $(this).attr('data-swatches') ? $(this).attr('data-swatches').split('|') : [],
        change: function(value, opacity) {
            if( !value ) return;
            if( opacity ) value += ', ' + opacity;
            if( typeof console === 'object' ) {
                console.log(value);
            }
        },
        theme: 'bootstrap'
    });
}

$(document).ready(onReady)
    .on('click', '.btnAdd', showCategoryAddModal)
    .on('click', '.btnSave', categorySave)
    .on('icheck', function(){
        $('input[type=checkbox], input[type=radio]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_flat-blue'
        });
    }).trigger('icheck')
    .on('ifToggled', '.category-list .category-data', getChildrenCategory);
