package com.app.blog.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RegisterUserDTO {

	@NotBlank(message = "email Email cannot be blank")
	@Length(max = 40)
	@Valid
	String email;

	@NotBlank(message = "Name cannot be blank")
	@Length(max = 40)
	@Valid
	String name;

	@NotBlank(message = "Password cannot be blank")
	@Length(min = 3, max = 45)
	@Valid
	String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RegisterUserDTO [email=" + email + ", name=" + name + ", password=" + password + "]";
	}
}
