package com.niit.dao;

import com.niit.models.User;

public interface UserDao {
	void registration(User user);
	boolean isEmailUnique(String email);
	User login(User user);//i/p - user with email and pwd
						  //o/p - user with email, pwd, fname, lname, pnumber, role, online 
						  //or null value
	void updateUser(User user);
	User getUser(String email);
}
