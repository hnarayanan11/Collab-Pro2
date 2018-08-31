package com.niit.dao;

import java.util.List;

import com.niit.models.BlogPost;

public interface BlogPostDao {
	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getApprovedBlogs();
	BlogPost getBlogPost(int id);
	List<BlogPost> getBlogWaitingForApproval();
}
