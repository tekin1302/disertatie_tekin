myModule.controller("editOwnProfileController", function ($scope, $upload, UserEmailSvc,
                                                    EmployeeSvc, PositionSvc, PositionListSvc) {


    $scope.employee = {};
    editEmployee();
    $scope.positions = PositionListSvc.list();

    function editEmployee() {
            $scope.employee = EmployeeSvc.show({id : -1}, function (data) {
                $scope.initialEmail = $scope.employee.user.email;
                if (exists($scope.employee.photo)) {
                    $scope.oldPhotoId = $scope.employee.photo.id;
                }
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
                tAlert("Contul a fost actualizat cu succes!");
            });
        } else {
            EmployeeSvc.update($scope.employee, function (data, x, y) {
                tAlert("Contul a fost actualizat cu succes!");
            });
        }
    }

});
