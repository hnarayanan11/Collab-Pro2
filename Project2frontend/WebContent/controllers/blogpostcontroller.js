/**
 * BlogPostCtrl
 */
app.controller('BlogPostCtrl',function($scope, BlogPostService, $location){
	
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
	getAllBlogs()
})