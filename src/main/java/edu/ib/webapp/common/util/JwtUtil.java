package edu.ib.webapp.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Klasa służąca do zarządzania JSON WEB TOKEN
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = generateSecretKey();

    private static final int TOKEN_VALIDITY = 3600 * 5;

    /**
     * metoda służąca do znajdowania nazwy użytkownika z tokena
     * @param token - JWT
     * @return nazwę uzytkownika (String)
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * metoda do znajdowania określonej części tokena
     * @param token - JWT
     * @param claimsResolver - funkcja do zapisów "claimów"
     * @return zwraca zapisane "claimy"
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Metoda do szukania wszystkich części tokena
     * @param token - JWT
     * @return zwraca ciało tokena
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Sprawdza czy nazwa użytkownika aktualnego użytkownika jest zgodna z tą z tokena i czy token nie jest przeterminowany
     * @param token - JWT (String)
     * @param userDetails - dane aktualnego użytkownika dostępne dzięki Springowi
     * @return prawda lub fałsz (Boolean)
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Metoda do sprawdzania przeterminowania tokenu
     * @param token - JWT (String)
     * @return prawda lub fałsz (Boolean)
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Metoda do uzyskiwania daty przeterminowania tokena.
     * @param token - JWT (String)
     * @return data przeterminowania (Date)
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Metoda do generowania JWT.
     * @param userDetails - dane użytkownika
     * @return Zaszyfrowany JSON WEB TOKEN (String)
     */
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * Metoda do generowania sekretnego klucza potrzebnego do szyfrowania
     * @return Sekretny klucz (String)
     */
    public static String generateSecretKey() {
        return RandomStringUtils.randomAlphabetic(16);
    }
}
