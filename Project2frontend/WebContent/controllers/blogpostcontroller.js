/**
 * BlogPostCtrl
 */
app.controller('BlogPostCtrl',function($scope,$locaiton,BlogPostSrivce){
	$scope.addBlogPost=function(blogPost){
		BlogPostService.addBlogPost(blogPost).then(function(Response){
			
		},function(Response){
			//Not Authentication
			if(Response.status==401)
				$locaiton.path('/login')
			//Exception while inserting blogpost object
			$scope.error=Response.data
		})
	}
})