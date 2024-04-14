let calendar;
let onReady = function() {
    initCalendar();
}

let initCalendar = function() {
    let calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
        initialDate: '2024-04-14',
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
            if (confirm('Are you sure you want to delete this event?')) {
                arg.event.remove()
            }
        },
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
    }

    console.log("params", params);

    // calendar.addEvent({
    //     title: 'Lunch',
    //     start: '2020-08-08T12:30:00',
    //     end: '2020-08-08T13:30:00'
    // });
    //
    // calendar.render();


    // calendar.addEvent({
    //     title: 'Lunch',
    //     start: '2024-04-14T12:30:00',
    //     end: '2024-04-14T13:30:00'
    // });

    calendar.addEvent({
        title: params.title,
        start: params.startDate,
        end: params.endDate,
        allDay: true
    });

    calendar.render();


    $modal.find('.close').trigger('click');
}

let showScheduleModal = function(arg) {
    let $modal = $('#scheduleModal');
    $modal.find('input[name="title"]').val('');
    $modal.find('input[name="customerName"]').val('');
    $modal.find('textarea[name="memo"]').val('');
    $modal.find('input[name="startDate"]').val(arg.startStr);
    $modal.find('input[name="endDate"]').val(arg.endStr);
    $modal.find('input[name="argStartDate"]').val(arg.start);
    $modal.find('input[name="argEndDate"]').val(arg.end);
    $modal.find('input[name="argAllDay"]').val(arg.allDay);
    $modal.modal("show");
}

$(document).ready(onReady)
    .on('click', '.btnAddSchedule', addScheduleCalendar);