var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function getAjaxFilterURL() {
    return 'ajax/profile/meals/filter'
        + '?' + 'startDate=' + $("[name='startDate']").val()
        + '&' + 'endDate=' + $("[name='endDate']").val()
        + '&' + 'startTime=' + $("[name='startTime']").val()
        + '&' + 'endTime=' + $("[name='endTime']").val();
}

function clearFilter() {
    $("[name='startDate']").val("");
    $("[name='endDate']").val("");
    $("[name='startTime']").val("");
    $("[name='endTime']").val("");
    updateTable();
}