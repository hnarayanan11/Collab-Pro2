/**
 * UserService
 */
app.factory('UserService',function($http){
	var userService={}
	var BASE_URL="http://localhost:8085/Project2middleware"
	userService.registration=function(user){
		var url=BASE_URL+"/register"
		return $http.post(url,user)
	}
	
	return userService;
})