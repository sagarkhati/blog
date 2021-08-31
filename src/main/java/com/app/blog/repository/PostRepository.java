package com.app.blog.repository;

import com.app.blog.models.Posts;
//import com.app.blog.models.Users;
import java.util.List;
//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Integer> {
	List<Posts> findByPublishedByUserId(Integer userId);
}