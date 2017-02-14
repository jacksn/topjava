var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

// $(document).ready(function () {
function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function updateElementDisplay(key, value) {
    if (key == "dateTime") {
        $('#dateTime').data("DateTimePicker").date(value);
    }
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return data.replace("T", " ").slice(0, 16);
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass("exceeded");
            } else {
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
    $('#startDate').datetimepicker({
        format: "YYYY-MM-DD",
        showTodayButton: true,
        showClear: true,
        showClose: true
    }).on("dp.change", function (endDate) {
        $('#endDate').data("DateTimePicker").minDate(endDate.date);
    });

    $('#endDate').datetimepicker({
        format: "YYYY-MM-DD",
        useCurrent: false,
        showTodayButton: true,
        showClear: true,
        showClose: true
    }).on("dp.change", function (startDate) {
        $('#startDate').data("DateTimePicker").maxDate(startDate.date);
    });

    $('#startTime').datetimepicker({
        format: "hh:mm"
    });
    $('#endTime').datetimepicker({
        format: "hh:mm"
    });
    $('#dateTime').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        showTodayButton: true,
        showClear: true,
        showClose: true
    });
});