myModule.controller("timesheetController", function ($scope, TimesheetSvc) {
    var empActivityId = 0;
    var activitiesGrid = $('#activitiesGrid');
    var tsGrid = $("#timesheetsGrid");
    $scope.err = errs;

    showActivitiesGrid();
    showTSGrid();

    function showActivitiesGrid() {
        activitiesGrid.jqGrid({
            datatype: "json",
            url: ctx + "/employee-activity/list",
            width:$("#mainIn").width(),
            colNames:['Id','Nume', "Valoare (&euro;)", "Durata (ore)", "Start", "Stop"],
            colModel:[
                {name:'id',index:'id', hidden: true}
                ,{name:'activity.name',index:'activity.name'}
                ,{name:'activity.value',index:'activity.value'}
                ,{name:'activity.duration',index:'activity.duration'}
                ,{name: 'start', index: 'start'}
                ,{name: 'end', index: 'end'}
            ],
            rownumbers:true,
            jsonReader : {
                repeatitems : false,
                id: "0",
                total: 'totalPages'
            },
            rowNum:20,
            pager: "#pager",
            sortname: 'id',
            rowList: [20, 30, 40, 50],
            viewrecords: true,
            sortorder: "asc",
            caption: "Lista activitati" ,
            height: 'auto',
            onSelectRow: function (id) {
                tsGrid .setGridParam({url: ctx + "/timesheet/list/" + getSelectedActivityField("id")});
                tsGrid .trigger("reloadGrid");
            }
        })
            .navGrid('#pager', {edit:false,add:false,del:false,search:false})
    }

    function getSelectedActivityField(field) {
        selRowId = activitiesGrid.jqGrid ('getGridParam', 'selrow');
        cellValue = activitiesGrid.jqGrid ('getCell', selRowId, field);
        return cellValue;
    }
    function getSelectedTSId() {
        selRowId = tsGrid.jqGrid ('getGridParam', 'selrow');
        cellValue = tsGrid.jqGrid ('getCell', selRowId, "id");
        return cellValue;
    }

function showTSGrid() {
    tsGrid.jqGrid({
        datatype: "json",
        url: ctx + "/timesheet/list/0",
        width:$("#mainIn").width(),
        colNames:['Id','Activitate', "Data", "Durata (ore)"],
        colModel:[
            {name:'id',index:'id', hidden: true}
            ,{name:'employeeActivity.activity.name',index:'employeeActivity.activity.name'}
            ,{name:'date',index:'date'}
            ,{name:'hours',index:'hours'}
        ],
        rownumbers:true,
        jsonReader : {
            repeatitems : false,
            id: "0",
            total: 'totalPages'
        },
        rowNum:20,
        pager: "#pagerTs",
        sortname: 'id',
        rowList: [20, 30, 40, 50],
        viewrecords: true,
        sortorder: "asc",
        caption: "Lista pontaje" ,
        height: 'auto'
    })
        .navGrid('#pagerTs', {edit:false,add:false,del:false,search:false})
        .navButtonAdd("#pagerTs",{caption:"", buttonicon:"timesheet", position: "last", title:"Ponteaza", cursor: "pointer", onClickButton: addTimesheet})
        .navButtonAdd("#pagerTs",{caption:"", buttonicon:"ui-icon-pencil", title:"Modifica", cursor: "pointer", onClickButton: editTimesheet})
        .navButtonAdd("#pagerTs",{caption:"", buttonicon:"ui-icon-trash", title:"Sterge", cursor: "pointer", onClickButton: deleteTimesheet})
        .navButtonAdd("#pagerTs",{caption:"", buttonicon:"ui-icon-search", title:"Vizualizare", cursor: "pointer", onClickButton: viewTimesheet});
}

    $scope.timesheetFile = "resources/templates/timesheet/addEdit.html?t="+ (new Date().getTime());

    function viewTimesheet () {
        editTimesheet(true);
    }

    function periodNotOk() {
        var startStr = getSelectedActivityField("start");
        var endStr = getSelectedActivityField("end");
        var today = new Date();
        if (exists(startStr) && startStr.length > 0) {
            var startDate = dateFromString(startStr);
            if (startDate.getTime() > today.getTime()) {
                tAlert("Perioada de pontare nu a inceput inca!");
                return true;
            }
        }
        if (exists(endStr) && endStr.length > 0) {
            var endDate = dateFromString(endStr);
            if (endDate.getTime() < today.getTime()) {
                tAlert("Perioada de pontare s-a incheiat!");
                return true;
            }
        }
        return false;
    }
    function addTimesheet() {
        if (periodNotOk()) return;
        $scope.isreadonly = false;
        if (!exists(getSelectedActivityField("id")) || !(getSelectedActivityField("id") > 0) ) {
            tAlert("Alegeti o activitate!", WARNING);
            return;
        }
        $scope.activityName = getSelectedActivityField("activity.name");
        $scope.timesheet = {employeeActivity: {id : getSelectedActivityField("id") }};
        safeApply();
        showAddEditTSDialog();
    }
    function editTimesheet(readonly) {
        if (readonly != undefined && readonly != true && periodNotOk()) return;
        if (!exists(getSelectedTSId()) || !(getSelectedTSId() > 0) ) {
            tAlert("Alegeti un pontaj!", WARNING);
            return;
        }
        $scope.activityName = getSelectedActivityField("activity.name");
        $scope.timesheet = TimesheetSvc.get({id: getSelectedTSId()});
        if (exists(readonly) && readonly == true) {
            $scope.isreadonly = true;
        } else {
            $scope.isreadonly = false;
        }
        safeApply();
        showAddEditTSDialog();
    }

    function showAddEditTSDialog() {
        $("#timesheetDialog").dialog({
            maxHeight: getDialogMaxHeight(),
            position: { my: 'top', at: 'top+50' },
            width: 800,
            modal: true,
            close: function(event, ui) {
                $scope.timesheetFile = "resources/templates/timesheet/addEdit.html?t="+ (new Date().getTime());
                safeApply();
            }
        });
    }

    function deleteTimesheet () {
        if (periodNotOk()) return;
        if (!exists(getSelectedTSId()) || !(getSelectedTSId() > 0) ) {
            tAlert("Alegeti un pontaj!", WARNING);
            return;
        }
        tConfirmation("Sunteti sigur ca doriti sa stergeti pontajul?", function () {
            TimesheetSvc.delete({id: getSelectedTSId()}, function () {
                tAlert("Pontajul a fost sters!");
                tsGrid .trigger("reloadGrid");
            });
        });
    }
    $scope.setDatepicker = function() {
        $("#date").datepicker({ dateFormat: 'dd-mm-yy', maxDate: new Date()});
    }

    $scope.saveTimesheet = function (tsForm) {
        if (tsForm.$invalid) {
            tsForm.activity.$dirty = true;
            tsForm.description.$dirty = true;
            tsForm.hours.$dirty = true;
            tsForm.date.$dirty = true;
            return;
        }
        TimesheetSvc.save($scope.timesheet, function (data) {
            tAlert("Pontajul a fost salvat!");
            $("#timesheetDialog").dialog("close");
            tsGrid .trigger("reloadGrid");
        });
    }
    function safeApply() {
        if(!$scope.$$phase) {
            $scope.$apply();
        }
    }
});