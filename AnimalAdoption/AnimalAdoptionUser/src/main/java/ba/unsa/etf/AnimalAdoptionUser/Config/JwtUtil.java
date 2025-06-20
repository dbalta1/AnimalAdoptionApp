package ba.unsa.etf.AnimalAdoptionUser.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String jwtIssuer = "your-app";
    private final long jwtExpirationMs = 86400000; // 1 dan
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // Koristi HS512 za jači ključ

    public String generateToken(String email, UUID korisnikId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", korisnikId.toString()) // Dodavanje korisničkog ID-a u token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(SignatureAlgorithm.HS512, key)  // Koristi HS512 za potpisivanje
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);  // Validacija tokena
            return true;
        } catch (JwtException ex) {
            return false;  // Ako dođe do greške, token je nevažeći
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);  // Parsiranje JWT
    }
}
