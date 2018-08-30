/**
 * BLogInDetailsCtrl 
 */
app.controller('BlogInDetailsCtrl',function($scope,BlogPostService,$location,$routeParams){
	var id=$routeParams.id
	
	//function or statement
	BlogPostService.getBlog(id).then(function(Response){
		$scope.blog=Resopnse.data //result of the query select * from  blogpost where id=922;
	},function(Response){
		
	})
})