package com.dawes.pdaw_Maria_Rosete.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Clase que proporciona metodos para generar, validar y extraer informacion de tokens JWT.
 * 
 * @author Maria Rosete
 */
@Component
public class JwtProvider {

    // Llave secreta para firmar y verificar los tokens JWT
    private static final Key LLAVE_SECRETA = new SecretKeySpec("F@cturacionSR12023F@cturacionSR12023F@cturacionSR12023".getBytes(), SignatureAlgorithm.HS512.getJcaName());

    // Tiempo de expiración del token JWT (1 hora)
    private static final long TIEMPO_EXPIRACION = 3600_000;

    /**
     * Genera un token JWT para el usuario con el nombre de usuario y ID proporcionados.
     * @param nombreUsuario El nombre de usuario.
     * @param idUsuario El ID del usuario.
     * @return El token JWT generado.
     */
    public static String generarTokenJWT(String nombreUsuario, Integer idUsuario) {
        String token = Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("userId", idUsuario) // Incluir el ID del usuario
                .setExpiration(Date.from(Instant.now().plusMillis(TIEMPO_EXPIRACION)))
                .signWith(LLAVE_SECRETA)
                .compact();
        
        // Imprimir el token JWT en la consola para verificar
        System.out.println("Token JWT generado: " + token);

        return token;
    }

    /**
     * Valida un token JWT.
     * @param token El token JWT a validar.
     * @return true si el token es valido, false de lo contrario.
     */
    public static boolean validarTokenJWT(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(LLAVE_SECRETA).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el ID del usuario desde un token JWT.
     * @param token El token JWT.
     * @return El ID del usuario.
     */
    public static Integer getIdUsuario(String token) {
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(LLAVE_SECRETA).build().parseClaimsJws(token);
        return jws.getBody().get("userId", Integer.class); // Obtener el ID del usuario del token
    }

    /**
     * Obtiene el nombre de usuario desde un token JWT.
     * @param token El token JWT.
     * @return El nombre de usuario.
     */
    public static String getNombreUsuario(String token) {
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(LLAVE_SECRETA).build().parseClaimsJws(token);
        return jws.getBody().getSubject();
    }
}

