/**
 * UserCtrl
 */
app.controller('UserCtrl', function($scope, UserService, $location, $rootScope, $cookieStore) {
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
	
	$scope.login=function(user){
		UserService.login(user).then(function(response){
			alert("Login Success")
			$cookieStore.put('userDetails',response.data)
			$rootScope.user=response.data //User Object
			console.log("login success ",$scope.user)
			$location.path('/home')
		},function(response){
			alert("Login Error")
			console.log("login error");
			$scope.error=response.data			
		})
	}
	
	/*UserService.getAllJobs().then(function(response){
		
	},function(response){
		$location.path('/login')
	})*/
})