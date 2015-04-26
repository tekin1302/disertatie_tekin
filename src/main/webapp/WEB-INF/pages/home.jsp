<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title>Disertatie</title>
    <%@include file="util/cssAndJs.jsp" %>

</head>
<body ng-app="myAppModule">

<div id="header">
    <div id="headerIn">
        <table>
            <tr style="width: 20%;">
                <td>
                    <sec:authorize access="hasRole('ROLE_COMPANY')">
                        <button class="btn btn-success" style="margin-top: 15px;" onclick="downloadReport()">Descarca raport</button>
                    </sec:authorize>
                </td>

                <td style="text-align: right; padding-bottom: 20px;">
                    <div style="text-align: right; padding-top:10px;">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <a href="#/exceptions" class="menu_item">Erori</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_COMPANY')">
                            <a href="#/employees" class="menu_item">Angajati</a>
                            <a href="#/diagram" class="menu_item">Organigrama</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_COMPANY')">
                            <a href="#/activity" class="menu_item">Activitati</a>
                            <a href="#/profile" class="menu_item">Profil</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <a href="#/empprofile" class="menu_item">Profil</a>
                            <a href="#/timesheet" class="menu_item">Pontaj</a>
                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_USER')">
                            <a href="#/chat" class="menu_item">Discutii</a>
                        </sec:authorize>
                        <a href="${appPath}/logout" class="menu_item"><img src="${appPath}/resources/logout.png"/>&nbsp;Delogare</a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="main">
    <div id="mainIn">
        <ng-view id="single-view">
        </ng-view>
    </div>
</div>

<script>
    function downloadReport () {
        window.location = ctx + "/reports/download"
    }
</script>
<%@include file="util/footer.jsp" %>
</body>
</html>
