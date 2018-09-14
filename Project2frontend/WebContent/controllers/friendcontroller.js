/**
 * FriendCtrl 
 */
app.controller('FriendCtrl',function($scope, $location, FriendService){
	//data from Controller To View
	//$scope.suggestedUsers has to get initialized automatically
	function getSuggestedUsers(){
		FriendService.getSuggestedUsers().then(function(response){
			console.log('getsuggestedusers')
			$scope.suggestedUsers=response.data//List<User>[A-(B U C)]
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	
	$scope.sendFriendRequest=function(toId){//toId is an User object, friend.setToId
		FriendService.sendFriendRequest(toId).then(function(response){
			alert('Friend Request has been sent successfully...')
			getSuggestedUsers();
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	getSuggestedUsers();
})