package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogCommentDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogComment;
import com.niit.models.BlogPost;
import com.niit.models.ErrorClazz;
import com.niit.models.User;

@RestController
public class BlogCommentController {
	@Autowired
	private BlogCommentDao blogCommentDao;
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/addblogcomment",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogPost blogPost, @RequestParam  String commentTxt, HttpSession session){
		String email = (String) session.getAttribute("loggedInUser"); // Check for
		// Authentication
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(4, "Unauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		BlogComment blogComment=new BlogComment();
		blogComment.setCommentTxt(commentTxt);
		blogComment.setBlogPost(blogPost);
		User user=userDao.getUser(email);
		blogComment.setCommentedBy(user);
		blogComment.setCommentedOn(new Date());
		blogCommentDao.addBlogComment(blogComment);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblogcomment/{blogPostId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComment(@PathVariable int blogPostId, HttpSession session){
		String email = (String) session.getAttribute("loggedInUser"); // Check for
		// Authentication
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(4, "Unauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		List<BlogComment> comments=blogCommentDao.getBlogComment(blogPostId);
		return new ResponseEntity<List<BlogComment>>(comments,HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteblogcomment",method=RequestMethod.PUT)
	public ResponseEntity<?> deleteBlogComment(@RequestBody BlogComment blogComment, HttpSession session){
		String email = (String) session.getAttribute("loggedInUser"); // Check for
		// Authentication
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(4, "Unauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		blogCommentDao.deleteBlogComment(blogComment);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}

}
