package com.niit.dao;

import java.util.List;

import com.niit.models.BlogPost;

public interface BlogPostDao {
	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getApprovedBlogs();
	BlogPost getBlogPost(int id);
	List<BlogPost> getBlogWaitingForApproval();
	void updateBlogPost(BlogPost blogPost);//updating approval status from false to true and can also be used for updating content of the blogpost
	void deleteBlogPost(BlogPost blogPost);
	void updateLike(BlogPost blogPost);
}
