myModule.controller("diagramController", function ($scope, $timeout, EmployeeListSvc, OrganigramElemsSvc, CreateDefaultChatRoom, OrganigramDataSvc) {

    $scope.employees = EmployeeListSvc.list(function() {
        var organigramDetails = OrganigramDataSvc.get(function (data) {
            if (exists(organigramDetails) && exists(organigramDetails.lines) && exists(organigramDetails.emplPositions)) {
                $scope.lines = organigramDetails.lines;
                $scope.lastLineId = $scope.lines.length;
                var emplPositions = organigramDetails.emplPositions;
                for(i=0;i<emplPositions.length;i++) {
                    var temp = emplPositions[i];
                    var emplDiv = $("#" + temp.id);
                    emplDiv.css("top", temp.top);
                    emplDiv.css("left", temp.left);
                }
            }
        });
    });

    var countRepeatOver = 0;
    $scope.redrawLines = function() {
        if (++countRepeatOver != 2) {
            return;
        }
        stageOffset = stage.offset();
        for(m=0;m<$scope.lines.length;m++) {
            $scope.lines[m].hasMenu = false;
            correctLine($scope.lines[m], true);
        }
    }

    $scope.makeDraggable = function () {
        $(".employee-node").each(function(index, element) {
            $(element).draggable({ containment: "#stage-container", scroll: true });
        })
        $scope.redrawLines();
    }
    $scope.safeApply = function(fn) {
        var phase = this.$root.$$phase;
        if(phase == '$apply' || phase == '$digest') {
            if(fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

    $scope.lines = [];
    $scope.draggableOptions = { containment: "#mainIn", scroll: true };
    $scope.radius = 3;
    $scope.lastLineId=0;

    $scope.drawLines = false;
    $scope.drawLinesFunc = function () {
        $scope.drawLines = !$scope.drawLines;
        if ($scope.drawLines) {
            $(".employee-node").draggable("disable");
        } else {
            $(".employee-node").draggable("enable");
        }
    };
    $scope.getLinieClass = function () {
        if ($scope.drawLines) {
            return "btn btn-primary";
        } else {
            return "btn btn-default";
        }
    }
// dl = draw line
    $scope.dl = {canDraw : true, mouseDown: false, currentLine: null, dragAlong: false, dragInitX:null, dragInitY:null,
        tempLines:null, currentEmpl: null,
        tMouseEnter : function (line) {
            this.canDraw = false;
            if (!line.hasMenu) {
                line.hasMenu = true;
                $('#line-wrap_' + line.id).contextMenu('lineMenu', {
                    bindings: {
                        'delete': function(t) {
                            var lineId = parseInt(t.id.substring(10, t.id.length));
                            for (k=0;k<$scope.employees.length;k++) {
                                var tempEmpl = $scope.employees[k];
                                if (exists(tempEmpl.lines) && tempEmpl.lines.length > 0)
                                    for (i=0;i<tempEmpl.lines.length;i++) {
                                        if (tempEmpl.lines[i].line.id == lineId) {
                                            tempEmpl.lines.splice(i,1);
                                        }
                                    }
                            }
                            var index=-1;
                            for (i=0;i<$scope.lines.length;i++){
                                if ($scope.lines[i].id==lineId) {
                                    index=i;
                                }
                            }
                            if(index != -1) {
                                $scope.lines.splice(index,1);
                                $scope.$apply();
                            }
                        }
                    }
                });
            }
        },
        tMouseLeave : function () {
            if (!this.mouseDown) {
                this.canDraw = true;
            }
        },
        tMouseDown : function (e, empl) {
            if (e.which != 1) {
                return;
            }
            stageOffset = stage.offset();
            if (exists(empl) && isOnStage(empl.id) && !$scope.drawLines && exists(empl.lines) && empl.lines.length > 0) {
                this.dragAlong = true;
                this.tempLines = [];
                this.currentEmpl = empl;
                for (i=0;i<empl.lines.length;i++){
                    var tempLine = empl.lines[i].line;
                    this.tempLines.push({x1:tempLine.x1, y1:tempLine.y1,x2:tempLine.x2,y2:tempLine.y2});
                }
                this.dragInitX = getPosX(e);
                this.dragInitY = getPosY(e);
            } else {
                if (!this.canDraw || !$scope.drawLines) {
                    return;
                }
                this.mouseDown = true;
                this.currentLine = $scope.addLine();
                this.currentLine.x1 = getPosX(e);
                this.currentLine.y1 = getPosY(e);
                this.currentLine.x2 = getPosX(e);
                this.currentLine.y2 = getPosY(e);
            }

        },
        tMouseUp : function (e) {
            if (e.which != 1) {
                return;
            }
            if (this.dragAlong) {
                this.dragAlong = false;
                for (i=0;i<this.currentEmpl.lines.length;i++){
                    correctLine(this.currentEmpl.lines[i].line, false);
                }
            }
            if (this.mouseDown || $scope.el.mouseDown || $scope.lm.mouseDown) {
                this.mouseDown = false;
                $scope.el.mouseDown = false;
                $scope.lm.mouseDown = false;
                correctLine(this.currentLine, true);
            }
        },
        tMouseMove : function (e) {
            if (this.dragAlong ) {
//                if (exists(currentEmpl) && exists(empl.lines) && currentEmpl.lines.length > 0) {
                for(i=0;i<this.currentEmpl.lines.length;i++){
                    var tempLine = this.tempLines[i];
                    var lineToDrag = this.currentEmpl.lines[i].line;

                    if (this.currentEmpl.lines[i].end === 1) {
                        lineToDrag.x1 = tempLine.x1 + getPosX(e) - this.dragInitX;
                        lineToDrag.y1 = tempLine.y1 + getPosY(e) - this.dragInitY;
                    } else {
                        lineToDrag.x2 = tempLine.x2 + getPosX(e) - this.dragInitX;
                        lineToDrag.y2 = tempLine.y2 + getPosY(e) - this.dragInitY;
                    }
                }
//                }
            } else if (this.mouseDown) {
                this.currentLine.x2 = getPosX(e);
                this.currentLine.y2 = getPosY(e);
            } else if ($scope.el.mouseDown) {
                this.currentLine = $scope.el.currentLine;
                if ($scope.el.m1) {
                    this.currentLine.x1 = getPosX(e);
                    this.currentLine.y1 = getPosY(e);
                } else if ($scope.el.m2) {
                    this.currentLine.x2 = getPosX(e);
                    this.currentLine.y2 = getPosY(e);
                }
            } else if ($scope.lm.mouseDown) {
                this.currentLine = $scope.lm.currentLine;
                $scope.lm.move(e);

            }
        }
    };


// el = expand line
    $scope.el = {
        canDraw : false, mouseDown: false, currentLine: null, m1 : false, m2 : false,
        tMouseEnter : function () {
            if (!$scope.dl.mouseDown) {
                this.canDraw = true;
                $scope.dl.canDraw = false;
            }
        },
        tMouseLeave : function () {
            if (this.canDraw && !this.mouseDown) {
                this.canDraw = false;
            }
        },
        tMouseDown1 : function (line, e) {
            if (e.which != 1) {
                return;
            }
            this.currentLine = line;
            this.mouseDown = true;
            this.m1 = true;
            this.m2 = false;
        },
        tMouseDown2 : function (line, e) {
            if (e.which != 1) {
                return;
            }
            this.currentLine = line;
            this.mouseDown = true;
            this.m2 = true;
            this.m1 = false;
        }
    }

    $scope.lm = {
        currentLine: null,
        mouseDown: false,
        xi: null, yi: null,
        clx1:null, clx2:null, cly1:null, cly2:null,
        tMouseDown: function (line, e) {
            if (e.which != 1) {
                return;
            }
            if (!$scope.dl.mouseDown) {
                this.currentLine = line;
                this.clx1 = line.x1;
                this.clx2 = line.x2;
                this.cly1 = line.y1;
                this.cly2 = line.y2;

                this.mouseDown = true;
                this.xi = getPosX(e);
                this.yi = getPosY(e);
            }
        },
        move: function (e) {
            var diffX = getPosX(e) - this.xi;
            var diffY = getPosY(e) - this.yi;
            this.currentLine.x1 = this.clx1 + diffX;
            this.currentLine.y1 = this.cly1 + diffY;
            this.currentLine.x2 = this.clx2 + diffX;
            this.currentLine.y2 = this.cly2 + diffY;
        }
    };

    $scope.addLine = function () {
        var newLine = {x1:0, y1: 0, x2 : 0, y2 : 0, id: $scope.lastLineId++, from: null, to: null, hasMenu: false};
        $scope.lines.push(newLine);
        return newLine;
    }

    var stage = $("#diagram-stage");
    var stageOffset = stage.offset();

    function getPosX(e){
        return e.pageX - stageOffset.left;
    }
    function getPosY(e){
        return e.pageY - stageOffset.top;
    }

    function isOnStage(empId) {
        var emp = $("#emp_" + empId);
        if (emp.offset().left >= stageOffset.left) {
            return true;
        }
        return false;
    }

    function correctLine(line, createBond) {
        if (!exists(line)) return;

        var empl1 = null, empl2 = null;
        var stageContOffset = $("#stage-container").offset();

        var c1 = $("#l1_" + line.id).offset();
        var c2 = $("#l2_" + line.id).offset();
        var changeNodes = false;

        // caut legatura
        var err = 15;
        $(".employee-node").each(function (index, elem) {
            if (empl1 == null || empl2 == null) {
                var $elem = $(elem);
                var offset = $elem.offset();
                if (empl1 == null && c1.left + err >= offset.left && c1.left - err <= ($elem.width() + offset.left) &&
                    c1.top + err >= offset.top && c1.top - err <= ($elem.height() + offset.top)) {
                    empl1 = $elem;
                } else if (empl2 == null && c2.left + err >= offset.left && c2.left - err <= ($elem.width() + offset.left) &&
                    c2.top + err >= offset.top && c2.top - err <= ($elem.height() + offset.top)) {

                    empl2 = $elem;
                }
            }
        });

        if (createBond){
            for (k=0;k<$scope.employees.length;k++) {
                var tempEmpl = $scope.employees[k];
                if (exists(tempEmpl.lines) && tempEmpl.lines.length > 0)
                    for (i=0;i<tempEmpl.lines.length;i++) {
                        if (tempEmpl.lines[i].line.id == line.id) {
                            tempEmpl.lines.splice(i,1);
                        }
                    }
            }
        }

        if (empl1 != null && empl2 != null) {
            var isHorizontal = false;

            var empl1Offset = empl1.offset();
            var empl2Offset = empl2.offset();

            if (empl1Offset.top < empl2Offset.top  - empl2.height()){
                // empl1 deasupra lui empl2
                line.x1 = empl1Offset.left - stageOffset.left + empl1.width()/2;
                line.y1 = empl1Offset.top - stageOffset.top;

                line.x2 = empl2Offset.left - stageOffset.left + empl2.width()/2;
                line.y2 = empl2Offset.top - stageOffset.top;
                line.y1 += empl1.height() + 16;
            } else if (empl2Offset.top < empl1Offset.top  - empl1.height()) {
                // empl2 deasupra lui empl1
                line.x1 = empl1Offset.left - stageOffset.left + empl1.width()/2;
                line.y1 = empl1Offset.top - stageOffset.top;

                line.x2 = empl2Offset.left - stageOffset.left + empl2.width()/2;
                line.y2 = empl2Offset.top - stageOffset.top;
                line.y2 += empl2.height() + 16;
            } else if (empl1Offset.left < empl2Offset.left - empl2.width()) {
                // empl1 in stanga lui empl2
                isHorizontal = true;
                if (c1.left > c2.left) {
                    var aux = empl1Offset;
                    empl1Offset = empl2Offset;
                    empl2Offset = aux;
                }
                line.x1 = empl1Offset.left - stageOffset.left + empl1.width() + 14;
                line.y1 = empl1Offset.top - stageOffset.top + empl1.height()/2 + 5;

                line.x2 = empl2Offset.left - stageOffset.left - 5;
                line.y2 = empl2Offset.top - stageOffset.top + empl2.height()/2 + 5;
            } else if (empl2Offset.left < empl1Offset.left - empl1.width()) {
                // empl2 in stanga lui empl2
                isHorizontal = true;
                if (c1.left > c2.left) {
                    var aux = empl1Offset;
                    empl1Offset = empl2Offset;
                    empl2Offset = aux;
                }
                line.x1 = empl2Offset.left - stageOffset.left - 5;
                line.y1 = empl2Offset.top - stageOffset.top + empl2.height()/2 + 5;

                line.x2 = empl1Offset.left - stageOffset.left + empl1.width() + 14;
                line.y2 = empl1Offset.top - stageOffset.top + empl1.height()/2 + 5;
            }
            if (createBond != undefined && createBond == false) {
                for (i=0;i<$scope.lines.length;i++){
                    if ($scope.lines[i].id === line.id) {
                        $scope.lines[i].isHoriz = isHorizontal;
                        return;
                    }
                }
                return;
            }

            var e1Id = parseInt(empl1.attr("id").substr(4, empl1.attr("id").length));
            var e2Id = parseInt(empl2.attr("id").substr(4, empl2.attr("id").length));

            for (i=0;i<$scope.employees.length;i++){
                if ($scope.employees[i].id === e1Id ) {
                    if (!exists($scope.employees[i].lines)) {
                        $scope.employees[i].lines = [];
                    }
                    $scope.employees[i].lines.push({line:line, end: 1, isHoriz: isHorizontal});
                } else if ($scope.employees[i].id === e2Id ) {
                    if (!exists($scope.employees[i].lines)) {
                        $scope.employees[i].lines = [];
                    }

                    $scope.employees[i].lines.push({line:line, end: 2, isHoriz: isHorizontal});
                }
//                tlog($scope.employees[i].lines.length + " " + $scope.employees[i].name);
            }
            line.valid = true;
        } else {
            line.valid = false;
            tlog("Nu se leaga");
        }
    }

    $scope.saveOrganigram = function () {
        for(m=0;m<$scope.lines.length;m++) {
            if ($scope.lines[m].valid == false) {
                tAlert("Organigrama nu este valida", ERROR);
                return;
            }

        }

        var finalLines = new Object();
        var employeePositions = [];

        $(".employee-node").each(function(idx, elem) {
            employeePositions.push({id: elem.id, top: elem.style.top, left: elem.style.left});
        });

        for (i=0;i<$scope.employees.length;i++) {
            var tempEmpl = $scope.employees[i];
            if (exists(tempEmpl.lines))
            for (j=0;j<tempEmpl.lines.length;j++) {
                var tempLine = tempEmpl.lines[j];
                if (!exists(finalLines[tempLine.line.id])) {
                    finalLines[tempLine.line.id] = {};
                }

                if (tempLine.isHoriz) {
                    if (!exists(finalLines[tempLine.line.id].employeeIdUp)) {
                        finalLines[tempLine.line.id].employeeIdUp = tempEmpl.id;
                    } else {
                        finalLines[tempLine.line.id].employeeIdDown = tempEmpl.id;
                    }
                    finalLines[tempLine.line.id].isHoriz = tempEmpl.isHoriz;
                } else if ((tempLine.end === 1 && tempLine.line.y1 < tempLine.line.y2) ||
                    (tempLine.end === 2 && tempLine.line.y1 > tempLine.line.y2)) {
                    finalLines[tempLine.line.id].employeeIdUp = tempEmpl.id;
                } else {
                    finalLines[tempLine.line.id].employeeIdDown = tempEmpl.id;
                }
            }
        }

//        tlog(finalLines.toSource());
        var result = [];
        for(i=0;i<$scope.lines.length;i++){
            result.push(finalLines[$scope.lines[i].id]);
        }

        organigramDetails = {lines: $scope.lines, emplPositions: employeePositions, employees: $scope.employees};

        OrganigramDataSvc.create(JSON.stringify(organigramDetails), function (data) {
            OrganigramElemsSvc.create(result, function(data) {
                if (data == 0) {
                    tAlert("Organigrama nu este valida!", ERROR)
                } else {
                    tAlert("Organigrama a fost salvata!");
                }
            });
        });
    }

    $scope.getImgPath = function (id) {
        if (exists(id)) {
            return ctx + "/file/img/" + id;
        } else {
            return "";
        }
    }
    $scope.createChatRoom = function() {
        CreateDefaultChatRoom.create(function () {
            tAlert("Grupurile de discutii au fost create!");
        });
    }
});
