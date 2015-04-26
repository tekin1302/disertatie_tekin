var exScope;
myModule.controller("exceptionController", function ($scope, ExceptionsSvc, ExceptionsImgSvc) {
    exScope = $scope;
    $scope.screenshot = null;
    showExceptionsGrid();
    initMenu();

    function showExceptionsGrid() {
        $("#exceptionsGrid").jqGrid({
            datatype: "json",
            url: ctx + "/exception",
            width:$("#mainIn").width(),
            colNames:['Id','Descriere','Data'],
            colModel:[
                {name:'id',index:'id',width: 10}
                ,{name:'stacktrace',index:'stacktrace'}
                ,{name:'date',index:'date', formatter: tDateFormatter, width:25}

            ],
            rownumbers:true,
            jsonReader : {
                repeatitems : false,
                id: "0",
                total: 'totalPages'
            },
            rowNum:10,
            pager: "#pager",
            sortname: 'id',
            rowList: [10, 20, 30, 40, 50],
            viewrecords: true,
            sortorder: "asc",
            caption: "Lista exceptii" ,
            height: 'auto',
            ondblClickRow: expandIt
        })
            .navGrid('#pager', {edit:false,add:false,del:false,search:false})
            .navButtonAdd("#pager",{caption:"", buttonicon:"screenshot", position: "last", title:"Imagine", cursor: "pointer", onClickButton: openScreenshot});
    }

    function expandIt(rowId,status, col, e) {
        if (!e || e.which === 1) { // left click
            var str = "#" + rowId + " td:nth-child(3)";
            elem = $(str);
            console.log(elem.attr("class"));
            elem.toggleClass("expanded");
        }
    }

    function initMenu() {
        $('#exceptionsGrid').contextMenu('exMenu', {
            bindings: {
                'open': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Open');
                },
                'email': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Email');
                },
                'save': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Save');
                },
                'delete': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Delete');
                }
            }
        });
    }

    function openScreenshot() {
        var myGrid = $("#exceptionsGrid");
        var selRowId = myGrid.jqGrid ('getGridParam', 'selrow');
        var cellValue = myGrid.jqGrid ('getCell', selRowId, 'id');
        $.ajax({
            type: "GET",
            url: ctx + "/exception/img?id=" + cellValue,
            success: function(data) {
                exScope.$apply(function(){
                    exScope.screenshot = data.value;

                    $( "#exceptionImgDialog" ).dialog({
                        height: 500,
                        width: 900,
                        modal: true
                    });

                });
            }
        });
    }
});

