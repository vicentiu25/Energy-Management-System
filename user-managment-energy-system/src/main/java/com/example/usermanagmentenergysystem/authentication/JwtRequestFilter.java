package com.example.usermanagmentenergysystem.authentication;
import com.example.usermanagmentenergysystem.model.User;
import com.example.usermanagmentenergysystem.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JWTokenCreator jwTokenCreator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        Long userId = 0L;
        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            //jwtToken = jwtToken.substring(0, jwtToken.length() - 2);
            try {
                userId = jwTokenCreator.getUserIdFromToken(jwtToken);
                username = this.userRepository.matchUserId(userId).getUsername();
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }

            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = this.userRepository.matchUser(username);

                // if token is valid configure Spring Security to manually set
                // authentication
                if (jwTokenCreator.validateToken(jwtToken, user)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, null);
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
//        } else {
//            System.out.println("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }

}
