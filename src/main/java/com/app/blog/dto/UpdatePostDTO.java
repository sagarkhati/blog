package com.app.blog.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UpdatePostDTO {

	@NotBlank(message = "post_id cannot be blank")
	@Valid
	Integer post_id;
	
	@NotBlank(message = "title cannot be blank")
	@Length(max = 450)
	@Valid
	String title;
	
	@NotBlank(message = "body cannot be blank")
	@Length(max = 5000)
	@Valid
	String body;
	
	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
