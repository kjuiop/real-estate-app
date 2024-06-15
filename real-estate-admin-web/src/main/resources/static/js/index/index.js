let calendar;
let onReady = function() {
    getCalendars();

    if (checkNullOrEmptyValue(errorMessage)) {
        twoBtnModal(errorMessage);
    }
}

let getCalendars = function() {

    $.ajax({
        url: "/scheduler",
        method: "get",
        type: "json",
        contentType: "application/json",
        success: function(result) {
            console.log("result", result);
            initCalendar(result.data);
        },
        error: function(error){
            ajaxErrorFieldByText(error);
        }
    });
}

let initCalendar = function(schedulers) {

    let events = convertSchedulers(schedulers);
    let calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        height: 650,
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
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
        "managerIds": getManagerIds(),
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
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
            calendar.addEvent({
                id: result.data.toString(),
                groupId: result.data.toString(),
                title: params.title,
                start: params.startDate,
                end: params.endDate,
                allDay: true
            });
            calendar.render();
            $modal.find('.close').trigger('click');
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
        "managerIds": getManagerIds(),
        "schedulerId": $modal.find('input[name="schedulerId"]').val(),
    }

    if (!checkNullOrEmptyValue(params.title)) {
        twoBtnModal("제목을 입력해주세요.");
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
            location.reload();
        },
        error:function(error){
            ajaxErrorFieldByText(error);
        }
    });

}

let getManagerIds = function() {
    let managerIds = [];
    $('.managerSection').find('.btnManager').each(function(idx, item) {
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
    $modal.modal("show");
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
    $modal.find('input[name="argStartDate"]').val(args.start);
    $modal.find('input[name="argEndDate"]').val(args.end);
    $modal.find('input[name="argAllDay"]').val(args.allDay);

    console.log("scheduler", scheduler);
    if (checkNullOrEmptyValue(scheduler.managers) && scheduler.managers.length > 0) {
        let tag = "";
        $.each(scheduler.managers, function(idx, item) {
            tag += '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + item.adminId + '" username="' + item.username + '" adminName="' + item.name + '" style="margin-right: 5px;">' + item.name + '</button>';
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

    let tag = '<button type="button" class="btn btn-xs btn-default btnManager btnManagerRemove" adminId="' + adminId + '" username="' + username + '" adminName="' + name + '" style="margin-right: 5px;">' + name + '</button>';
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
        schedulers.push(scheduler);
    });

    console.log("schedulers", schedulers);

    return schedulers;
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
    .on('click', '.btnEditSchedule', updateScheduleCalendar);
