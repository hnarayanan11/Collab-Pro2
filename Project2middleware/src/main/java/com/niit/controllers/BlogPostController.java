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

import com.niit.dao.BlogPostDao;
import com.niit.dao.NotificationDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogPost;
import com.niit.models.ErrorClazz;
import com.niit.models.Notification;
import com.niit.models.User;

@RestController
public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotificationDao notificationDao;

	@RequestMapping(value = "/addblogpost", method = RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost, HttpSession session) {
		String email = (String) session.getAttribute("loggedInUser");
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		blogPost.setPostedOn(new Date());
		blogPost.setPostedBy(userDao.getUser(email));
		try {
			blogPostDao.addBlogPost(blogPost);
		} catch (Exception e) {
			ErrorClazz errorClazz = new ErrorClazz(6, "Unable to post blogpsoe.." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approvedblogs",method=RequestMethod.GET)
	public ResponseEntity<?> getApproveBlogs(HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");		
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> approvalBlogs=blogPostDao.getApprovedBlogs();
		System.out.println("List of blogs "+approvalBlogs);
		return new ResponseEntity<List<BlogPost>>(approvalBlogs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(@PathVariable int id, HttpSession session){
		System.out.println("Entering into getblog method");
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost=blogPostDao.getBlogPost(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/blogwaitforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsWaitingForApproval(HttpSession session){
		//Authentication
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		//Authorization - only admin can view list of blogs waiting for approval
		User user=userDao.getUser(email);
		System.out.println(user.getRole()=="ADMIN");//memory address are different
		System.out.println("ADMIN"=="ADMIN");//Same memory address are same
		System.out.println(user.getRole().equals("ADMIN"));
		if(!user.getRole().equals("ADMIN")) {
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogPostWaitingForApproval=blogPostDao.getBlogWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(blogPostWaitingForApproval,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveblogpost",method=RequestMethod.PUT)
	public ResponseEntity<?> approveBlogpost(@RequestBody BlogPost blogPost, HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")) {
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		//how to update approvalstatus
		blogPost.setApprovalStatus(true);
		blogPostDao.updateBlogPost(blogPost);
		Notification notification=new Notification();
		notification.setApprovalStatus("Approved");
		notification.setBlogPost(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notificationDao.addNotification(notification);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateblogpostuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateBlogPost(@RequestBody BlogPost blogPost, HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		blogPost.setApprovalStatus(false);
		blogPost.setPostedOn(new Date());
		blogPostDao.updateBlogPost(blogPost);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectblogpost",method=RequestMethod.PUT)
	public ResponseEntity<?> rejectBlogPost(@RequestBody BlogPost blogPost,@RequestParam String rejectionReason,HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")) {
			ErrorClazz errorClazz=new ErrorClazz(6,"Access Denied....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		Notification notification=new Notification();
		notification.setApprovalStatus("Rejected");
		notification.setBlogPost(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notification.setRejectReason(rejectionReason);
		notificationDao.addNotification(notification);
		blogPostDao.deleteBlogPost(blogPost);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/inclike",method=RequestMethod.PUT)
	public ResponseEntity<?> incLike(@RequestBody BlogPost blogPost, HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		blogPost.setLikes(blogPost.getLikes()+1);
		blogPostDao.updateLike(blogPost);		
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/dcrlike",method=RequestMethod.PUT)
	public ResponseEntity<?> dcrLike(@RequestBody BlogPost blogPost, HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		blogPost.setDislikes(blogPost.getDislikes()+1);
		blogPostDao.updateLike(blogPost);		
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
}
