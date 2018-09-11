/**
 * NotificationCtrl getnotification/923
 */
app.controller('NotificationCtrl', function($scope, NotificationService, $routeParams, $location, $rootScope) {
	var id = $routeParams.id
	console.log(id)
	NotificationService.getNotification(id).then(function(Response) {
		console.log(Response.data)
		$scope.notification = Response.data // select * from notification whereid=?
	}, function(resposne) {
		if (Response.status == 401)
			$location.path('/login')
	})

	NotificationService.updateNotification(id).then(function(Response) {
		console.log(id)
		getNotificationNotViewed()
	}, function(Response) {
		if (Response.status == 401)
			$location.path('/login')
	})

	function getNotificationNotViewed() {
		NotificationService.getNotificationNotViewed().then(function(response) {
			//update the value of the variables
			//response.data = [list of notification not yet viewed by the user]
			$rootScope.notifications=response.data
			$rootScope.notificationCount=$rootScope.notifications.length
		}, function(response) {
			if(response.status==401)
				$location.path('/login')
		})
	}
})