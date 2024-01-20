let loadLandInfoList = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    let isLandInfo = dto.existLandInfo;
    if (isLandInfo === true) {
        url = "/real-estate/land/" + dto.realEstateId;
    } else {
        url = "/real-estate/land/ajax/public-data"
            + "?legalCode=" + dto.legalCode
            + "&landType=" + dto.landType
            + "&bun=" + dto.bun
            + "&ji=" + dto.ji
    }


    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("load land info", result);
            let landList = result.data;
            let $frm = $('form[name="frmLandRegister"]');
            let $table = $frm.find('.land-table');

            if (!checkNullOrEmptyValue(landList)) {
                $frm.find('.btnLandAdd').trigger('click');
                let tag = '';
                tag += '<tr>';
                tag += '<td class="text-center" colspan="8">현재 등록된 필지가 없습니다.</td>';
                tag += '</tr>';
                $table.find('tbody').html(tag);
                $table.find('tfoot').addClass('hidden')
                return;
            }

            let landInfo = landList[0];

            settingLandInfo(landInfo);
            drawLandTable($table, landList);

            if (isLandInfo === true) {
                $frm.find('.btnLandSection').html('');
                $.each(landList, function(idx, item) {
                    let tag = drawLandButton(item);
                    $frm.find('.btnLandSection').append(tag);
                    $frm.find('.btnLandSection .btnLandLoad').last().data('land-data', item);
                });

                calculateLandInfo();
            }

            if (!checkNullOrEmptyValue(dto.realEstateId) || !dto.existLandInfo) {
                $frm.find('.btnLandAdd').trigger('click');
            }
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let loadLandPriceInfoList = function() {

    if (!checkNullOrEmptyValue(dto)) {
        return;
    }

    let url;
    let isLandPriceInfo = dto.existLandPriceInfo;
    if (isLandPriceInfo === true) {
        url = "/real-estate/land/price/" + dto.realEstateId;
    } else {
        url = "/real-estate/land/price/ajax/public-data"
            + "?legalCode=" + dto.legalCode
            + "&landType=" + dto.landType
            + "&bun=" + dto.bun
            + "&ji=" + dto.ji
    }

    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("land price result", result);
            let priceInfo = result.data,
                $frm = $('form[name="frmLandRegister"]'),
                $table = $frm.find('.pblnt-table tbody')
            ;
            if (!checkNullOrEmptyValue(priceInfo)) {
                return;
            }

            let tag = drawPriceTable(priceInfo);
            $table.html(tag);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let landUsageInfoReload = function(e) {
    e.preventDefault();

    let url = "/real-estate/land/usage/ajax/public-data"
        + "?legalCode=" + dto.legalCode
        + "&landType=" + dto.landType
        + "&bun=" + dto.bun
        + "&ji=" + dto.ji
    ;

    twoBtnModal("공공데이터를 불러오겠습니까?", function () {
        $.ajax({
            url: url,
            method: "get",
            type: "json",
            contentType: "application/json",
            success: function(result) {
                console.log("load land usage info", result);
                let data = result.data;
                if (!checkNullOrEmptyValue(data)) {
                    return;
                }

                let $frm = $('form[name="frmLandRegister"]');
                $frm.find('.prposAreaDstrcNmList').html(data.prposAreaDstrcNmList);
            },
            error: function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}

let pblntInfoReload = function(e) {
    e.preventDefault();

    let url = "/real-estate/land/price/ajax/public-data"
        + "?legalCode=" + dto.legalCode
        + "&landType=" + dto.landType
        + "&bun=" + dto.bun
        + "&ji=" + dto.ji
    ;

    twoBtnModal("공공데이터를 불러오겠습니까?", function () {
        $.ajax({
            url: url,
            method: "get",
            type: "json",
            contentType: "application/json",
            success: function(result) {
                console.log("land price result", result);

                let priceInfo = result.data,
                    $frm = $('form[name="frmLandRegister"]'),
                    $table = $frm.find('.pblnt-table tbody')
                ;
                if (!checkNullOrEmptyValue(priceInfo)) {
                    return;
                }

                let tag = drawPriceTable(priceInfo);
                $table.html(tag);
            },
            error: function(error){
                ajaxErrorFieldByText(error);
            }
        });
    });
}


let drawPriceTable = function(priceInfo) {
    let tag = '';

    $.each(priceInfo, function(idx, item) {
        tag += '<tr>';
        tag += '<th class="text-alien-center thead-light pclndStdrYear pnu landPriceId" pclndStdrYear="' + item.pclndStdrYear + '" pnu="' + item.pnu + '" landPriceId="' + convertNullOrEmptyValue(item.landPriceId) + '">' + item.pclndStdrYear + '</th>';
        tag += '<td class="text-alien-center pblntfPclnd" pblntfPclnd="' + item.pblntfPclnd + '">' + addCommasToNumber(item.pblntfPclnd) + '</td>';
        tag += '<td class="text-alien-center pblntfPclndPy" pblntfPclndPy="' + item.pblntfPclndPy + '">' + addCommasToNumber(item.pblntfPclndPy) + '</td>';
        if (item.changeRate > 0) {
            tag += '<td class="text-alien-center changeRate" changeRate="' + item.changeRate + '">' + item.changeRate + '%<span style="color: darkred;">▲</span></td>';
        } else if (item.changeRate < 0) {
            tag += '<td class="text-alien-center changeRate" changeRate="' + item.changeRate + '">' + item.changeRate + '%<span style="color: darkblue;">▼</span></td>';
        } else {
            tag += '<td class="text-alien-center changeRate" changeRate="' + item.changeRate + '">' + item.changeRate + '%</td>';
        }
        tag += '</tr>';
    });

    return tag;
}

let drawLandTable = function($table, landList) {

    let calLndpclAr = 0,
        calLndpclArByPyung = 0,
        calPblntfPclnd = 0,
        calPblntfPclndByPyung = 0,
        calTotalPblntfPclnd = 0,
        calTotalPblntfPclndByPyung = 0;

    let tag = '';
    $.each(landList, function(idx, item) {

        let pblndfPclndByPyung = 0;
        if (checkNullOrEmptyValue(item.pblntfPclndByPyung)) {
            pblndfPclndByPyung = item.pblntfPclndByPyung;
        }

        if (isNaN(item.pblntfPclnd)) {
            return;
        }

        if (pblndfPclndByPyung === 0) {
            pblndfPclndByPyung = Number(item.pblntfPclnd) * 3.305785
        }
        let totalPblntfPclnd = convertNullOrEmptyValue(item.totalPblntfPclnd);
        if (isNaN(item.totalPblntfPclnd) || isNaN(item.lndpclAr)) {
            return;
        }
        if (totalPblntfPclnd === 0) {
            totalPblntfPclnd = Number(item.pblntfPclnd) * Number(item.lndpclAr);
        }
        let totalPblntfPclndByPyung = convertNullOrEmptyValue(item.totalPblntfPclndByPyung);
        if (isNaN(item.totalPblntfPclndByPyung) || isNaN(item.lndpclArByPyung)) {
            return;
        }
        if (totalPblntfPclndByPyung === 0) {
            totalPblntfPclndByPyung = pblndfPclndByPyung * Number(item.lndpclArByPyung);
        }

        console.log("drawLandTable", item);

        tag += '<tr>';
        if (checkNullOrEmptyValue(dto.realEstateId)) {
            tag += '<td class="text-alien-center min-width-130">' + item.address + '</td>';
        } else {
            tag += '<td class="text-alien-center min-width-130">' + item.lnmLndcgrSmbol + '</td>';
        }
        tag += '<td class="text-alien-center" style="min-width:70px;">' + item.lndcgrCodeNm + '</td>';
        tag += '<td class="text-alien-center" style="min-width:90px;">' + item.lndpclAr + '㎡</td>';
        tag += '<td class="text-alien-center" style="min-width:90px;">' + item.lndpclArByPyung + '평</td>';
        tag += '<td class="text-alien-center min-width-130">' + addCommasToNumber(item.pblntfPclnd) + '원/㎡</td>';
        tag += '<td class="text-alien-center min-width-130">' + addCommasToNumber(pblndfPclndByPyung) + '원/평</td>';
        tag += '<td class="text-alien-center min-width-130">' + addCommasToNumber(totalPblntfPclnd) + '원/㎡</td>';
        tag += '<td class="text-alien-center min-width-130">' + addCommasToNumber(totalPblntfPclndByPyung) + '원/평</td>';
        tag += '</tr>';

        calLndpclAr += Number(item.lndpclAr);
        calLndpclArByPyung += Number(item.lndpclArByPyung);
        calPblntfPclnd += Number(item.pblntfPclnd);
        calPblntfPclndByPyung += pblndfPclndByPyung;
        calTotalPblntfPclnd += totalPblntfPclnd;
        calTotalPblntfPclndByPyung += totalPblntfPclndByPyung;
    });
    
    if (calLndpclAr > 0) {
        calLndpclAr = calLndpclAr.toFixed(2);
    }
    if (calLndpclArByPyung > 0) {
        calLndpclArByPyung = calLndpclArByPyung.toFixed(2);
    }
    if (calPblntfPclnd > 0) {
        calPblntfPclnd = calPblntfPclnd.toFixed(2);
    }
    if (calPblntfPclndByPyung > 0) {
        calPblntfPclndByPyung = calPblntfPclndByPyung.toFixed(0);
    }
    if (calTotalPblntfPclnd > 0) {
        calTotalPblntfPclnd = calTotalPblntfPclnd.toFixed(0);
    }
    if (calTotalPblntfPclndByPyung > 0) {
        calTotalPblntfPclndByPyung = calTotalPblntfPclndByPyung.toFixed(0);
    }

    $table.find('tbody').html(tag);
    $table.find('tfoot .calLndpclAr').text(calLndpclAr + '㎡');
    $table.find('tfoot .calLndpclArByPyung').text(calLndpclArByPyung + '평');
    $table.find('tfoot .calPblntfPclnd').text(addCommasToNumber(calPblntfPclnd) + '원/㎡');
    $table.find('tfoot .calPblntfPclndByPyung').text(addCommasToNumber(calPblntfPclndByPyung) + '원/평');
    $table.find('tfoot .calTotalPblntfPclnd').text(addCommasToNumber(calTotalPblntfPclnd) + '원/㎡');
    $table.find('tfoot .calTotalPblntFPclndByPyung').text(addCommasToNumber(calTotalPblntfPclndByPyung) + '원/평');
    $table.find('tfoot').removeClass('hidden');
}

let settingLandInfo = function(landInfo) {
    let $frm = $('form[name="frmLandRegister"]');
    $frm.find('.lndpclAr').val(landInfo.lndpclAr);
    $frm.find('.lndpclArByPyung').val(landInfo.lndpclArByPyung);
    $frm.find('.pblntfPclnd').val(addCommasToNumber(landInfo.pblntfPclnd));
    $frm.find('.totalPblntfPclnd').val(addCommasToNumber(landInfo.totalPblntfPclnd));
    $frm.find('input[name="totalPblntfPclndByPyung"]').val(landInfo.totalPblntfPclndByPyung);
    $frm.find('.lndcgrCodeNm').val(landInfo.lndcgrCodeNm);
    $frm.find('.prposArea1Nm').val(landInfo.prposArea1Nm);
    $frm.find('.ladUseSittnNm').val(landInfo.ladUseSittnNm);
    $frm.find('.roadSideCodeNm').val(landInfo.roadSideCodeNm);
    $frm.find('.tpgrphHgCodeNm').val(landInfo.tpgrphHgCodeNm);
    $frm.find('.tpgrphFrmCodeNm').val(landInfo.tpgrphFrmCodeNm);
    $frm.find('.roadWidth').val(landInfo.roadWidth);
    $frm.find('textarea[name="etcInfo"]').text(landInfo.etcInfo);
    $frm.find('input[name="pnu"]').val(landInfo.pnuStr);
    if (landInfo.commercialYn === 'Y') {
        $frm.find('input[name="commercialYn"]').iCheck('check');
    } else {
        $frm.find('input[name="commercialYn"]').iCheck('uncheck');
    }

    $frm.find('.prposAreaDstrcNmList').text(landInfo.prposAreaDstrcNmList);
    $frm.find('input[name="prposAreaDstrcNmList"]').val(landInfo.prposAreaDstrcNmList);
    $frm.find('input[name="prposAreaDstrcCodeList"]').val(landInfo.prposAreaDstrcCodeList);
    $frm.find('input[name="posList"]').val(landInfo.posList);
}

let landInfoAdd = function(e) {
    e.preventDefault();

    let $frmLand = $('form[name="frmLandRegister"]'),
        params = serializeObject({form:$frmLand[0]}).json(),
        pnu = $frmLand.find('input[name="pnu"]').val(),
        $section = $frmLand.find('.btnLandSection'),
        isAlready = false;

    params.pnu = pnu;
    params.pblntfPclnd = removeComma(params.pblntfPclnd);
    params.totalPblntfPclnd = removeComma(params.totalPblntfPclnd);
    params.address = $frmLand.find('input[name="address"]').val();

    console.log("landInfoAdd params", params)

    $section.find('.btnLandLoad').each(function(idx, item) {
        let alreadyPnu = $(item).attr('pnu');
        if (pnu === alreadyPnu) {
            twoBtnModal("이미 등록된 토지 정보입니다. 정보를 갱신하겠습니까?", function() {
                $(item).data('land-data', params);
            });
            isAlready = true;
            return false;
        }
    });

    if (isAlready === true) {
        return;
    }

    let tag = drawLandButton(params);
    $frmLand.find('.btnLandSection').append(tag);
    $frmLand.find('.btnLandSection .btnLandLoad').last().data('land-data', params);
    calculateLandInfo();
}

let calculateLandInfo = function() {

    let $frmLand = $('form[name="frmLandRegister"]'),
        $section = $frmLand.find('.btnLandSection');

    let totalLndpclArByPyung = 0;
    let totalLndpcl = 0;
    let landLength = $section.find('.btnLandLoad').length;

    $section.find('.btnLandLoad').each(function(idx, item) {
        let landData = $(item).data('land-data');
        totalLndpclArByPyung += parseFloat(landData.lndpclArByPyung);
        totalLndpcl += parseFloat(landData.lndpclAr);
    });


    console.log("before calculate totalLndpcl : ", totalLndpcl)
    console.log("before calculate totalLndpclArByPyung : ", totalLndpclArByPyung)
    totalLndpcl = Math.floor(totalLndpcl * 100) / 100;
    totalLndpclArByPyung = Math.floor(totalLndpclArByPyung * 100) / 100;

    console.log("after calculate totalLndpcl : ", totalLndpcl)
    console.log("after calculate totalLndpclArByPyung : ", totalLndpclArByPyung)

    $frmLand.find('input[name="totalLndpclAr"]').val(totalLndpcl);
    $frmLand.find('input[name="totalLndpclArByPyung"]').val(totalLndpclArByPyung);
    $frmLand.find('.pyung').html(totalLndpclArByPyung);
    $frmLand.find('.landSize').html(landLength);
    $frmLand.find('.area').html(totalLndpcl);
}

let drawLandButton = function(data) {
    let tag = '';

    if (checkNullOrEmptyValue(data.landId)) {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="' + convertNullOrEmptyValue(data.landId ) + '">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeLandBtn" aria-hidden="true"></i></button>'
    } else {
        tag += '<button class="btn btn-sm btn-default btnLandLoad margin-right-3" pnu="' + data.pnu + '" landId="">' + data.address + '&nbsp;&nbsp;<i class="fa fa-times removeLandBtn" aria-hidden="true"></i></button>'
    }

    return tag;
}


let loadLandInfoById = function(e) {
    e.preventDefault();

    let $this = $(this),
        landInfo = $this.data('land-data');

    if (!checkNullOrEmptyValue(landInfo)) {
        return;
    }
    console.log("land data", landInfo);

    let $frm = $('form[name="frmLandRegister"]');
    $frm.find('input[name="address"]').val(landInfo.address);
    $frm.find('.lndpclAr').val(landInfo.lndpclAr);
    $frm.find('.lndpclArByPyung').val(landInfo.lndpclArByPyung);
    $frm.find('.pblntfPclnd').val(addCommasToNumber(landInfo.pblntfPclnd));
    $frm.find('.totalPblntfPclnd').val(addCommasToNumber(landInfo.totalPblntfPclnd));
    $frm.find('.lndcgrCodeNm').val(landInfo.lndcgrCodeNm);
    $frm.find('.prposArea1Nm').val(landInfo.prposArea1Nm);
    $frm.find('.ladUseSittnNm').val(landInfo.ladUseSittnNm);
    $frm.find('.roadSideCodeNm').val(landInfo.roadSideCodeNm);
    $frm.find('.tpgrphHgCodeNm').val(landInfo.tpgrphHgCodeNm);
    $frm.find('.tpgrphFrmCodeNm').val(landInfo.tpgrphFrmCodeNm);
    $frm.find('.roadWidth').val(landInfo.roadWidth);
    $frm.find('.textarea[name="etcInfo"]').val(landInfo.etcInfo);
    $frm.find('input[name="pnu"]').val(landInfo.pnu);
    $frm.find('.prposAreaDstrcNmList').text(landInfo.prposAreaDstrcNmList);
    $frm.find('input[name="prposAreaDstrcNmList"]').val(landInfo.prposAreaDstrcNmList);
    $frm.find('input[name="prposAreaDstrcCodeList"]').val(landInfo.prposAreaDstrcCodeList);
    $frm.find('input[name="posList"]').val(landInfo.posList);
}

let assembleLandParams = function() {

    let landInfoList = [];

    let $frmLand = $('form[name="frmLandRegister"]'),
        commercialYn = $frmLand.find('input[name="commercialYn"]').is(":checked") ? "Y" : "N";

    let $section = $frmLand.find('.btnLandSection'),
        addBtnLength = $section.find('.btnLandLoad').length;

    if (addBtnLength === 0) {
        twoBtnModal('저장하려는 토지 정보를 추가해주세요.');
        return landInfoList;
    }


    $frmLand.find('.btnLandSection .btnLandLoad').each(function (idx, item) {
        let landData = $(item).data('land-data');
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclAr = landData.lndpclAr.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.lndpclArByPyung = landData.lndpclArByPyung.replaceAll(',', '');
        }
        if (typeof landData.lndpclAr === 'string') {
            landData.pblntfPclnd = landData.pblntfPclnd.replaceAll(',', '');
        }
        if (typeof landData.totalPblntfPclnd === 'string') {
            landData.totalPblntfPclnd = landData.totalPblntfPclnd.replaceAll(',', '');
        }
        landData.realEstateId = dto.realEstateId;
        landData.commercialYn = commercialYn;
        landInfoList.push(landData);
    });

    return landInfoList;
}

let searchAddress = function(e) {
    e.preventDefault();

    let $modal = $('#landModal');
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
            $modal.find(`input[name="address"]`).val(data.address);
            loadKakaoModalMap(data.address)
        }
    }).open();
}

let showLandModal = function(e) {
    e.preventDefault();

    let $landModal = $('#landModal');
    $landModal.find('input[name="address"]').val('');
    $landModal.find('input[name="bun"]').val('');
    $landModal.find('input[name="ji"]').val('');
    $landModal.find('select[name="landType"]').val('general');
    $landModal.find('input[name="bCode"]').val('');
    $landModal.find('input[name="hCode"]').val('');

    let tag = '';
    tag += '<span class="margin-bottom-10">지도</span>';
    tag += '<p style="font-size: 12px; margin-top: 20px; color: #7987a1;">';
    tag +=     '검색버튼을 클릭해서';
    tag +=     '<br/>';
    tag +=     '주소를 입력해주세요.';
    tag += '</p>';
    $landModal.find('#modalMap').html(tag);
    $landModal.modal('show');
}


let applyLandInfo = function(e) {
    e.preventDefault();

    let $frm = $('form[name="frmLandRegister"]'),
        $modal = $('#landModal'),
        address = $('#landModal').find('input[name="address"]').val(),
        bCode = $modal.find('input[name="bCode"]').val();

    if (!checkNullOrEmptyValue(bCode)) {
        bCode = $modal.find('select[name="dong"] option:selected').val();
    }

    if (!checkNullOrEmptyValue(address) && checkNullOrEmptyValue(bCode)) {
        let sido = $modal.find('select[name="sido"] option:selected').attr('name');
        let gungu = $modal.find('select[name="gungu"] option:selected').attr('name');
        let dong = $modal.find('select[name="dong"] option:selected').attr('name');
        let bun = $modal.find('input[name="bun"]').val();
        let ji = $modal.find('input[name="ji"]').val();
        address = sido + " " + gungu + " " + dong + " " + bun;
        if (checkNullOrEmptyValue(ji)) {
            address += "-" + ji;
        }
    }

    let url = "/real-estate/land/ajax/public-data"
        + "?legalCode=" + bCode
        + "&landType=" + $modal.find('select[name="landType"] option:selected').val()
        + "&bun=" + $modal.find('input[name="bun"]').val()
        + "&ji=" + $modal.find('input[name="ji"]').val()
    ;

    $.ajax({
        url: url,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("load land info", result);
            let landList = result.data;
            if (!checkNullOrEmptyValue(landList)) {
                return;
            }
            let landInfo = landList[0];
            $frm.find('input[name="address"]').val(address);
            $frm.find('input[name="pnu"]').val(landInfo.pnu);
            settingLandInfo(landInfo);
        },
        error: function(error){
        }
    });

    $('.btnModalClose').trigger('click');
}

let removeLandBtn = function(e) {
    e.preventDefault();
    $(this).parents('button').remove();
    calculateLandInfo();
}