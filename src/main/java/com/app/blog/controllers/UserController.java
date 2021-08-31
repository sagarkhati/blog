package com.app.blog.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.dto.LoginDto;
import com.app.blog.dto.RegisterUserDTO;
import com.app.blog.models.Users;
import com.app.blog.repository.UserRepository;
import com.app.blog.util.EntitiyHawk;
import com.app.blog.util.JWTUtils;

@RestController
@RequestMapping("/")
public class UserController extends EntitiyHawk {

	@Autowired
	UserRepository userRepo;

	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDTO registerUser, BindingResult bindingResult) {

		// checking if there is any validation error in RegisterUserDTO field(s)
		if (bindingResult.hasErrors()) {

			// getting error field(s)
			// List<FieldError> errors = bindingResult.getFieldErrors();
			FieldError error = bindingResult.getFieldError();

			// getting error message
			String errorMessage = error.getDefaultMessage();

			// returning error message
			return genericError(errorMessage);
		} else {

			// converting RegisterUserDTO into Users to save into db
			Users user = new Users();
			user.setUserName(registerUser.getName());
			user.setEmail(registerUser.getEmail());
			user.setPassword(registerUser.getPassword());

			// saving user into db
			userRepo.save(user);

			// returning success message
			return genericSuccess("User Registered");
		}
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginUser, BindingResult bindingResult) {

		// checking if there is any validation error in RegisterUserDTO field(s)
		if (bindingResult.hasErrors()) {

			// getting error field(s)
			// List<FieldError> errors = bindingResult.getFieldErrors();
			FieldError error = bindingResult.getFieldError();

			// getting error message
			String errorMessage = error.getDefaultMessage();

			// returning error message
			return genericError(errorMessage);
		} else {

			// searching user in db using email
			Optional<Users> userOptional = userRepo.findByEmail(loginUser.getEmail());

			// Checking if email and password is correct
			if (!userOptional.isPresent() || !userOptional.get().getPassword().equals(loginUser.getPassword())) {
				return genericError("Invalid Username or Password");
			}

			// creating JWT token
			JWTUtils jwt = new JWTUtils();
			String token = jwt.CreateJWTToken(userOptional.get());

			// returning JWT token
			return genericSuccess(token);
		}
	}
}
