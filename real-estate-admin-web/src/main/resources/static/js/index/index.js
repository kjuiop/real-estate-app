const Calendar = tui.Calendar;
let calendar;

let onReady = function() {
    initCalendar();

    calendar.createEvents([
        {
            id: 'event1',
            calendarId: 'cal1',
            title: 'Weekly Meeting',
            start: '2022-05-30T09:00:00',
            end: '2022-05-30T10:00:00',
        },
        {
            id: 'event2',
            calendarId: 'cal2',
            title: 'Lunch with Teammates',
            start: '2022-05-30T12:00:00',
            end: '2022-05-30T13:00:00',
        },
    ]);
}

let initCalendar = function() {
    calendar = new Calendar('#calendar', {
        usageStatistics: false,
        defaultView: 'month',
        useDetailPopup: true,
        timezone: {
            zones: [
                {
                    timezoneName: 'Asia/Seoul',
                    displayLabel: 'Seoul',
                },
            ],
        },
    });
}

$(document).ready(onReady);