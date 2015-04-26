<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Login</title>
    <%@include file="../util/cssAndJs.jsp" %>
</head>
<body>

<div id="main">
    <div id="mainIn">

        <c:url value="/loginP" var="loginURL" />
        <div style="margin: 0px auto; width: 257px;">
            <c:if test="${not empty error }">
                <span class="alert alert-danger">Nume si parola gresite!</span>
            </c:if>
        </div>
        <div id="login_form">
            <form action="${loginURL }" method="POST">
                <table style="width: 100%;">
                    <tr>
                        <td>
                            Email:
                        </td>
                        <td>
                            <input name="j_username" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Parola:
                        </td>
                        <td>
                            <input type="password" name="j_password" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td ><input type="submit" class="btn btn-default" value="Login" /> <a class="btn btn-primary" href="${appPath}/user/register">Inregistrare</a></td>
                    </tr>

                </table>
            </form>
        </div>
    </div>
</div>
