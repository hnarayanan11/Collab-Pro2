package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.models.BlogPost;
@Repository
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {
@Autowired
private SessionFactory sessionFactory;
	public void addBlogPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(blogPost);

	}
	public List<BlogPost> getApprovedBlogs() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approvalStatus=true");
		List<BlogPost> blogPosts=query.list();
		return blogPosts;
	}
	public BlogPost getBlogPost(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost) session.get(BlogPost.class, id);
		return blogPost;
	}
	public List<BlogPost> getBlogWaitingForApproval() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approvalStatus=false");
		List<BlogPost> blogPosts=query.list();
		return blogPosts;
	}
	public void updateBlogPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(blogPost);
		
	}
	public void deleteBlogPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.delete(blogPost);
		
	}
	public void updateLike(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(blogPost);
		
	}

}
