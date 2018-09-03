/**
 * BlogPostCtrl
 */
app.controller('BlogPostCtrl',function($scope, BlogPostService, $location, $rootScope){
	
	$scope.addBlogPost=function(blog){
		BlogPostService.addBlogPost(blog).then(function(Response){
			getAllBlogs()
			$location.path('/home')
		},function(Response){
			if(Response.status==401)
				$location.path('/login')
			$scope.error=Response.data
		})
	}
	
	//List of blogs approved
	function getAllBlogs(){
		BlogPostService.getAllBlogs().then(function(Response){
			console.log('Successfully retrieve '+Response.data)
			console.log('Getting the data')
			$scope.blogsApproved=Response.data
		},function(Response){
			if(Response.status==401)
				$location.path('/login')
			$scope.error=Response.data
		})
	}
	
	function getBlogsWaitingForApproval(){
		BlogPostService.getBlogsWaitingForApproval().then(function(Response){
			console.log('Successfully entered into getblogpostwaiting '+Response.data)
			$scope.blogsWaitingForApproval=Response.data
		},function(Response){
			if(Response.status==401)
				$location.path('/login')
		})
	}
	
	getAllBlogs()
	//call the funciton only for ADMIN role
	if($rootScope.user.role=='ADMIN')
		getBlogsWaitingForApproval()
})