package rikkei.academy.security.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import rikkei.academy.security.usersPrincipal.UserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private  JwtProvider jwtProvider;

   @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getTokenFromRequest(request);
            String username = jwtProvider.getUserNameFromToken(jwtToken);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (userDetails!=null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }catch (Exception e){
            logger.error("Failed-> unauthenticated message {}",e.getMessage());
        }
        filterChain.doFilter(request,response);

    }
    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header!=null&&header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
