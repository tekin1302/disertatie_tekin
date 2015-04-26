<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title>Telefoane</title>
    <%@include file="util/cssAndJs.jsp" %>

</head>
<body ng-app="myAppModule">

<div id="header">
    <div id="headerIn">
        <table>
            <tr style="width: 20%;">
                <td>
                </td>

                <td style="text-align: right; padding-bottom: 20px;">
                    <div style="text-align: right; padding-top:10px;">
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="main">

    <div id="mainIn">

        <c:url value="/" var="imgURL" />

        <c:forEach items="${phones}" var="phone">
            <ul style="display: inline-block; list-style-type: none;">
                <li>
                    <a href="#" style="padding: 20px;">
                        <table>
                            <tr>
                                <td>${phone.name}</td>
                            </tr>
                            <tr>
                                <td>
                                    <img src="${imgURL}/file/img/${phone.photo.id}" width="150px"/>
                                </td>
                            </tr>
                        </table>
                    </a>
                </li>
            </ul>
        </c:forEach>
    </div>
</div>

<%@include file="util/footer.jsp" %>
</body>
</html>
