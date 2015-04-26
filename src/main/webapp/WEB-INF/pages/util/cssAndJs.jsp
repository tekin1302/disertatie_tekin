<c:set var="appPath" value="${pageContext.request.contextPath}"/>

<div id="alert" title="" style="display: none;">
    <div id="msg" style="min-height: 50px; text-align: center;"></div>
</div>
<div id="confirm" title="" style="display: none;">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        <div id="msgConfirm">

        </div></p>
</div>
<!-- JS -->
 <!-- JQUERY -->
<script type="text/javascript" src="${appPath}/resources/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery/jquery.contextmenu.r2.packed.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>

<!-- JQ GRID -->
<script type="text/javascript" src="${appPath}/resources/js/jqgrid/grid.locale-ro.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/jqgrid/jquery.jqGrid.min.js"></script>

<!-- ANGULAR -->
<script type="text/javascript" src="${appPath}/resources/js/angular/angular-file-upload-shim.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/angular/angular.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/angular/angular-file-upload.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/angular/angular-resource.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/angular/angular-route.min.js"></script>

<!-- ALTE JS-uri PENTRU ANGULAR -->
<script type="text/javascript" src="${appPath}/resources/js/services/app.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/services/services.js"></script>

<!-- CANVAS -->
<script type="text/javascript" src="${appPath}/resources/js/canvas/html2canvas.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/d_commons.js"></script>

<!-- Controllers -->
<script type="text/javascript" src="${appPath}/resources/js/controllers/registerController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/exceptions/exceptionController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/profile/profileController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/home/homeController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/employee/employeeController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/employee/editOwnProfileController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/diagram/diagramController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/chat/chatController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/activity/activityController.js"></script>
<script type="text/javascript" src="${appPath}/resources/templates/timesheet/timesheetController.js"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${appPath}/resources/css/jqgrid/ui.jqgrid.css" />
<%--<link rel="stylesheet" type="text/css" href="${appPath}/resources/css/jquery-ui/jquery.ui.theme.css" />--%>
<link rel="stylesheet" type="text/css" href="${appPath}/resources/css/jquery-ui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${appPath}/resources/css/jquery-ui/jquery-ui-1.10.4.custom.min.css" />
<link rel="stylesheet" type="text/css" href="${appPath}/resources/css/style.css" />

<!-- BOOTSTRAP -->
<link rel="stylesheet" type="text/css" href="${appPath}/resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${appPath}/resources/bootstrap/css/bootstrap-theme.min.css" />
