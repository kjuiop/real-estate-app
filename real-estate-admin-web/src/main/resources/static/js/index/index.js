let calendar;
let onReady = function() {
    initCalendar(schedulers);
    if (checkNullOrEmptyValue(errorMessage)) {
        twoBtnModal(errorMessage);
    }
    if (checkNullOrEmptyValue(schedulerId)) {
        loadSchedulerEditModal(schedulerId);
    }
}

let initCalendar = function(schedulers) {

    let events = convertSchedulers(schedulers);
    let calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap',
        height: 650,
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
        locale: "ko",
        initialDate: moment().format('YYYY-MM-DD'),
        navLinks: true, // can click day/week names to navigate views
        businessHours: true, // display business hours
        editable: true,
        dayMaxEvents: true,
        selectable: true,
        selectMirror: true,
        select: function(arg) {
            console.log("arg", arg);
            showScheduleModal(arg);
        },
        eventClick: function(arg) {
            getScheduleModal(arg);
        },
        events: events
    });
    calendar.render();
}


let addScheduleCalendar = function(e) {
    e.preventDefault();

    let $modal = $('#scheduleModal');

    let params = {
        "argStartDate" : $modal.find('input[name="argStartDate"]').val(),
        "argEndDate" : $modal.find('input[name="argEndDate"]').val(),
        "argAllDay" : $modal.find('input[name="argAllDay"]').val(),
        "startDate" : $modal.find('input[name="startDate"]').val(),
        "endDate" : $modal.find('input[name="endDate"]').val(),
        "title" : $modal.find('input[name="title"]').val(),
        "customerName" : $modal.find('input[name="customerName"]').val(),
        "memo" : $modal.find('textarea[name="memo"]').val(),
        "managerIds": getManagerIds($modal),
        "priorityOrderCds": $modal.find('.priorityOrderCds option:selected').val(),
        "buyerId" : $modal.find('.buyerList').val(),
        "processCds" : $modal.find('.processCds option:selected').val(),
    }

    if (!checkNullOrEmptyValue(params.priorityOrderCds)) {
        twoBtnModal("우선순위를 설정해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("프로젝트, 고객을 입력해주세요.");
        return;
    }

    console.log("params", params);

    $.ajax({
        url: "/scheduler",
        method: 'post',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log("save result : ", result);
            location.href = "/";
            // calendar.addEvent({
            //     id: result.data.toString(),
            //     groupId: result.data.toString(),
            //     title: params.title,
            //     start: params.startDate,
            //     end: params.endDate,
            //     allDay: true
            // });
            // calendar.render();
            // $modal.find('.close').trigger('click');
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let updateScheduleCalendar = function(e) {
    e.preventDefault();

    let $modal = $('#scheduleEditModal');

    let params = {
        "argStartDate" : $modal.find('input[name="argStartDate"]').val(),
        "argEndDate" : $modal.find('input[name="argEndDate"]').val(),
        "argAllDay" : $modal.find('input[name="argAllDay"]').val(),
        "startDate" : $modal.find('input[name="startDate"]').val(),
        "endDate" : $modal.find('input[name="endDate"]').val(),
        "title" : $modal.find('input[name="title"]').val(),
        "customerName" : $modal.find('input[name="customerName"]').val(),
        "memo" : $modal.find('textarea[name="memo"]').val(),
        "managerIds": getManagerIds($modal),
        "schedulerId": $modal.find('input[name="schedulerId"]').val(),
        "priorityOrderCds": $modal.find('.priorityOrderCds option:selected').val(),
        "buyerId" : $modal.find('.buyerList option:selected').val(),
        "processCds" : $modal.find('.processCds option:selected').val(),
    }

    if (!checkNullOrEmptyValue(params.priorityOrderCds)) {
        twoBtnModal("우선순위를 설정해주세요.");
        return;
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("프로젝트, 고객을 입력해주세요.");
        return;
    }

    console.log("params", params);

    $.ajax({
        url: "/scheduler",
        method: 'put',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            $modal.find('.close').trigger('click');
            location.href = "/";
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let getManagerIds = function($modal) {
    let managerIds = [];
    $modal.find('.managerSection').find('.btnManager').each(function(idx, item) {
        let id = parseInt($(item).attr('adminId'));
        managerIds.push(id);
    });
    return managerIds;
}

let showScheduleModal = function(arg) {

    let $modal = $('#scheduleModal');
    $modal.find('input[name="schedulerId"]').val('');
    $modal.find('input[name="title"]').val('');
    $modal.find('input[name="customerName"]').val('');
    $modal.find('textarea[name="memo"]').val('');
    $modal.find('input[name="startDate"]').val(moment(arg.startStr).startOf('day').format('YYYY-MM-DDTHH:mm'));
    $modal.find('input[name="endDate"]').val(moment(arg.endStr).endOf('day').format('YYYY-MM-DDTHH:mm'));
    $modal.find('input[name="argStartDate"]').val(arg.start);
    $modal.find('input[name="argEndDate"]').val(arg.end);
    $modal.find('input[name="argAllDay"]').val(arg.allDay);
    $modal.find('.adminList').val('');
    $modal.find('.buyerList').val('');

    let tag = '<button type="button" class="btn btn-xs btn-default btnManager" adminId="' + loginUser.adminId + '" username="' + loginUser.username + '" style="margin-right: 5px; margin-top: 3px;">' + loginUser.name + '</button>';
    $modal.find('.managerSection').html(tag);
    $modal.modal("show");
}

let loadSchedulerEditModal = function(schedulerId) {

    $.ajax({
        url: "/scheduler/" + schedulerId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let scheduler = result.data;
            showSchedulerEditModal(null, scheduler);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let getScheduleModal = function(args) {

    let schedulerId = args.event.id;

    $.ajax({
        url: "/scheduler/" + schedulerId,
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            let scheduler = result.data;
            showSchedulerEditModal(args, scheduler);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let showSchedulerEditModal = function(args, scheduler) {
    let $modal = $('#scheduleEditModal');
    $modal.find('input[name="schedulerId"]').val(scheduler.schedulerId);
    $modal.find('input[name="title"]').val(scheduler.title);
    $modal.find('input[name="customerName"]').val(scheduler.customerName);
    $modal.find('textarea[name="memo"]').text(scheduler.memo);
    $modal.find('input[name="startDate"]').val(moment(scheduler.startDate).startOf('day').format('YYYY-MM-DDTHH:mm'));
    $modal.find('input[name="endDate"]').val(moment(scheduler.endDate).endOf('day').format('YYYY-MM-DDTHH:mm'));
    $modal.find('.priorityOrderCds').val(scheduler.priorityOrderCds);
    if (checkNullOrEmptyValue(args)) {
        $modal.find('input[name="argStartDate"]').val(args.start);
        $modal.find('input[name="argEndDate"]').val(args.end);
        $modal.find('input[name="argAllDay"]').val(args.allDay);
    }
    if (checkNullOrEmptyValue(scheduler.buyerId)) {
        $modal.find('.buyerList').val(scheduler.buyerId);
        let tag = '<a href="/buyer/' + scheduler.buyerId + '/edit" class="btn btn-xs btn-primary" target="_blank">매수자 상세정보</a>';
        $modal.find('.buyerLink').html(tag);
    } else {
        $modal.find('.buyerList').val("");
        $modal.find('.buyerLink').html("");
    }

    if (checkNullOrEmptyValue(scheduler.processCds)) {
        $modal.find('.processCds').val(scheduler.processCds);
    } else {
        $modal.find('.processCds').val('');
    }

    console.log("scheduler", scheduler);
    if (checkNullOrEmptyValue(scheduler.managers) && scheduler.managers.length > 0) {
        let tag = "";
        $.each(scheduler.managers, function(idx, item) {
            tag += '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + item.adminId + '" username="' + item.username + '" adminName="' + item.name + '" style="margin-right: 5px; margin-top:3px;">' + item.name + '</button>';
        });
        $modal.find('.managerSection').html(tag);
    }


    $modal.modal("show");
}

let drawManager = function(e) {
    e.preventDefault();

    let $this = $(this),
        $modal = $(this).parents('.modal-box'),
        username = $this.val(),
        name = $this.find('option:selected').attr('adminName'),
        adminId = parseInt($this.find('option:selected').attr('adminId'))
    ;

    console.log(adminId);

    if (!checkNullOrEmptyValue(adminId) || isNaN(adminId)) {
        return;
    }

    let isExist = false;
    $modal.find('.managerSection').find('.btnManager').each(function(idx, item) {
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

    let tag = '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + adminId + '" username="' + username + '" adminName="' + name + '" style="margin-right: 5px; margin-top:3px;">' + name + '</button>';
    $modal.find('.managerSection').append(tag);
}

let convertSchedulers = function(data) {
    let schedulers = [];

    $.each(data, function(idx, item) {
        let scheduler = {
            "id": item.schedulerId.toString(),
            "groupId": item.schedulerId.toString(),
            "title": item.title,
            "start": moment(item.startDate).format('YYYY-MM-DD'),
            "end": moment(item.endDate).format('YYYY-MM-DD'),
        }
        if (checkNullOrEmptyValue(item.colorCode)) {
            scheduler.backgroundColor = item.colorCode;
            scheduler.borderColor = item.colorCode;
        }
        schedulers.push(scheduler);
    });

    console.log("schedulers", schedulers);

    return schedulers;
}

let removeSchedule = function(e) {
    e.preventDefault();

    let $modal = $('#scheduleEditModal'),
        scheduleId = $modal.find('input[name="schedulerId"]').val();

    $.ajax({
        url: "/scheduler/" + scheduleId,
        method: 'delete',
        type: "json",
        contentType: "application/json",
        success: function (result) {
            $modal.find('.close').trigger('click');
            location.href = "/";
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let addComment = function(e) {
    if (e.keyCode !== 13) {
        return;
    }

    let $modal = $('#scheduleEditModal'),
        schedulerId = $modal.find('input[name="schedulerId"]').val(),
        comment = $(this).val();

    let params = {
        "comment": comment,
    }

    $.ajax({
        url: "/scheduler/" + schedulerId + "/comment",
        method: 'post',
        type: "json",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            $modal.find('input[name="comment"]').val('');

            let comment = result.data;
            let tag = drawComment(comment);
            $modal.find('tbody').append(tag);
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let drawComment = function(comment) {
    if (!checkNullOrEmptyValue(comment)) {
        return;
    }

    let tag = "";
    tag += '<tr>';
    tag += '    <td class="display-flex-column"';
    tag += '        style="background-color: #f8f9fa; border-top: 0; padding: 0 10px 10px; border-radius: 10px;">';
    tag += '        <div class="row margin-bottom-5">';
    tag += '            <div class="col-md-8">';
    tag += '                <label class="control-label padding-top-6 font-size-14">';
    tag += comment.createdName;
    tag += '                </label>';
    tag += '            </div>';
    tag += '            <div class="col-md-4">';
    tag += '                <label class="padding-top-6 font-weight-normal pull-right">';
    tag += moment(comment.createdAt).format('YYYY-MM-DD');
    tag += '                </label>';
    tag += '            </div>';
    tag += '        </div>';
    tag += '        <div class="row">';
    tag += '            <div class="col-md-12">';
    tag += comment.comment;
    tag += '            </div>';
    tag += '        </div>';
    tag += '    </td>';
    tag += '</tr>';

    return tag;
}


/**
 events: [
 {
                title: 'Business Lunch',
                start: '2023-01-03T13:00:00',
                constraint: 'businessHours'
            },
 {
                title: 'Meeting',
                start: '2023-01-13T11:00:00',
                constraint: 'availableForMeeting', // defined below
                color: '#257e4a'
            },
 {
                title: 'Conference',
                start: '2023-01-18',
                end: '2023-01-20'
            },
 {
                title: 'Party',
                start: '2023-01-29T20:00:00'
            },

 // areas where "Meeting" must be dropped
 {
                groupId: 'availableForMeeting',
                start: '2023-01-11T10:00:00',
                end: '2023-01-11T16:00:00',
                display: 'background'
            },
 {
                groupId: 'availableForMeeting',
                start: '2023-01-13T10:00:00',
                end: '2023-01-13T16:00:00',
                display: 'background'
            },

 // red areas where no events can be dropped
 {
                start: '2023-01-24',
                end: '2023-01-28',
                overlap: false,
                display: 'background',
                color: '#ff9f89'
            },
 {
                start: '2023-01-06',
                end: '2023-01-08',
                overlap: false,
                display: 'background',
                color: '#ff9f89'
            }
 ]
 */



$(document).ready(onReady)
    .on('change', '.adminList', drawManager)
    .on('click', '.btnAddSchedule', addScheduleCalendar)
    .on('click', '.btnEditSchedule', updateScheduleCalendar)
    .on('click', '.btnRemove', removeSchedule)
    .on('keydown', 'input[name="comment"]', addComment)
;
