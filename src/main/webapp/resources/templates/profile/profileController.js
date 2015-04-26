myModule.controller("profileController", function ($scope, $upload, CompanySvc, UserEmailSvc) {
    $scope.company = CompanySvc.get(function (data) {
        $scope.initialEmail = $scope.company.user.email;
        if (!$scope.company.logo) $scope.company.logo = {id: null};
        $scope.oldLogoId = $scope.company.logo.id;
    });

    $scope.err = errs;

    $scope.saveUser = function () {

        if ($scope.regForm.$invalid || formContainsErrors("profileForm")) {
            $scope.regForm.email.$dirty = true;
            $scope.regForm.password.$dirty = true;
            $scope.regForm.confirm.$dirty = true;
            $scope.regForm.name.$dirty = true;

            return;
        }
        if (exists($scope.company.logo) && $scope.company.logo.id == null) {
            delete $scope.company.logo;
        }
        CompanySvc.update($scope.company, function (data, x, y) {
            if(exists($scope.company.logo)) {
                $scope.oldLogoId = $scope.company.logo.id;
            } else {
                $scope.company.logo = {id: null};
            }
            tAlert("Contul a fost actualizat cu succes!");
        });
    }

    $scope.checkEmail = function (email) {
        if ($scope.initialEmail == email) {
            $scope.emailExists = false;
        }else if (email != null && $.trim(email).length > 0) {
            $scope.emailExists = UserEmailSvc.check({emailP: email});
        }
    }

    // validation start
    $scope.passwordsMatch = function() {
        if ($scope.company.user.confirm != $scope.company.user.password) {
            return false;
        }
        return true;
    }

    $scope.getEmailExists = function() {
        if ($scope.emailExists != null) {
            return $scope.emailExists.value;
        }
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

    $scope.revertLogo = function () {
        $scope.company.logo.id = $scope.oldLogoId;
    }
    // validation end

    /*$scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {
            $scope.logo = args.file;
        });
    });*/

    $scope.prcLoaded = 0;
    $scope.uploadFile = function($files) {
        var file = $files[0];

        $scope.fileIsNotImage = $scope.fileIsNotImageFunc(file);
        $scope.fileTooLarge = $scope.fileTooLargeFunc(file);

        if ($scope.fileIsNotImage || $scope.fileTooLarge) {
            return;
        }

        $scope.loadingLogo = true;
        $scope.first = true;
        $scope.upload = $upload.upload({
            url: ctx + '/file',
            file: $files,
            fileFormDataName: 'fileData'
        }).progress(function(evt) {
            $scope.prcLoaded = parseInt(100.0 * evt.loaded / evt.total);
        }).success(function(data, status, headers, config) {
            $scope.loadingLogo = false;
            $scope.prcLoaded = 0;
            if (data != null) {
                $scope.company.logo.id = data;
            }
        });
    }

    // setup progress bar
    tProgressBar({elemId: "logoProgressBar", proc: $scope.prcLoaded});

    $scope.getLogoPath = function () {
        if (exists($scope.company.logo) && $scope.company.logo.id) {
            return ctx + "/file/img/" + $scope.company.logo.id;
        }
        return null;
    }
    $scope.getInnerWith = function() {
        return {width: $scope.prcLoaded + '%'};
    }
});
