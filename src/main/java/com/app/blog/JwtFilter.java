package com.app.blog;

import com.app.blog.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) sr;
		
		// accessing bearer token from header
		final String requestTokenHeader = request.getHeader("Authorization");

		// checking if bearer token present
		if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
			throw new RuntimeException("Unable to read JSON value");
		}

		try {
			
			// parsing jwt token
			Claims body = Jwts.parser().setSigningKey(Constants.JWT_SECRET)
					.parseClaimsJws(requestTokenHeader.substring(7)).getBody();
			request.setAttribute("user_id", body.get("user_id"));	
			
		} catch (SignatureException sex) {
			throw new RuntimeException("Unable to read JSON value");
		} catch (Exception ex) {
			throw new RuntimeException("Unable to read JSON value");
		}

		fc.doFilter(request, sr1);
	}
}
