let drawEmptyHistoryTable = function() {
    let tag = '';
    tag += '<tr>';
    tag += '<td colSpan="5" class="text-alien-center">';
    tag += '등록된 메모가 없습니다.';
    tag += '</td>';
    tag += '</tr>';
    return tag;
}

let drawEmptyTableBody = function() {
    let tag = '';
    tag += '<tr>';
    tag += '    <td class="text-alien-center" colSpan="7">연동된 매물이 없습니다.</td>';
    tag += '</tr>';
    return tag;
}

let drawRealEstateTable = function(list) {
    let tag = '';
    $.each(list, function(idx, item) {
        tag += '<tr>';
        tag += '<td class="text-alien-center">';
        tag += '<input type="checkbox" class="checkElement" name="realEstateId" value="' + item.realEstateId + '">';
        tag += '</td>';
        tag += '<td class="salePrice" salePrice="' + item.salePrice + '">' + item.salePrice + '억</td>';
        tag += '<td class="address" address="' + item.address + '"><a href="/real-estate/' + item.realEstateId + '/edit" target="_blank">' + item.address + '</a></td>';
        tag += '<td class="lndpclArByPyung">' + item.lndpclArByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="lndpclArByPyung" value="' + item.lndpclArByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="totAreaByPyung">' + item.totAreaByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="totAreaByPyung" value="' + item.totAreaByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="archAreaByPyung">' + item.archAreaByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="archAreaByPyung" value="' + item.archAreaByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="managerName" managerName="' + item.managerName + '">' + item.managerName + '</td>';
        // tag += '<td class="createdAt" createdAt="' + moment(item.createdAt).format("YYYY-MM-DD") + '">' + moment(item.createdAt).format("YYYY-MM-DD") + '</td>';
        tag += '</tr>';
    });
    return tag;
}

let drawSelectedItem = function(realEstate) {
    let tag = '';
    tag += '<tr>';
    tag += '<td class="text-alien-center realEstateId" realEstateId="' + realEstate.realEstateId + '" style="width:30%;"><a href="/real-estate/' + realEstate.realEstateId + '/edit" target="_blank">' + realEstate.address + '</a></td>';
    tag += '<td class="text-alien-center" style="width:15%;">' + realEstate.salePrice + '억원</td>';
    tag += '<td class="text-alien-center" style="width:10%;">' + realEstate.lndpclArByPyung + '평</td>';
    tag += '<td class="text-alien-center" style="width:10%;">' + realEstate.totAreaByPyung + '평</td>';
    tag += '<td class="text-alien-center" style="width:10%;">' + realEstate.archAreaByPyung + '평</td>';
    tag += '<td class="text-alien-center" style="width:15%;">' + realEstate.managerName + '</td>';
    tag += '<td class="text-alien-center" style="width:10%;"><button type="button" class="btn btn-xs btn-danger btnRemove">삭제</button></td>';
    tag += '</tr>';
    return tag;
}

let drawEmptyTableBodyRealSearchModal = function() {
    let tag = '';
    tag += '<tr>';
    tag += '    <td class="text-alien-center" colSpan="7">검색된 매물이 없습니다.</td>';
    tag += '</tr>';
    return tag;
}

let drawDetailRealEstateTable = function(list) {
    let tag = '';
    $.each(list, function(idx, item) {
        tag += '<tr>';
        tag += '<td class="text-alien-center">' + item.realEstateId + '</td>';
        tag += '<td class="salePrice" salePrice="' + item.salePrice + '">' + item.salePrice + '억</td>';
        tag += '<td class="address" address="' + item.address + '"><a href="/real-estate/' + item.realEstateId + '/edit" target="_blank">' + item.address + '</a></td>';
        tag += '<td class="lndpclArByPyung">' + item.lndpclArByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="lndpclArByPyung" value="' + item.lndpclArByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="totAreaByPyung">' + item.totAreaByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="totAreaByPyung" value="' + item.totAreaByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="archAreaByPyung">' + item.archAreaByPyung.toFixed(2) + '평';
        tag += '<input type="hidden" name="archAreaByPyung" value="' + item.archAreaByPyung.toFixed(2) + '" />';
        tag += '</td>';
        tag += '<td class="managerName" managerName="' + item.managerName + '">' + item.managerName + '</td>';
        // tag += '<td class="createdAt" createdAt="' + moment(item.createdAt).format("YYYY-MM-DD") + '">' + moment(item.createdAt).format("YYYY-MM-DD") + '</td>';
        tag += '</tr>';
    });
    return tag;
}
