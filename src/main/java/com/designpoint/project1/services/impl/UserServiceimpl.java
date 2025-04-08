package com.designpoint.project1.services.impl;

import com.designpoint.project1.entities.*;
import java.util.List;
import java.util.stream.Collectors;

import com.designpoint.project1.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.designpoint.project1.paylods.UserDto;
import com.designpoint.project1.repositoris.UserRepo;
import com.designpoint.project1.service.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtotoUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.usertoDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userID) {

		User user = this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.usertoDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userID) {

		User user = this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

		return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map((user) -> this.usertoDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userID) {

		User user = this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
		this.userRepo.delete(user);

		

	}

	public User dtotoUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		return user;
	}

	public UserDto usertoDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		return userDto;

	}

}
