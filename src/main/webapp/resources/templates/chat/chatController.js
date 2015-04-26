myModule.controller("chatController", function ($scope, $timeout, ChatListSvc, CurrentUserSvc, MessagesSvc) {

//    $scope.currentEmpl = CurrentUserSvc.get();
    $scope.chats = ChatListSvc.list(function () {
        if (exists($scope.chats))
        for (i=0;i<$scope.chats.length;i++) {
            getMessages($scope.chats[i].id);
        }
    });
    $scope.send = function (id) {
        var mess = $("#mess_" + id);
        $.ajax({
            type: "POST",
            url: ctx + "/mess",
            data: {
                id: id,
                m: mess.val()
            },
            success: function() {
                mess.val("");
            }
        });
    }

    $scope.checkEnterPressed = function(e, id) {
        if(e.which === 13) {
            $scope.send(id);
        }
    }
    var last = new Object();
    var requests = new Object();

    function getMessages(id) {
        if (!exists(last[id])) {
            last[id] = 0;
        }
        requests[id] = $.ajax({
            type: "GET",
            url: ctx + "/mess",
            data: {
                id: id,
                last: last[id]
            },
            success: function(messages) {
               if (exists (messages) && messages.length > 0) {
                last[id] = messages[messages.length - 1].id;

                for (i=0;i<messages.length;i++) {
                    var $mess = $("#messages_" + id);
                    $mess.append("<br/>" + messages[i].user + ": " + messages[i].text);
                    $mess.scrollTop($mess[0].scrollHeight);
                }
            }
            getMessages(id);
            }
        });

        /*var messages = MessagesSvc.get({id: id, last: last[id]}, function () {
            if (exists (messages) && messages.length > 0) {
                last[id] = messages[messages.length - 1].id;

                for (i=0;i<messages.length;i++) {
                    var $mess = $("#messages_" + id);
                    $mess.append("<br/>" + messages[i].user + ": " + messages[i].text);
                    $mess.scrollTop($mess[0].scrollHeight);
                }
            }
            getMessages(id);
        });*/
    }

    $scope.$on("$destroy", function(){
        for (i=0;i<$scope.chats.length;i++) {
            requests[$scope.chats[i].id].abort();
        }
    });
});
