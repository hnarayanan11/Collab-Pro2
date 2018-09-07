/**
 * BlogPostService
 */
app.factory('BlogPostService', function($http) {
	var blogPostService = {}
	var BASE_URL = "http://localhost:8085/Project2middleware"
		
		
		//Query - insert into blogpost values(?,,,,) - DAO layer
		blogPostService.addBlogPost=function(blog){
		var url=BASE_URL+"/addblogpost"
		return $http.post(url,blog)
	}

	//Query - select * from blogpost where approvalstatus=true - in DAO
	blogPostService.getAllBlogs= function() {
		var url=BASE_URL+"/approvedblogs"
		return $http.get(url)
	}
	
	blogPostService.getBlog=function(id){
		var url=BASE_URL+"/getblog/"+id
		return $http.get(url)
	}
	
	blogPostService.getBlogsWaitingForApproval=function(){
		return $http.get(BASE_URL+"/blogwaitforapproval")
	}
	
	blogPostService.approvedBlogPost=function(blogPost){
		return $http.put(BASE_URL+"/approveblogpost",blogPost)
	}
	
	blogPostService.updateBlogPost=function(blogPost){
		return $http.put(BASE_URL+"/updateblogpostuser",blogPost)
	}
	
	blogPostService.rejectBlogPost=function(blogPost,rejectionReason){
		console.log(blogPost)
		return $http['put'](BASE_URL+"/rejectblogpost?rejectionReason="+rejectionReason,blogPost)
	}
	
	blogPostService.getNotificationNotViewed=function(){
		return $http.get(BASE_URL+'/notifications')
	}
	
	blogPostService.incLike=function(blogPost){
		return $http.put(BASE_URL+'/inclike',blogPost)
	}
	
	blogPostService.dcrLike=function(blogPost){
		return $http.put(BASE_URL+'/dcrlike',blogPost)
	}
	return blogPostService;
})