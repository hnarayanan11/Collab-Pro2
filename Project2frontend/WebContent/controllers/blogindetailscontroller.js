/**
 * BLogInDetailsCtrl 
 * #/getblog/id
 */
app.controller('BlogInDetailsCtrl',function($scope, BlogPostService, $location, $routeParams, $sce){
	var id=$routeParams.id
	console.log("Id on request params"+id)
	
	//function or statement // select * from bogpost where id=?
	BlogPostService.getBlog(id).then(function(Response){
		$scope.blogPost=Response.data //result of the query select * from  blogpost where id=922; //BLogPost object
		console.log(Response.data)
		$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
	},function(Response){
		if(Response.status==401)
			$location.path('/loign')
		$scope.error=Response.data
	})
	
	$scope.approvedBlogPost=function(blogPost){
		BlogPostService.approvedBlogPost(blogPost).then(function(Response){
			$location.path('/getblogswaitingofapproval')
		},function(Response){
			if(Response.status==401)
				$location.path('/loign')
			$scope.error=Response.data
		})
	}
})