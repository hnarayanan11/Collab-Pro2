package com.niit.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.models.ErrorClazz;
import com.niit.models.Job;
import com.niit.models.User;

@RestController
public class JobController {
	@Autowired
	private JobDao jobDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/addjob", method = RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job, HttpSession session) {

		String email = (String) session.getAttribute("loggedInUser"); // Check for
		// Authentication
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(4, "Uauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}

		// Check for Authorization(Role)
		System.out.println("Entered Job Add method");
		// String email = "adam@abc.com";
		User user = userDao.getUser(email);
		if (!user.getRole().equals("ADMIN")) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Access Denied.... You are not authorized to post a job");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		try {
			job.setPostedOn(new Date());
			jobDao.saveJob(job);
		} catch (Exception e) {
			ErrorClazz errorClazz = new ErrorClazz(6, "Unable to post job details");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
}