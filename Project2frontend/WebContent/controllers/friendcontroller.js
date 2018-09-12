/**
 * FriendCtrl 
 */
app.controller('FriendCtrl',function($scope, $location, FriendService){
	//data from Controller To View
	//$scope.suggestedUsers has to get initialized automatically
	FriendService.getSuggestedUsers().then(function(response){
		console.log('getsuggestedusers')
		$scope.suggestedUsers=response.data//List<User>[A-(B U C)]
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
})