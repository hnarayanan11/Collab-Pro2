package com.niit.dao;

import java.util.List;

import com.niit.models.BlogComment;

public interface BlogCommentDao {
	void addBlogComment(BlogComment blogComment);
	List<BlogComment> getBlogComment(int blogPostId);
	void deleteBlogComment(BlogComment blogComment);
}