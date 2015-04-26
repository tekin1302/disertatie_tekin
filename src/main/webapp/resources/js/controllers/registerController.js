myModule.controller("registerController", function ($scope, RegisterSvc, UserEmailSvc) {
    $scope.company = {user: {password: null, confirm: null} };

    $scope.err = errs;
    $scope.saveUser = function () {
        if (formContainsErrors("regFormId") || $scope.regForm.$invalid) {
            $scope.regForm.email.$dirty = true;
            $scope.regForm.password.$dirty = true;
            $scope.regForm.confirm.$dirty = true;
            $scope.regForm.name.$dirty = true;

            return;
        }
        var registerBean = {company: $scope.company, captchaChallenge: Recaptcha.get_challenge(), captchaResp: Recaptcha.get_response()};
        var resp = RegisterSvc.create(registerBean, function (data, x, y) {
            Recaptcha.reload();
            if (resp["0"] == 0) {
                tAlert("Captcha incorect!", ERROR);
            } else {
                tAlert("Contul a fost creat cu succes!");
                $scope.company = {user: {password: null, confirm: null} };
                $scope.regForm.$setPristine();
            }
        });
    }

    $scope.checkEmail = function (email) {
        if (email != null && $.trim(email).length > 0) {
            $scope.emailExists = UserEmailSvc.check({emailP: email});
        }
    }

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
    $scope.goToLogin = function () {
        window.location = ctx;
    }

    // captcha

    $(document).ready(function(){
        showRecaptcha()
    });
    function showRecaptcha() {
        Recaptcha.create("6LfUkvQSAAAAAA9ljC7QhJnBcOsd3pjhnresdjbR", "captcha-div", {
            theme: "white",
            callback: Recaptcha.focus_response_field
        });
    }
});
