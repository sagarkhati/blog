package com.app.blog.util;

import com.app.blog.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JWTUtils {

	public String CreateJWTToken(Users user) {

		Claims claims = Jwts.claims();
		claims.put("name", user.getUserName());
		claims.put("email", user.getEmail());
		claims.put("user_id", user.getUserId());
		claims.setSubject("MY Blog");
		claims.setIssuedAt(new Date());
		//claims.setExpiration(new Date(new Date().getTime() + 1 * 60 * 1000));

		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET)
				.compact();
    
		return token;
	}
}
