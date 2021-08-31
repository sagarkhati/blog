package com.app.blog.util;

import com.app.blog.models.Posts;
import java.util.HashMap;
import java.util.Map;

public class PostMapper {

	public Map<String, Object> postDetailsToMap(Posts post) {
		Map<String, Object> map = new HashMap<>();
		map.put("post_id", post.getPostId().toString());
		map.put("title", post.getPostTitle());
		map.put("body", post.getPostBody());
		map.put("created_on", post.getCreatedOn());
		map.put("created_by", post.getPublishedBy().getUserName());
		map.put("last_updated", post.getUpdatedOn());
		return map;
	}
}
