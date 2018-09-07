/**
 * BLogInDetailsCtrl 
 * #/getblog/id
 */
app.controller('BlogInDetailsCtrl',function($scope, BlogPostService, $location, $routeParams, $sce){
	var id=$routeParams.id
	$scope.isRejected=false
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
	
	$scope.updateBlogPost=function(blogPost){
		BlogPostService.updateBlogPost(blogPost).then(function(response){
			$location.path('/getblogs')
		},function(response){
			if(Response.status==401)
				$location.path('/loign')
			$scope.error=Response.data
		})
	}
	
	$scope.rejectBlogPost=function(blogPost){
		BlogPostService.rejectBlogPost(blogPost,$scope.rejectionReason).then(function(response){
			console.log(blogPost)
			$location.path('/getblogswaitingofapproval')
		},function(response){
			if(Response.status==401)
				$location.path('/loign')
		})
	}
	
	$scope.incLike=function(blogPost){
		BlogPostService.incLike(blogPost).then(function(Response){
			$location.path('/getblogs')
		},function(Response){
			if(Response.status==401)
				$location.path('/loign')
		})
	}
	
	$scope.dcrLike=function(blogPost){
		BlogPostService.dcrLike(blogPost).then(function(Response){
			$location.path('/getblogs')
		},function(Response){
			if(Response.status==401)
				$location.path('/loign')
		})
	}
	
	$scope.showTextArea=function(){
		$scope.isRejected=!$scope.isRejected
	}
})