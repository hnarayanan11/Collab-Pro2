/**
 * FriendCtrl 
 */
app.controller('FriendCtrl',function($scope, $location, FriendService){
	//data from Controller To View
	//$scope.suggestedUsers has to get initialized automatically
	$scope.viewed=false
	
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
	
	function getPendingRequests(){
		FriendService.getPendingRequests().then(function(response){
			$scope.pendingRequests=response.data;//List<user>
			//response.data=[select f.formId from Friend f where f.toId=? and status='p']
			console.log(response.data)
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.acceptRequest=function(pendingRequest){//pending request contains a friend object
		FriendService.acceptRequest(pendingRequest).then(function(response){
			getPendingRequests()// pendingRequest.html [accept] -> friendslist.html
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.deleteRequest=function(pendingRequest){
		FriendService.deleteRequest(pendingRequest).then(function(response){
			getPendingRequests();
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.detailsViewed=function(id){
		$scope.viewed=!($scope.viewed)
	}
	
	FriendService.listOfFriends().then(function(response){
		console.log(response.data)
		$scope.friendsDetatils=response.data//List<User> [toId and fromId]
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	getSuggestedUsers();
	getPendingRequests();
})