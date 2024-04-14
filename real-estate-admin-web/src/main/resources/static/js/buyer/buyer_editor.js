const historyRealEstateMap = new Map();

let onReady = function() {
    if (checkNullOrEmptyValue(dto)) {
        setConvertDoubleToInt();
    }
    minicolors();
};

let setConvertDoubleToInt = function() {
    let salePrice = dto.salePrice,
        handCache = dto.handCache,
        exclusiveAreaPy = dto.exclusiveAreaPy,
        landAreaPy = dto.landAreaPy,
        totalAreaPy = dto.totalAreaPy
    ;

    let $frm = $('form[name="frmRegister"]');
    $frm.find('input[name="salePrice"]').val(convertDoubleValue(salePrice));
    $frm.find('input[name="handCache"]').val(convertDoubleValue(handCache));
    $frm.find('input[name="exclusiveAreaPy"]').val(convertDoubleValue(exclusiveAreaPy));
    $frm.find('input[name="landAreaPy"]').val(convertDoubleValue(landAreaPy));
    $frm.find('input[name="totalAreaPy"]').val(convertDoubleValue(totalAreaPy));
}

let convertDoubleValue = function(doubleValue) {
    if (doubleValue % 1 === 0) {
        return doubleValue.toFixed(0);
    }
    return doubleValue.toFixed(1);
}

let addUsageType = function(e) {
    e.preventDefault();

    let id = $(this).val(),
        name = $(this).find('option:selected').attr('name');

    if (!checkNullOrEmptyValue(id)) {
        return;
    }

    if (checkDuplicateUsageType(id)) {
        twoBtnModal("이미 등록된 매입목적입니다.");
        return;
    }

    let tag = drawUsageTypeButton(id, name);
    $('.usageTypeSection').append(tag);
    $('.usageTr').removeClass('hidden');
}

let checkDuplicateUsageType = function(id) {
    let $section = $('.usageTypeSection').find('.btnUsageCode');
    return $section.toArray().some(function (item) {
        let usageTypeId = $(item).attr('usageTypeId');
        return id === usageTypeId;
    });
}

let drawUsageTypeButton = function(id, name) {
    let tags = '';
    tags += '<button type="button" class="btn btn-xs btn-primary btnUsageCode selected margin-top-8" usageTypeId="' + id + '" name="usageTypeId" style="margin-right: 5px;"> ' + name + '</button>';
    return tags
}

let save = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    let buyerGradeCds = $frm.find('#buyerGradeCd option:selected').val();
    if (!checkNullOrEmptyValue(buyerGradeCds)) {
        twoBtnModal("매수자 등급을 설정해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
        return;
    }

    params["managerIds"] = getManagerIds();
    params["buyerGradeCds"] = buyerGradeCds;
    params["purposeCds"] = extractCodeId($('.purposeSection'));
    params["loanCharacterCds"] = extractCodeId($('.loanCharacterSection'));
    params["preferBuildingCds"] = extractCodeId($('.preferBuildingSection'));
    params["investmentTimingCds"] = extractCodeId($('.investmentTimingSection'));


    console.log("params", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    location.href = '/buyer/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let update = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmRegister"]'),
        params = serializeObject({form:$frm[0]}).json();

    let buyerGradeCds = $frm.find('#buyerGradeCd option:selected').val();
    if (!checkNullOrEmptyValue(buyerGradeCds)) {
        twoBtnModal("매수자 등급을 설정해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
        return;
    }

    params["managerIds"] = getManagerIds();
    params["buyerGradeCds"] = buyerGradeCds;
    params["purposeCds"] = extractCodeId($('.purposeSection'));
    params["loanCharacterCds"] = extractCodeId($('.loanCharacterSection'));
    params["preferBuildingCds"] = extractCodeId($('.preferBuildingSection'));
    params["investmentTimingCds"] = extractCodeId($('.investmentTimingSection'));


    console.log("params", params);

    twoBtnModal("수정하시겠습니까?", function () {
        $.ajax({
            url: "/buyer",
            method: 'put',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 수정되었습니다.', function() {
                    location.href = '/buyer/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let changeBtn = function(e) {
    e.preventDefault();

    let code = $(this).find('option:selected').attr('code');
    let isExist = false;
    $.each(dto.processList, function(idx, item) {
        console.log("code : " + code + " item : ", item.processCd);
        if (item.processCd === code) {
            isExist = true;
        }
    });

    console.log("isExist", isExist)

    if (isExist) {
        $('.btnSave').addClass('hidden');
        $('.btnUpdate').removeClass('hidden');
    } else {
        $('.btnUpdate').addClass('hidden');
        $('.btnSave').removeClass('hidden');
    }
}

let toggleSelectButton = function(e) {
    e.preventDefault();

    let $this = $(this);
    if ($this.hasClass('selected')) {
        $this.removeClass('selected');
        $this.removeClass('btn-primary');
        $this.addClass('btn-default');
    } else {
        $this.addClass('selected');
        $this.addClass('btn-primary');
        $this.removeClass('btn-default');
    }
}

let toggleSelectOneButton = function(e) {
    e.preventDefault();

    let $this = $(this),
        $section = $(this).parents('.selected-button-radio-section');

    $section.find('button').each(function() {
        $(this).removeClass("btn-primary");
        $(this).removeClass("selected");
        $(this).addClass("btn-default");
    });

    $this.removeClass("btn-default");
    $this.addClass("btn-primary");
    $this.addClass("selected");
}

let extractCodeId = function(section) {
    let extractCds = '';
    $(section).find('.btnCodeCd').each(function(idx, item) {
        if ($(item).hasClass('selected')) {
            extractCds += $(item).attr('code');
            extractCds += ',';
        }
    });

    if (extractCds.endsWith(',')) {
        extractCds = extractCds.slice(0, -1);
    }
    return extractCds;
}

let showHistoryModal = function(e) {
    e.preventDefault();

    let $modal = $('#historyModal'),
        buyerId = $(this).attr('buyerId'),
        title = $(this).attr('title'),
        name = $(this).attr('name'),
        gradeName = $(this).attr('gradeName'),
        customerName = $(this).attr('customerName'),
        salePrice = $(this).attr('salePrice'),
        preferArea = $(this).attr('preferArea'),
        preferSubway = $(this).attr('preferSubway'),
        preferRoad = $(this).attr('preferRoad'),
        createdAt = $(this).attr('createdAt'),
        landAreaPy = $(this).attr('landAreaPy'),
        totalAreaPy = $(this).attr('totalAreaPy'),
        exclusiveAreaPy = $(this).attr('exclusiveAreaPy'),
        processCd = $(this).attr('processCode')
    ;

    $.ajax({
        url: "/buyer/" + buyerId + "/history",
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let data = result.data,
                detail = data.buyerDetail;

            $modal.find('.modal-title').text('[' + gradeName + '] ' + title);
            $modal.find('#processName').text('[' + name + ']');
            $modal.find('.customerName').text(customerName);
            $modal.find('.salePrice').text(salePrice);
            $modal.find('.preferArea').text(preferArea);
            $modal.find('.preferSubway').text(preferSubway);
            $modal.find('.preferRoad').text(preferRoad);
            $modal.find('.landAreaPy').text(landAreaPy);
            $modal.find('.totalAreaPy').text(totalAreaPy);
            $modal.find('.exclusiveAreaPy').text(exclusiveAreaPy);
            $modal.find('.createdAt').text(createdAt);
            $modal.find('input[name="processCds"]').val(processCd);
            $modal.find('input[name="processName"]').val(name);

            $modal.find('.purposeNameStr').text(detail.purposeNameStr);
            $modal.find('.preferBuildingNameStr').text(detail.preferBuildingNameStr);
            $modal.find('.investmentTimingNameStr').text(detail.investmentTimingNameStr);
            $modal.find('.loanCharacterNameStr').text(detail.loanCharacterNameStr);
            $modal.find('.requestDetail').text(detail.requestDetail);
            $modal.find('input[name="buyerId"]').val(detail.buyerId);
            if (detail.histories.length > 0) {
                $modal.find('.historyTable tbody').html(drawHistoryTable(detail.histories));
            } else {
                $modal.find('.historyTable tbody').html(drawEmptyHistoryTable());
            }
            $modal.modal('show');
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });


}

let drawHistoryTable = function(histories) {
    let tag = '';
    $.each(histories, function(idx, item) {
        tag += '<tr>';
        tag += '<td>' + item.processName + '</td>';
        tag += '<td style="white-space: normal;">' + item.memo + '</td>';
        tag += '<td>' + item.createdByName + '</td>';
        tag += '<td>' + moment(item.createdAt).format("YYYY-MM-DD") + '</td>';
        tag += '<td>';
        tag += '<button class="btn btn-xs btn-default btnRealEstateList" historyId="' + item.historyId + '" data-toggle="modal" data-target="#realEstateListModal">매물정보</button>';
        tag += '</td>';
        tag += '</tr>';
        historyRealEstateMap.set(item.historyId.toString(), item.realEstateList);
    })
    return tag;
}


let addHistory = function(e) {
    e.preventDefault();

    let $modal = $('#historyModal'),
        buyerId = $modal.find('input[name="buyerId"]').val(),
        processCd = $modal.find('input[name="processCds"]').val(),
        processName = $modal.find('input[name="processName"]').val(),
        memo = $modal.find('textarea[name="memo"]').val();

    if (!checkNullOrEmptyValue(memo)) {
        twoBtnModal("메모를 입력해주세요.");
        return;
    }

    let realEstateIds = [];
    $modal.find('.realEstateTable tbody tr').each(function(idx, item) {
        let realEstateId = $(item).find('.realEstateId').attr('realEstateId');
        if (checkNullOrEmptyValue(realEstateId)) {
            realEstateIds.push(realEstateId);
        }
    });


    let params = {
        "processCds" : convertNullOrEmptyValue(processCd),
        "processName" : processName,
        "memo" : memo,
        "realEstateIds" : realEstateIds
    }

    console.log("buyerId : ",  buyerId);
    console.log("params : ", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer/" + buyerId + "/history",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    let tag = drawHistoryTable(result.data);
                    $modal.find('.historyTable tbody').html(tag);
                    initHistoryModal();
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let initHistoryModal = function() {
    let $modal = $('#historyModal');
    $modal.find('textarea[name="memo"]').val('');
    $modal.find('.realEstateTable tbody').html(drawEmptyTableBody());
}

let showHistoryMapModal = function(e) {
    e.preventDefault();

    let $modal = $('#historyMapModal'),
        buyerId = $(this).attr('buyerId'),
        title = $(this).attr('title'),
        gradeName = $(this).attr('gradeName')
    ;

    $('#colorCode').val('');
    $('.minicolors-swatch-color').css('background-color', '');

    $modal.find('.modal-title').text('[' + gradeName + '] ' + title);
    $modal.find('input[name="buyerId"]').val(buyerId);
    $modal.modal('show');
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

let addHistoryMap = function(e) {
    e.preventDefault();

    let $modal = $('#historyMapModal'),
        buyerId = $modal.find('input[name="buyerId"]').val(),
        processName = $modal.find('input[name="processName"]').val(),
        colorCode = $modal.find('input[name="colorCode"]').val(),
        sortOrder = $modal.find('input[name="sortOrder"]').val();

    if (!checkNullOrEmptyValue(processName)) {
        twoBtnModal("단계이름을 입력해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(colorCode)) {
        twoBtnModal("색상코드를 입력해주세요.");
        return;
    }

    let params = {
        "colorCode" : colorCode,
        "processName" : processName,
        "sortOrder" : sortOrder
    }

    console.log("params : ", params);

    twoBtnModal("저장하시겠습니까?", function () {
        $.ajax({
            url: "/buyer/" + buyerId + "/history-map",
            method: 'post',
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (result) {
                console.log("save result : ", result);
                twoBtnModal('정상적으로 저장되었습니다.', function() {
                    location.href = '/buyer/' + result.data + '/edit';
                });
            },
            error:function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let loadTeamMember = function(e) {
    e.preventDefault();

    let $this = $(this),
        teamId = $this.val();

    $.ajax({
        url: "/administrators/team/" + teamId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let adminList = result.data;
            let tag = drawAdministrators(adminList);
            $('.adminList').html(tag);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let drawAdministrators = function(adminList) {
    let tag = '';
    tag += '<option value="">선택해주세요.</option>';
    $.each(adminList, function(idx, admin) {
        tag += '<option value="' + admin.username + '" adminName="' + admin.name + '" adminId="' + admin.adminId + '">' + admin.name + '</option>';
    });
    return tag;
}

let drawManager = function(e) {
    e.preventDefault();

    let $this = $(this),
        username = $this.val(),
        name = $this.find('option:selected').attr('adminName'),
        adminId = parseInt($this.find('option:selected').attr('adminId'))
    ;

    console.log(adminId);

    if (!checkNullOrEmptyValue(adminId) || isNaN(adminId)) {
       return;
    }

    let isExist = false;
    $('.managerSection').find('.btnManager').each(function(idx, item) {
        let id = parseInt($(item).attr('adminId'));
        console.log(adminId, id);
        if (id === adminId) {
            isExist = true;
            return;
        }
    });
    if (isExist) {
        return;
    }

    let tag = '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + adminId + '" username="' + username + '" adminName="' + name + '" style="margin-right: 5px;">' + name + '</button>';
    $('.managerSection').append(tag);
}

let getManagerIds = function() {
    let managerIds = [];
    $('.managerSection').find('.btnManager').each(function(idx, item) {
        let id = parseInt($(item).attr('adminId'));
        managerIds.push(id);
    });
    return managerIds;
}

let removeManager = function(e) {
    e.preventDefault();

    let $this = $(this),
        adminId = parseInt($this.attr('adminId')),
        createdById = parseInt(dto.createdByAdminId)
    ;
    if (adminId === createdById) {
        return;
    }
    twoBtnModal("담당자를 해제하시겠습니까?", function () {
        $this.remove();
    });
}

let searchRealEstate = function(e) {
    e.preventDefault();

    let $modal = $('#searchRealEstateModal'),
        address = $modal.find('input[name="address"]').val(),
        $tbody = $modal.find('.searchTable').find('tbody');

    if (!checkNullOrEmptyValue(address)) {
        twoBtnModal("주소를 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/real-estate/address/" + address,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let tag = '';
            if (result.data.length > 0) {
                tag = drawRealEstateTable(result.data);
                $tbody.html(tag);
                $modal.find('input[name="address"]').val('');
            } else {
                tag = drawEmptyTableBodyRealSearchModal();
                $tbody.html(tag);
            }
            $tbody.html(tag);
            initICheck();

        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let applyRealEstate = function(e) {
    e.preventDefault();

    let $modal = $('#searchRealEstateModal'),
        $tbody = $modal.find('.searchTable tbody');

    let tag = '';
    $tbody.find('tr').each(function(idx, item){
        let isChecked = $(item).find('input[name="realEstateId"]').prop('checked');
        if (isChecked) {
            let realEstate = {
                "realEstateId": $(item).find('input[name="realEstateId"]').val(),
                "salePrice": $(item).find('.salePrice').attr('salePrice'),
                "address": $(item).find('.address').attr('address'),
                "managerName": $(item).find('.managerName').attr('managerName'),
                "lndpclArByPyung": $(item).find('input[name="lndpclArByPyung"]').val(),
                "totAreaByPyung": $(item).find('input[name="totAreaByPyung"]').val(),
                "archAreaByPyung": $(item).find('input[name="archAreaByPyung"]').val(),
                "createdAt": $(item).find('.createdAt').attr('createdAt')
            }
            tag += drawSelectedItem(realEstate);
        }
    });
    $('#historyModal').find('.realEstateTable tbody').html(tag);
    $modal.find('.btnClose').trigger('click');
    $modal.find('input[name="address"]').val('');
    $tbody.html(drawEmptyTableBodyRealSearchModal());
}

let initRealEstateModal = function(e) {
    e.preventDefault();

    let historyId = $(this).attr('historyId'),
        $modal = $('#realEstateListModal'),
        $tbody = $modal.find('.searchTable tbody')
    ;

    let data = historyRealEstateMap.get(historyId);
    console.log("data", data);
    let tag = "";
    if (data.length > 0) {
        tag = drawDetailRealEstateTable(data);
    } else {
        tag = drawEmptyTableBody();
    }
    $tbody.html(tag);
}

$(document).ready(onReady)
    .on('click', '.selected-button-radio-section button', toggleSelectOneButton)
    .on('click', '.selected-button-checkbox-section button', toggleSelectButton)
    .on('click', '.btnSave', save)
    .on('click', '.btnUpdate', update)
    .on('change', '#usageType', addUsageType)
    .on('click', '.btnHistoryModal', showHistoryModal)
    .on('click', '.btnHistoryAdd', addHistory)
    .on('click', '.btnHistoryMapModal', showHistoryMapModal)
    .on('click', '.btnHistoryMapAdd', addHistoryMap)
    .on('change', '.teamList', loadTeamMember)
    .on('change', '.adminList', drawManager)
    .on('click', '.btnManagerRemove', removeManager)
    .on('click', '.btnSearch', searchRealEstate)
    .on('click', '.btnApply', applyRealEstate)
    .on('click', '.btnRealEstateList', initRealEstateModal)
;