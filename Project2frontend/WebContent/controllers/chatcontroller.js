/**
 * ChatCtrl
 */
app.controller('ChatCtrl',  function($rootScope ,$scope, ChatService) {
    alert('entering chat controller')
    $scope.chats = [];
    $scope.stompClient = ChatService.stompClient;
    $scope.users=[]
    $scope.$on('sockConnected', function(event, frame) {
    	alert('sockconnected')
        $scope.userName=$rootScope.user.firstname;
        $scope.stompClient.subscribe("/topic/join", function(message) {
            user = JSON.parse(message.body);
            console.log(user)
            alert(message.body)
            if(user != $scope.userName && $.inArray(user, $scope.users) == -1) {
                $scope.addUser(user);
                $scope.latestUser = user;
                $scope.$apply();
 		alert($scope.latestUser + ' has joined the chat ')
                $('#joinedChat').fadeIn(500).delay(10000).fadeOut(500);
            }
            
        });
        
  
        $scope.stompClient.subscribe('/app/join/'+$scope.userName, function(message) {
            alert(message)
	    alert(message.body)
            $scope.users = JSON.parse(message.body);
        	
            $scope.$apply();
        });
        
    });

    $scope.sendMessage = function(chat) {
        chat.from = $scope.userName;
      
        $scope.stompClient.send("/app/chat", {}, JSON.stringify(chat));
        $rootScope.$broadcast('sendingChat', chat);
        $scope.chat.message = '';

    };

    $scope.capitalize = function(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    };
 
    $scope.addUser = function(user) {
        $scope.users.push(user);
        $scope.$apply();
    };
 
    
    
    
    
    
    
    $scope.$on('sockConnected', function(event, frame) {
        
  
        $scope.user=$rootScope.user.firstname;
       
        $scope.stompClient.subscribe( "/queue/chats/"+$scope.userName, function(message) {
        	
            $scope.processIncomingMessage(message, false);
        });
        
        
        $scope.stompClient.subscribe("/queue/chats", function(message) {
        	
            $scope.processIncomingMessage(message, true);
        });
        
        
    });

    $scope.$on('sendingChat', function(event, sentChat) {
        chat = angular.copy(sentChat);
        chat.from = 'Me';
        chat.direction = 'outgoing';
        $scope.addChat(chat);
    });

    $scope.processIncomingMessage = function(message, isBroadcast) {
        message = JSON.parse(message.body);
        message.direction = 'incoming';
	message.broadcast=isBroadcast
        if(message.from != $scope.userName) {
        	$scope.addChat(message);
            $scope.$apply(); // since inside subscribe closure
        }
    };

 
    $scope.addChat = function(chat) {
        $scope.chats.push(chat);
    };
 
 
});