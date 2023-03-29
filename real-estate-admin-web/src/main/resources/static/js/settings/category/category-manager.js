var onReady = function() {
    console.log("categories", categories);


    $('#btn-add-lv2, btn-add-lv3').attr('disabled', true);
    getCategories(null, 1);
    minicolors();
};

var showCategoryAddModal = function(e) {
    e.preventDefault();

    var lv = parseInt($(this).data('lv'));
    var parentId = $('#parentId-lv' + lv).val();

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

var getChildrenCategory = function(e) {
    e.preventDefault();

    var $this = $(this),
        checked = $this.prop('checked');

    if (checked) {
        var parentId = $(this).val();
        var level = parseInt($(this).attr('name').replace("lv", "")) + 1;
        var colorCode= $(this).attr('colorCode');

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
var getCategories = function (parentId, level) {
    $('#category-lv' + level).empty();
    $('#btn-add-lv' + level).attr('disabled', false);

    var ajaxUrl = '/settings/category-manager/parent-categories';
    if (checkNullOrEmptyValue(parentId)) {
        ajaxUrl = '/settings/category-manager/children-categories?parentId=' + parentId;
    }

    if (level === 1) {
        $('#category-lv2').empty();
        $('#btn-add-lv2').attr('disabled', true);
    }

    $.get(ajaxUrl, function (resp) {
        console.log("categories", resp);
        if (checkNullOrEmptyValue(resp) && resp.length > 0) {
            $(resp).each(function (idx, c) {
                var clsInactive = c.activeYn === 'N' ? 'inactive' : '';
                var categoryUi = '';
                var colorCode = checkNullOrEmptyValue(c.colorCode) ? c.colorCode : 'white';

                categoryUi += '<li class="display-flex-row col-12 category-unit" style="color: grey;">';
                categoryUi += '<span class="col-1" style="padding-top: 3px;"><input type="radio" name="lv' + level + '" value="' + c.id + '" class="category-data" colorCode="' + colorCode + '"></span>';
                categoryUi += '<span class="col-8 category-name text-break ' + clsInactive + '" style="padding-top: 5px;">' + c.name + '<i class="fa fa-bookmark ml-3" style="color: ' + colorCode + '"></i></span>';
                categoryUi += '<span class="col-3" style="padding-top: 5px;"><button class="btn btn-default btn-sm pull-right" onclick="editCode(' + c.id + ',' + level + ')">수정</button></span>';
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
var showParentCategoryName = function (level) {
    level -= 1;
    $('#category-panel-lv1').hide();
    $('#colorCode').attr('disabled', false);
    if (level > 0) {
        $('#category-panel-lv' + level).show();
        $('#colorCode').attr('disabled', true);
        $('#colorCode').val($('#parentColorCode').val());
        $('#category-name-lv' + level).text($('input[name="lv' + level + '"]:checked').parents('.category-unit').find('.category-name').text());
    }
};

/**
 * 코드값 수정
 * @param codeId
 * @param level
 */
var editCode = function (codeId, level) {
    $.get('/settings/category-manager/' + codeId, function (resp) {
        console.log("detail", resp);
        if (checkNullOrEmptyValue(resp)) {
            $('#parentId').val(resp.parentId);
            $('#name').val(resp.name);
            $('#colorCode').val(resp.colorCode);
            $('#lv').val(level);
            $('#saveType').val("modify");
            $('#sortOrder').val(resp.sortOrder);
            $('#id').val(resp.id);

            if (resp.activeYn === "Y") {
                $("#category-active").iCheck('check');
            } else {
                $("#category-inactive").iCheck('check');
            }

            showParentCategoryName(resp.level);

            $('.modal-title').text('카테고리 수정');
            $('.btnSave').text('수정');
            $('#category-editor').modal('show');
        }
    });
};

var categorySave = function(e) {
    e.preventDefault();

    var $frm = $('form[name="frmRegister"]');
    var saveType = $('#saveType').val();

    var formMethod = saveType === "new" ? "post" : "put";
    var message = saveType === "new" ? "카테고리가 추가되었습니다." :
        "카테고리가 수정되었습니다.";

    var params = serializeObject({form:$frm[0]}).json();

    if (!checkNullOrEmptyValue(params.name)) {
        twoBtnModal("카테고리명은 필수입니다.");
        return false;
    }

    console.log("params", params);

    $.ajax({
        url: "/settings/category-manager",
        method: formMethod,
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            twoBtnModal(message, function() {
                location.reload();
            });
        },
        error:function(error){
            ajaxErrorFieldByModal(error);
        }
    });
};

var minicolors = function() {
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
    .on('ifToggled', '#category-lv1 .category-data', getChildrenCategory);
