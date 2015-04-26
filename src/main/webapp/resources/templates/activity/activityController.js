myModule.controller("activityController", function ($scope, ActivitySvc, TSStatsSvc,
                                                    EmployeeActivityListSvc, EmployeeListSvc, EmployeeActivitySvc) {

    init();
    $scope.err = errs; // defined in d_commons.js
    $scope.associationFile = "resources/templates/activity/association.html?t="+ (new Date().getTime());
    $scope.statistics = null;
    $scope.min = 0;
    $scope.max = 0;

    function init() {
        showActivitiesGrid();
    }


    function showActivitiesGrid() {
        $("#activitiesGrid").jqGrid({
            datatype: "json",
            url: ctx + "/activity/list",
            width:$("#mainIn").width(),
            colNames:['Id','Nume', "Valoare (&euro;)", "Durata (ore)"],
            colModel:[
                {name:'id',index:'id', hidden: true}
                ,{name:'name',index:'name'}
                ,{name:'value',index:'value'}
                ,{name:'duration',index:'duration'}
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
            onSelectRow: function(id) {
                refreshStatistics(id);
            }
        })
            .navGrid('#pager', {edit:false,add:false,del:false,search:false})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-plus", title:"Adauga", cursor: "pointer", onClickButton: addActivity})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-pencil", title:"Modifica", cursor: "pointer", onClickButton: editActivity})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-trash", title:"Sterge", cursor: "pointer", onClickButton: deleteActivity})
            .navButtonAdd("#pager",{caption:"", buttonicon:"employee", position: "last", title:"Asigneaza angajat", cursor: "pointer", onClickButton: assignEmployee});
    }

    function refreshStatistics(id) {
        $scope.statistics = TSStatsSvc.get({id: getSelectedActivityId()}, function(data){
            var min = -1, max = 0;
            if ($scope.statistics != null && $scope.statistics.length > 0) {
                for (i=0;i<$scope.statistics.length;i++) {
                    if ($scope.statistics[i].end > max) {
                        max = $scope.statistics[i].end;
                    }
                    if (min == -1 || $scope.statistics[i].start < min) {
                        min = $scope.statistics[i].start;
                    }
                }
            }
            $scope.min = min;
            $scope.max = max;

            safeApply();
        });
    }
    // ADD OR EDIT ACTIVITY

    function getSelectedActivityId() {
        var myGrid = $('#activitiesGrid'),
            selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
            cellValue = myGrid.jqGrid ('getCell', selRowId, 'id');
        return cellValue;
    }

    function addActivity() {
        $scope.activity = {};
        addEditActivity();
    }
    function editActivity() {
        var activityId = getSelectedActivityId();
        if (exists(activityId)) {
            $scope.activity = ActivitySvc.show({id : activityId}, function (data) {
            });
            addEditActivity();
        } else {
            tAlert("Alegeti o activitate!", WARNING)
        }
    }

    $scope.addEditActivityFile = "resources/templates/activity/addEdit.html?t="+ (new Date().getTime());

    var d = new Date();
    function addEditActivity() {
        safeApply();
        $("#addEditActivityDialog").dialog({
            maxHeight: getDialogMaxHeight(),
            position: { my: 'top', at: 'top+50' },
            width: 800,
            modal: true,
            close: function(event, ui) {
                $scope.addEditActivityFile = "resources/templates/activity/addEdit.html?t="+ (new Date().getTime());
                safeApply();
            }
        });
    }

    function deleteActivity () {
        var activityId = getSelectedActivityId();
        if (exists(activityId)) {
            tConfirmation("Sunteti sigur ca doriti sa stergeti activitatea?", function () {
                ActivitySvc.delete({id : activityId}, function (data) {
                    $('#activitiesGrid').trigger( 'reloadGrid' );
                    tAlert("Activitatea a fost stearsa cu succes!");
                });
            });
        } else {
            tAlert("Alegeti o activitate!", WARNING)
        }
    }

    $scope.saveActivity = function (actForm) {
        if (actForm.$invalid) {
            actForm.name.$dirty = true;
            actForm.description.$dirty = true;
            actForm.value.$dirty = true;
            actForm.duration.$dirty = true;
            return;
        }
        if (!exists($scope.activity.id)) {
            ActivitySvc.create($scope.activity, function (data, x, y) {
                activitySaveCallback(0);
            });
        } else {
            ActivitySvc.update($scope.activity, function (data, x, y) {
                activitySaveCallback(1);
            });
        }
    }

    function activitySaveCallback (f) {
        $("#addEditActivityDialog").dialog("close");
        $('#activitiesGrid').trigger( 'reloadGrid' );
        if (f == 0) {
            tAlert("Activitatea a fost creata cu succes!");
        } else{
            tAlert("Activitatea a fost actualizata cu succes!");
        }
    }

    function safeApply() {
        if(!$scope.$$phase) {
            $scope.$apply();
        }
    }

    $scope.setupDateInputs = function () {
        $(".date-type").datepicker({ dateFormat: 'dd-mm-yy' } );
        for (i=0;i<$scope.employeeActivities.length;i++) {
            $scope.setDateRestrictions(i);
        }
    }

    $scope.setDateRestrictions = function (index) {
        var maxDate = $("#endDate_"+index).val();
        if (exists(maxDate) && maxDate.length > 0) {
            maxDate = maxDate.split("-");
            maxDate = new Date(maxDate[2], maxDate[1]-1, maxDate[0]);
            $("#startDate_" + index).datepicker("destroy");
            $("#startDate_" + index).datepicker({ dateFormat: 'dd-mm-yy', maxDate: maxDate });
        }

        var minDate = $("#startDate_"+index).val();
        if (exists(minDate) && minDate.length > 0) {
            minDate = minDate.split("-");
            minDate = new Date(minDate[2], minDate[1]-1, minDate[0]);
            $("#endDate_" + index).datepicker("destroy");
            $("#endDate_"+index).datepicker({ dateFormat: 'dd-mm-yy', minDate: minDate} );
        }
    }
    // employee - activities associations

    $scope.employees = EmployeeListSvc.list();
    $scope.addSelectInput = function (employeeActivity, index) {
        var i = $scope.employeeActivities.length - 1;
        if (i > 0 && (!exists(employeeActivity.employee) || !(employeeActivity.employee.id > 0))) {
            $scope.employeeActivities.splice(index,1); // sterg activitatea daca s-a ales prima optiune
        } else {
            if (exists($scope.employeeActivities[i].employee) && $scope.employeeActivities[i].employee.id > 0){
                // adaug un selec la final daca ultimul nu este deja pus pe default
                $scope.employeeActivities.push({activity : {id: getSelectedActivityId()}});
            }
        }
    };
    $scope.deleteAssociation = function(id) {
        EmployeeActivitySvc.delete({id: id});
    };
    $scope.saveEmployeeActivities = function () {
        EmployeeActivitySvc.save($scope.employeeActivities, function() {
            refreshStatistics(getSelectedActivityId())
            tAlert("Salvarea s-a realizat cu succes!");
        });
    };

    function assignEmployee() {
        if (!exists(getSelectedActivityId())) {
            tAlert("Alegeti o activitate!", WARNING);
            return;
        }
        $scope.employeeActivities = EmployeeActivityListSvc.get({byWhat: "by-activity", id: getSelectedActivityId()}, function(data) {
            if ($scope.employeeActivities == null || $scope.employeeActivities.length == 0) {
                $scope.employeeActivities = [{activity : {id: getSelectedActivityId()}}];
            } else {
                $scope.employeeActivities.push({activity : {id: getSelectedActivityId()}});
            }
        });

        $("#addEditEmployeeActivityDialog").dialog({
            maxHeight: getDialogMaxHeight(),
            position: { my: 'top', at: 'top+50' },
            width: 800,
            modal: true
        });
    }


    //statistics
    $scope.dateFormatted = function(date) {
        var d = new Date(date);
        var result = "";
        var day = d.getDate();
        var month = (d.getMonth() + 1);

        if (day < 10) day = "0" + day;
        if (month < 10) month = "0" + month;
        result += day + "-" + month + "-" + d.getFullYear();
        return result;
    }

    $scope.getStatStyle = function (stat) {
        var pixels = $("#timespan").css("width");
        pixels = pixels.substr(0, pixels.indexOf("px"));
        var pxUnit = pixels / ($scope.max - $scope.min);
        var paddLeft = (stat.start - $scope.min) * pxUnit;
        var paddRight = ($scope.max - stat.end) * pxUnit;

        return {"padding-left": paddLeft, "padding-right": paddRight, "background-color":"#b0b0b0"};
    }
});
