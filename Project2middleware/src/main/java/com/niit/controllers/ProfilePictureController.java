package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.models.ErrorClazz;
import com.niit.models.ProfilePicture;

@Controller
public class ProfilePictureController {
	@Autowired
	private ProfilePictureDao profilePictureDao;

	//call this url directly from HTML <html><form action="http://locahost:8085/Project2middleware/uploadprofilepicture"></form></html>
	@RequestMapping(value="/uploadimage",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(HttpSession session, @RequestBody CommonsMultipartFile image) {
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
	/*public @ResponseBody byte[] getProfilePicture(String email, HttpSession session) {
		
	}*/
}
