/**
 * BlogPostService
 */
app.factory('BlogPostService', function($http) {
	var blogPostService = {}
	var BASE_URL = "http://localhost:8085/Project2middleware"
		
		blogPostService.addBlogPost=function(blog){
		var url=BASE_URL+"/addblogpost"
		return $http.post(url,blog)
	}

	blogPostService.getAllBlogs= function() {
		var url=BASE_URL+"/approvedblogs"
		return $http.get(url)
	}

	return blogPostService;
})