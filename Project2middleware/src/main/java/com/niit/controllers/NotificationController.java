package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.NotificationDao;
import com.niit.models.ErrorClazz;
import com.niit.models.Notification;

@RestController
public class NotificationController {
	@Autowired
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/notifications",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationNotViewed(HttpSession session){
		String email = (String) session.getAttribute("loggedInUser");
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		List<Notification> notifications=notificationDao.getNotificationNotViewed(email);
		return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getnotification/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotification(@PathVariable int id, HttpSession session){
		String email = (String) session.getAttribute("loggedInUser");
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		Notification notification=notificationDao.getNotificaiton(id);
		return new ResponseEntity<Notification>(notification,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatenotificaiton/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> UpdateNotification(@PathVariable int id, HttpSession session){
		String email = (String) session.getAttribute("loggedInUser");
		if (email == null) {
			ErrorClazz errorClazz = new ErrorClazz(5, "Unauthorized access....");
			return new ResponseEntity<ErrorClazz>(errorClazz, HttpStatus.UNAUTHORIZED);
		}
		notificationDao.updateNotification(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
