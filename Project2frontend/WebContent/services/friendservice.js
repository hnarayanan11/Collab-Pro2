/**
 * FriendService
*/
app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL="http://localhost:8085/Project2middleware"
	friendService.getSuggestedUsers=function(){
		console.log('getsuggestedusers')
		return $http.get(BASE_URL+'/suggestedusers')
	}
	return friendService
})