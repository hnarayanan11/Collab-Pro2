package com.niit.dao;

import com.niit.models.User;

public interface UserDao {
	void registration(User user);
	boolean isEmailUnique(String email);
}
