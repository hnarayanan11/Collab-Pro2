/**
 * NotificationService
 */
app.factory('NotificationService', function($http) {
	var notificationService = {}
	var BASE_URL = "http://localhost:8085/Project2middleware"

	notificationService.getNotification = function(id) {
		console.log(id);
		return $http.get(BASE_URL + '/getnotification/' + id)
	}

	notificationService.updateNotification = function(id) {
		console.log(BASE_URL+'/updatenotification/'+id)
		return $http.put(BASE_URL + '/updatenotificaiton/' + id)
	}

	notificationService.getNotificationNotViewed = function() {
		return $http.get(BASE_URL + '/notifications')
	}

	return notificationService;
})