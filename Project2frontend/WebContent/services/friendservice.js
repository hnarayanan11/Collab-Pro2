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
	
	friendService.sendFriendRequest=function(toId){
		return $http.post(BASE_URL+'/friendrequest',toId)
	}
	
	friendService.getPendingRequests=function(){
		return $http.get(BASE_URL+'/pendingrequests')
	}
	
	friendService.acceptRequest=function(pendingRequest){
		return $http.put(BASE_URL+'/acceptfriendrequest',pendingRequest)
	}
	
	friendService.deleteRequest=function(pendingRequest){
		return $http.put(BASE_URL+'/deletefriendrequest',pendingRequest)
	}
	
	friendService.listOfFriends=function(){
		return $http.get(BASE_URL+'/listoffriends');
	}
	
	return friendService
})