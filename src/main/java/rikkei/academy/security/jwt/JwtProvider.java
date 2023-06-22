package rikkei.academy.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rikkei.academy.security.usersPrincipal.UserPrincipal;
import io.jsonwebtoken.*;

import java.util.Date;


@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private String SECRET_KEY = "minhkhiet1";

    private long TOKEN_EXPIRED = 86400000;
    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+TOKEN_EXPIRED))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token){
        try {
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return true;
        }catch (ExpiredJwtException e) {
            logger.error("Failed -> Expired Token Message {}", e.getMessage());
        }catch (UnsupportedJwtException e) {
            logger.error("Failed -> Unsupported Token Message {}", e.getMessage());
        }catch (MalformedJwtException e) {
            logger.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        }catch (SignatureException e) {
            logger.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        }catch (IllegalArgumentException e) {
            logger.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }
    public String getUserNameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
