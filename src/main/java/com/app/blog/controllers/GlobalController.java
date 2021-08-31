package com.app.blog.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.dto.PostDTO;
import com.app.blog.dto.UpdatePostDTO;
import com.app.blog.models.Posts;
import com.app.blog.models.Users;
import com.app.blog.repository.PostRepository;
import com.app.blog.util.EntitiyHawk;
import com.app.blog.util.PostMapper;

@RestController
@RequestMapping("/api")
public class GlobalController extends EntitiyHawk {

	@Autowired
	PostRepository postRepo;

	@GetMapping("/getPostCount")
	public ResponseEntity<?> postCount() {

		// getting no of post in db
		long count = postRepo.count();

		// returning count
		return genericResponse(count);
	}

	@PostMapping("/publish")
	public ResponseEntity<?> publishPost(@RequestBody PostDTO post, @RequestAttribute("user_id") int user_id) {

		// checking if post's body is present
		if (post.getBody() == null) {
			return genericResponse("body should not be empty");
		}

		// converting PostDTO into Posts to save into db
		Posts posts = new Posts();
		posts.setPostTitle(post.getTitle());
		posts.setPostBody(post.getBody());
		posts.setCreatedOn(new Date());
		posts.setUpdatedOn(new Date());
		posts.setIsDeleted(false);

		// setting the creator of post
		Users users = new Users();
		users.setUserId(user_id);
		posts.setPublishedBy(users);

		// saving post in db
		postRepo.save(posts);

		// returning success message
		return genericResponse("Published");
	}

	@GetMapping("/getPost")
	public ResponseEntity<?> getPost() {

		// retrieve all post from db
		List<Posts> posts = postRepo.findAll();

		// using post mapper to hide some details
		PostMapper postMapper = new PostMapper();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Posts p : posts) {
			Map<String, Object> map = postMapper.postDetailsToMap(p);
			list.add(map);
		}

		// returning list of post
		return genericResponse(list);
	}

	@GetMapping("/getPost/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable("postId") int postId) {

		// retrieve post using postid from db
		Optional<Posts> data = postRepo.findById(postId);

		// checking is post is present and not deleted
		if (!data.isPresent() || data.get().getIsDeleted()) {
			return genericResponse("Post Not Found");
		}

		// modifying some details using post mapper and returning modified post
		PostMapper postMapper = new PostMapper();
		return genericResponse(postMapper.postDetailsToMap(data.get()));
	}

	@GetMapping("/getPostByUser/{userId}")
	public ResponseEntity<?> getPostByUserId(@PathVariable("userId") int userId) {

		// retrieving list of post belong to specified user
		List<Posts> posts = postRepo.findByPublishedByUserId(userId);

		// checking is post is present created by specified user
		if (posts.isEmpty()) {

			return genericResponse("No posts by user Id " + userId);
		} else {

			// modifying some details using post mapper
			PostMapper postMapper = new PostMapper();
			List<Map<String, Object>> list = new ArrayList<>();
			for (Posts p : posts) {
				Map<String, Object> map = postMapper.postDetailsToMap(p);
				list.add(map);
			}

			// returning modified list of posts
			return genericResponse(list);
		}
	}

	@PostMapping("/updatePost")
	public ResponseEntity<?> updatePost(@RequestBody UpdatePostDTO post, @RequestAttribute("user_id") int user_id) {

		// retrieving specified post from db
		Optional<Posts> postOptional = postRepo.findById(post.getPost_id());

		// checking if post is not present in db and deleted
		if (!postOptional.isPresent() || postOptional.get().getIsDeleted())
			return genericError("Post Not Found");

		// checking user is author of updating post
		if (postOptional.get().getPublishedBy().getUserId() != user_id)
			return genericError("Not Authorized to Update this post");

		// updating post
		Posts postNew = postOptional.get();
		postNew.setPostTitle(post.getTitle());
		postNew.setPostBody(post.getBody());
		postNew.setUpdatedOn(new Date());

		// saving updated post in db
		postRepo.save(postNew);

		// returning success response
		return genericResponse("Post updated");

	}

	@GetMapping("/deletePost/{postId}")
	public ResponseEntity<?> delPost(@PathVariable("postId") int postId, @RequestAttribute("user_id") int user_id) {

		// retrieving specified post id from db
		Optional<Posts> postOptional = postRepo.findById(postId);

		// checking if post is not present in db and deleted
		if (!postOptional.isPresent() || postOptional.get().getIsDeleted())
			return genericError("Post Not Found");

		// checking user is author of deleting post
		if (postOptional.get().getPublishedBy().getUserId() != user_id)
			return genericError("Not Authorized to Delete this post");

		// deleting post and save
		postOptional.get().setIsDeleted(true);
		postRepo.save(postOptional.get());

		// return success message
		return genericResponse("Post Deleted");
	}

}
