/**
 * UserCtrl
 */
app.controller('UserCtrl', function($scope, UserService, $location) {
	// function for registration
	$scope.registration = function(user) {
		UserService.registration(user).then(				
				function(response) {
					console.log(user)
					alert('Registrered successfully... please login again')
					$location.path('/login')
				}, function(response) {
					$scope.error=response.data //ErrorCalzz object
				})
	}
})