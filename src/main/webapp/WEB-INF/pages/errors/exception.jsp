<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Disertatie</title>
    <%@include file="../util/cssAndJs.jsp" %>

</head>
<body>

<div id="main">
    <div id="mainIn">
            Eroarea este:
            ${ex.stacktrace}

            <br/>
            <img src="data:image/jpg;base64,${ex.imageString}" width="100%"/>
    </div>
</div>

<%@include file="../util/footer.jsp" %>
</body>
</html>
