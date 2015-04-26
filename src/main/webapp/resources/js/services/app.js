var ctx = "/disertatie";
var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;

var myModule = angular.module("myAppModule", ['ngResource', 'ngRoute', 'angularFileUpload'])
        .config(function ($routeProvider) {
            $routeProvider.when("/exceptions",
                {
                    templateUrl: 'resources/templates/exceptions/exceptions.html',
                    controller: 'exceptionController'
                });
            $routeProvider.when("/profile",
                {
                    templateUrl: 'resources/templates/profile/profile.html',
                    controller: 'profileController'
                });
            $routeProvider.when("/employees",
                {
                    templateUrl: 'resources/templates/employee/list.html',
                    controller: 'employeeController'
                });
            $routeProvider.when("/diagram",
                {
                    templateUrl: 'resources/templates/diagram/diagram.html',
                    controller: 'diagramController'
                });
        $routeProvider.when("/chat",
            {
                templateUrl: 'resources/templates/chat/chat.html',
                controller: 'chatController'
            });
            $routeProvider.when("/empprofile",
                {
                    templateUrl: 'resources/templates/employee/editOwnProfile.html',
                    controller: 'editOwnProfileController'
                });
            $routeProvider.when("/activity",
                {
                    templateUrl: 'resources/templates/activity/list.html',
                    controller: 'activityController'
                });
            $routeProvider.when("/timesheet",
                {
                    templateUrl: 'resources/templates/timesheet/list.html',
                    controller: 'timesheetController'
                });
        $routeProvider.when("/",
            {
                templateUrl: 'resources/templates/employee/list.html',
                controller: 'employeeController'
            });

        })
        .config(function ($httpProvider) {
            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

            var interceptor = ['$rootScope', '$q', function (scope, $q) {
                function success(response) {
                    return response;
                }

                function error(response) {
                    handleException(response.data);
                    return $q.reject(response);
                }

                return function (promise) {
                    return promise.then(success, error);
                }

            }];
            $httpProvider.responseInterceptors.push(interceptor);
        })
    .directive('customJs', function() {
        return {
            // A = attribute, E = Element, C = Class and M = HTML Comment
            restrict:'A',
            scope: {
                customJs: "="
            },
            //The link function is responsible for registering DOM listeners as well as updating the DOM.
            link: function(scope, element, attrs) {
                scope.customJs(scope, element, attrs);
            }
        };
    })

        .directive('repeatOver', function() {
            return {
                restrict: 'A',
                link: {
                    pre: function (scope, element, attrs) {
                        if (scope.$last) {
//                            alert("call " + scope[attrs.repeatOver] );
                            setTimeout(function(){
                                scope[attrs.repeatOver]();
                            }, 1);
                        }
                    }
                }
            };
        })
        .directive('smartFloat', function() {
            return {
                require: 'ngModel',
                link: function(scope, elm, attrs, ctrl) {
                    ctrl.$parsers.unshift(function(viewValue) {
                        if (FLOAT_REGEXP.test(viewValue)) {
                            ctrl.$setValidity('float', true);
                            return parseFloat(viewValue.replace(',', '.'));
                        } else {
                            ctrl.$setValidity('float', false);
                            return undefined;
                        }
                    });
                }
            };
        })
        .filter('ignoreFromSelected', function () {
            return function (allItems, value) {
                if (!exists(value.ea) || value.ea.length == 0) {
                    return allItems;
                }
                var out = [];

                for (i=0;i<allItems.length;i++) {
                    var ignore = false;
                    for (j=0;j<value.ea.length;j++){
                        if (exists(value.ea[j].employee) && allItems[i].id == value.ea[j].employee.id && allItems[i].id != value.e) {
                            ignore = true;
                        }
                    }
                    if (!ignore) {
                        out.push(allItems[i]);
                    }
                }
                return out;
            };
        })
        .filter('hasRate', function () {
            return function (allItems, value) {
                var out = [];

                for (i=0;i<allItems.length;i++) {
                    if (exists(allItems[i].rate)) {
                        out.push(allItems[i]);
                    }
                }
                return out;
            };
        })
    ;

/*.directive('fileUpload', function () {
 return {
 scope: true,        //create a new scope
 link: function (scope, el, attrs) {
 el.bind('change', function (event) {
 var files = event.target.files;
 //iterate files since 'multiple' may be specified on the element
 for (var i = 0;i<files.length;i++) {
 //emit event upward
 scope.$emit("fileSelected", { file: files[i] });
 }
 });
 }
 };
 })*/;