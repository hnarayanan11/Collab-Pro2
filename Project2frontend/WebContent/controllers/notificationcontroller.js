/**
 * NotificationCtrl
 * getnotification/923
 */
app.controller('NotificationCtrl',function($scope, NotificationService, $routeParams, $location){
	var id=$routeParams.id
	console.log(id)
	NotificationService.getNotification(id).then(function(Response){
		console.log(Response.data)
		$scope.notification=Response.data // select * from notification where id=?
	},function(resposne){
		if(Response.status==401)
			$location.path('/login')
	})
	
	/*Notification.updateNotification(id).then(function(Response){
		
	},function(Response){
		if(Response.status==401)
			$location.path('/login')
	})*/
})