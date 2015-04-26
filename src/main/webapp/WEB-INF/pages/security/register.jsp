<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Inregistrare</title>
    <%@include file="../util/cssAndJs.jsp" %>
</head>
<body ng-app="myAppModule" ng-controller="registerController">
<script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>

<div id="main">
    <div id="mainIn">

        <form name="regForm" id="regFormId" >
            <table class="formTable">
                <tr>
                    <td>
                        <label>E-mail:</label>
                    </td>
                    <td>
                        <input name="email" id="email" class="form-control" ng-model="company.user.email" required type="email" ng-blur="checkEmail(company.user.email)" placeholder="e-mail"/>
                    </td>
                    <td>
                        <div class="error" ng-show="regForm.email.$dirty && regForm.email.$error.email">{{err.invalidEmail}}</div>
                        <div class="error" ng-show="regForm.email.$dirty && regForm.email.$error.required">{{err.required}}</div>
                        <span class="error" ng-show="regForm.email.$dirty && getEmailExists()">{{err.emailExists}}</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Parola:</label>
                    </td>
                    <td>
                        <input name="password" type="password" class="form-control" required ng-model="company.user.password" autocomplete="off" placeholder="parola"/>
                    </td>
                    <td>
                        <span class="error" ng-show="regForm.password.$dirty && regForm.password.$error.required">{{err.required}}</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Confirma parola:</label>
                    </td>
                    <td>
                        <input name="confirm" type="password" class="form-control" required ng-model="company.user.confirm" placeholder="confirma parola"/>
                    </td>
                    <td>
                        <span class="error" ng-show="regForm.confirm.$dirty && regForm.confirm.$error.required">{{err.required}}</span>
                        <span class="error" ng-show="regForm.confirm.$dirty && !passwordsMatch()">{{err.passwordsDontMatch}}</span>
                    </td>
                </tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <label>Nume:</label>
                    </td>
                    <td>
                        <input name="name" ng-model="company.name" class="form-control" required placeholder="nume companie" maxlength="130"/>
                    </td>
                    <td>
                        <span class="error" ng-show="regForm.name.$dirty && regForm.name.$error.required">{{err.required}}</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Oras:</label>
                    </td>
                    <td>
                        <input name="city" ng-model="company.city" class="form-control" placeholder="oras" maxlength="45" />
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Adresa:</label>
                    </td>
                    <td>
                        <textarea name="address" placeholder="adresa" class="form-control" ng-model="company.address" maxlength="1000"></textarea>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Telefon:</label>
                    </td>
                    <td>
                        <input name="phone" ng-model="company.phone" class="form-control" placeholder="telefon" maxlength="15"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Fax:</label>
                    </td>
                    <td>
                        <input name="fax" ng-model="company.fax" class="form-control" placeholder="fax" maxlength="15"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <div id="captcha-div"></div>
                    </td>
                </tr>
                <tr>
                    <td><input type="button" class="btn btn-default" ng-click="goToLogin()" value="Autentificare" /></td>
                    <td><input type="button" class="btn btn-default" ng-click="saveUser()" value="Salveaza" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
