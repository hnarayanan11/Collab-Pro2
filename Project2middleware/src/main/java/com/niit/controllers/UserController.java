package com.niit.controllers;

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
	
	@RequestMapping(value="/login",method=RequestMethod.PUT)
	//middleware get the data from angular js client in JSON fmt
	//ex/ {'email':'adame@abc.com','password':'qwerst'}
	public ResponseEntity<?> login(@RequestBody User user){
		User validUser=userDao.login(user);
		if(validUser==null) { //Invalid credentials Email/pwd is incorrect
			return null;
		}else { // valid credentails, valid email and password
			return null;
		}
	}
}
