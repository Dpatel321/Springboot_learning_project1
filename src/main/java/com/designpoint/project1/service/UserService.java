package com.designpoint.project1.service;

import java.util.List;

import com.designpoint.project1.paylods.UserDto;




public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto usre, Integer userID);

	UserDto getUserById(Integer userID);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userID);
}
