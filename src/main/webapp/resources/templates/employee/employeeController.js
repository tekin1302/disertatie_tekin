myModule.controller("employeeController", function ($scope, $upload, UserEmailSvc,
                                                    EmployeeSvc, PositionSvc, PositionListSvc) {

    init();

    function init() {
        showEmployeesGrid();
        loadPositions();
        initEmployee();
    }

    function showEmployeesGrid() {
        $("#employeesGrid").jqGrid({
            datatype: "json",
            url: ctx + "/employee/list",
            width:$("#mainIn").width(),
            colNames:['Id','Nume','Companie','Functie', 'Email', 'Telefon'],
            colModel:[
                {name:'id',index:'id', hidden: true}
                ,{name:'name',index:'name'}
                ,{name:'company.name',index:'companyId.name'}
                ,{name:'position.name',index:'position.name'}
                ,{name:'user.email',index:'user.email'}
                ,{name:'phone',index:'phone'}

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
            caption: "Lista angajati" ,
            height: 'auto'
        })
            .navGrid('#pager', {edit:false,add:false,del:false,search:false})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-plus", title:"Adauga", cursor: "pointer", onClickButton: addEmployee})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-pencil", title:"Modifica", cursor: "pointer", onClickButton: editEmployee})
            .navButtonAdd("#pager",{caption:"", buttonicon:"ui-icon-trash", title:"Sterge", cursor: "pointer", onClickButton: deleteEmployee});
    }

    // ADD OR EDIT EMPLOYEE

    function getSelectedEmployeeId() {
        var myGrid = $('#employeesGrid'),
            selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
            cellValue = myGrid.jqGrid ('getCell', selRowId, 'id');
        return cellValue;
    }
    function addEmployee() {
        initEmployee();
        addEditEmployee();
    }
    function editEmployee() {
        var employeeId = getSelectedEmployeeId();
        if (exists(employeeId)) {
            $scope.employee = EmployeeSvc.show({id : employeeId}, function (data) {
                $scope.initialEmail = $scope.employee.user.email;
                if (exists($scope.employee.photo)) {
                    $scope.oldPhotoId = $scope.employee.photo.id;
                }
            });
            addEditEmployee();
        } else {
            tAlert("Alegeti un angajat!", WARNING)
        }
    }
    function addEditEmployee() {
        $("#addEditEmployeeDialog").dialog({
            maxHeight: getDialogMaxHeight(),
            position: { my: 'top', at: 'top+50' },
            width: 800,
            modal: true
        });
        setEmployeeDirtyFields(angular.element("#empFormId").scope().empForm, false);
        $scope.$apply();
    }

    function deleteEmployee () {
        var employeeId = getSelectedEmployeeId();
        if (exists(employeeId)) {
            tConfirmation("Sunteti sigur ca doriti sa stergeti angajatul?", function () {
                EmployeeSvc.delete({id : employeeId}, function (data) {
                    $('#employeesGrid').trigger( 'reloadGrid' );
                    tAlert("Angajatul a fost sters cu succes!");
                });
            });
        } else {
            tAlert("Alegeti un angajat!", WARNING)
        }
    }
    function loadPositions() {
        $scope.positions = PositionListSvc.list();
    }
    function initEmployee() {
        $scope.employee = {photo: {id: null}, user: {password: null, confirm: null}, position:{id: null}};
        $("#empFormId input").each(function (index, elem){
            $(elem).val("");
        });
        $("#empFormId select").each(function (index, elem){
            $(elem).val("");
        });
    }

    $scope.oldPhotoId = null;
    $scope.err = errs; // defined in d_commons.js

    // validations start
    $scope.checkEmail = function (email) {
        if ($scope.initialEmail == email) {
            $scope.emailExists = false;
        }else if (email != null && $.trim(email).length > 0) {
            $scope.emailExists = UserEmailSvc.check({emailP: email});
        }
    }

    $scope.getEmailExists = function() {
        if ($scope.emailExists != null) {
            return $scope.emailExists.value;
        }
    }

    $scope.passwordsMatch = function() {
        if ($scope.employee.user.confirm != $scope.employee.user.password) {
            return false;
        }
        return true;
    }
    // validations end

    $scope.prcLoaded = 0;
    $scope.uploadFile = function($files) {
        var file = $files[0];

        $scope.fileIsNotImage = $scope.fileIsNotImageFunc(file);
        $scope.fileTooLarge = $scope.fileTooLargeFunc(file);
        if ($scope.fileIsNotImage || $scope.fileTooLarge) {
            return;
        }
        $scope.loadingPhoto = true;
        $scope.upload = $upload.upload({
            url: ctx + '/file',
            file: $files,
            fileFormDataName: 'fileData'
        }).progress(function(evt) {
            $scope.prcLoaded = parseInt(100.0 * evt.loaded / evt.total);
        }).success(function(data, status, headers, config) {
            $scope.loadingPhoto = false;
            $scope.prcLoaded = 0;
            if (data != null) {
                if (!exists($scope.employee.photo)) {
                    $scope.employee.photo = {id : null};
                }
                $scope.employee.photo.id = data;
            }
        });
    }

    $scope.fileIsNotImageFunc = function(img) {
        return fileIsNotImage(img);
    }
    $scope.fileTooLargeFunc = function (img) {
        if (img != null) {
            return img.size > 10485760;
        } else {
            return false;
        }
    }

    $scope.revertPhoto = function () {
        $scope.employee.photo.id = $scope.oldPhotoId;
    }

    $scope.getPhotoPath = function () {
        if ($scope.employee.photo && $scope.employee.photo.id) {
            return ctx + "/file/img/" + $scope.employee.photo.id;
        }
        return null;
    }
    $scope.getInnerWith = function() {
        return {width: $scope.prcLoaded + '%'};
    }

    function setEmployeeDirtyFields(empForm, flag) {
        if (flag == false) {
            empForm.$setPristine();
        } else {
            empForm.email.$dirty = flag;
            empForm.password.$dirty = flag;
            empForm.confirm.$dirty = flag;
            empForm.name.$dirty = flag;
            empForm.position.$dirty = flag;
        }
    }
    $scope.saveEmployee = function (empForm) {
        if (empForm.$invalid || formContainsErrors("empFormId")) {
            setEmployeeDirtyFields(empForm, true);
            return;
        }
        if (exists($scope.employee.photo) && $scope.employee.photo.id == null) {
            delete $scope.employee.photo;
        }
        if (!exists($scope.employee.id)) {
            EmployeeSvc.create($scope.employee, function (data, x, y) {
                employeeSaveCallback(0);
            });
        } else {
            EmployeeSvc.update($scope.employee, function (data, x, y) {
                employeeSaveCallback(1);
            });
        }
    }

    function employeeSaveCallback (f) {
        $("#addEditEmployeeDialog").dialog("close");
        if (exists($scope.employee.photo)) {
            $scope.oldPhotoId = $scope.employee.photo.id;
        }
        $('#employeesGrid').trigger( 'reloadGrid' );
        if (f == 0) {
            tAlert("Contul a fost creat cu succes!");
        } else{
            tAlert("Contul a fost actualizat cu succes!");
        }
    }

    /*
     * ADD EDIT POSITION
     */

    initPosition();

    function initPosition() {
        $scope.position = {};
    }

    $scope.savePosition = function (posForm) {
        if (posForm.$invalid) {
            setPositionDirtyFields(posForm, true);
            return;
        }
        PositionSvc.create($scope.position, function(){
            $("#addEditPositionDialog").dialog("close");
            tAlert("Functia a fost creata cu succes!");
            loadPositions();
        });
    }

    $scope.addPosition = function () {
        initPosition();
        showPositionDialog();
    }
    $scope.editPosition = function () {
        if (exists($scope.employee.position.id)){
            $scope.position = PositionSvc.show({id: $scope.employee.position.id});
            showPositionDialog();
        } else {
            tAlert("Alegeti o functie!", WARNING);
        }
    }
    $scope.deletePosition = function () {
        if (exists($scope.employee.position.id)) {
            tConfirmation("Sunteti sigur ca doriti sa stergeti functia?", function () {
                PositionSvc.delete({id: $scope.employee.position.id}, function() {
                    tAlert("Functia a fost stearsa cu succes!");
                    loadPositions();
                });

            });
        } else {
            tAlert("Alegeti o functie!", WARNING);
        }

    }
    function showPositionDialog() {
        $("#addEditPositionDialog").dialog({
            maxHeight: getDialogMaxHeight(),
            position: { my: 'top', at: 'top+50' },
            width: 800,
            modal: true
        });
        setPositionDirtyFields(angular.element("#positionFormId").scope().positionForm, false);
    }
    function setPositionDirtyFields(posForm, flag) {
        if (flag == false) {
            posForm.$setPristine();
        } else {
            posForm.name.$dirty = flag;
        }
    }
});
