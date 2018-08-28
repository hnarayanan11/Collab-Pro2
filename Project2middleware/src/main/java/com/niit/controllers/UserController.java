package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDao;
import com.niit.models.ErrorClazz;
import com.niit.models.User;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;
	
	public UserController() {
		System.out.println("User Controller Bean is Created");
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody User user){
		try {
			//check if email is unique
			//if email is not unique
			if(!userDao.isEmailUnique(user.getEmail())) {
				ErrorClazz errorClazz=new ErrorClazz(2,"Email is already exists... ");
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			userDao.registration(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e) {
			ErrorClazz errorClazz=new ErrorClazz(1,"Something went worng"+ e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//middleware get the data from angular js client in JSON fmt
	//ex/ {'email':'adame@abc.com','password':'qwerst'}
	@RequestMapping(value="/login",method=RequestMethod.PUT)
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session){
		System.out.println("Entered login");
		User validUser=userDao.login(user);
		if(validUser==null) { //Invalid credentials Email/pwd is incorrect
			System.out.println("login email or password is invalid");
			ErrorClazz errorClazz=new ErrorClazz(4,"Invalid email/password....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);			
		}else { // valid credentails, valid email and password
			System.out.println("login success");
			validUser.setOnline(true);
			userDao.updateUser(validUser);
			session.setAttribute("loggedInUser", validUser.getEmail());
			System.out.println("Session Attribute"+session.getAttribute("loggedInUser"));
			System.out.println("Session Id" + session.getId());
			System.out.println("Created On"+ session.getCreationTime());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	
	/*@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
		System.out.println("Session Attribute"+session.getAttribute("loggedInUser"));
		System.out.println("Session Id" + session.getId());
		System.out.println("Created On"+ session.getCreationTime());
		String email= (String) session.getAttribute("loggedInUser");
		if(email == null) {
			ErrorClazz errorClazz=new ErrorClazz(4,"email is null");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}			
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}*/
	
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
		String email=(String) session.getAttribute("loggedInUser");
		System.out.println("logout method "+email +" email for logged in user" );
		if(email==null) {
			ErrorClazz errorClazz=new ErrorClazz(4,"Uauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		user.setOnline(false);
		userDao.updateUser(user);
		session.removeAttribute("loggedInUser");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody User user,HttpSession session){
		//Check for Authentication
		String email=(String)session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz=new ErrorClazz(4,"Uauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try {
		userDao.updateUser(user);}catch(Exception e) {
			ErrorClazz errorClazz=new ErrorClazz(5,"unable to update user detials.....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
