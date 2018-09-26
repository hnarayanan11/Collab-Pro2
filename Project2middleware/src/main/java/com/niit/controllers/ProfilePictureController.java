package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.models.ErrorClazz;
import com.niit.models.ProfilePicture;

@RestController
public class ProfilePictureController {
	@Autowired
	private ProfilePictureDao profilePictureDao;

	//call this url directly from HTML <html><form action="http://locahost:8085/Project2middleware/uploadprofilepicture"></form></html>
	@RequestMapping(value="/uploadimage",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image, HttpSession session) {
		System.out.println("Entering to the upload picutre");
		String email=(String)session.getAttribute("loggedInUser");
		if(email==null) {
			ErrorClazz errorClazz=new ErrorClazz(4,"Uauthorized access.. please login.....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setEmail(email);
		profilePicture.setImage(image.getBytes());
		profilePictureDao.uploadProfilePicture(profilePicture);
		return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
	}
	
	//return image directly, use this image in img tag
	//<img src="http://localhost:8085/Project2middleware.getimage/"
	@RequestMapping(value="/getimage",method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePicture(@RequestParam String email, HttpSession session) {
		System.out.println(email);
		String authEmail=(String) session.getAttribute("loggedInUser");
		if(authEmail==null) {
			return null;
		}
		ProfilePicture profilePicture=profilePictureDao.getProfilePicture(email);
		if(profilePicture==null) //No image
			return null;
		else
			return profilePicture.getImage();
	}
}
