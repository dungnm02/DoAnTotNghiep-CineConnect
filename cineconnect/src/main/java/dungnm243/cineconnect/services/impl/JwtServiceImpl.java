package dungnm243.cineconnect.services.impl;

import dungnm243.cineconnect.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Override
    public String generateToken(UserDetails userDetails) {
        return buildToken(userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Lấy thông tin từ token
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();

        // Kiểm tra username và thời gian hết hạn của token
        return username.equals(userDetails.getUsername()) && expiration.after(new Date(System.currentTimeMillis()));
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Tạo token
     *
     * @param userDetails
     * @return
     */
    private String buildToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Chuyển secret key từ dạng String sang dạng Key
     *
     * @return
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Lấy tất cả claims từ token
     *
     * @param token token
     * @return Claims thông tin từ token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
