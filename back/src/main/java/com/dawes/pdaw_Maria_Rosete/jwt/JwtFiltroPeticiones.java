package com.dawes.pdaw_Maria_Rosete.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dawes.pdaw_Maria_Rosete.servicios.DetalleUsuarioImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que intercepta todas las peticiones entrantes para validar y procesar el token JWT.
 * 
 * @author Maria Rosete
 */
public class JwtFiltroPeticiones extends OncePerRequestFilter {

    @Autowired
    private DetalleUsuarioImpl detalleUsuario;

    /**
     * Realiza el filtrado de las peticiones.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain El filtro de cadena.
     * @throws ServletException Si hay un error de servlet.
     * @throws IOException Si hay un error de E/S.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null && JwtProvider.validarTokenJWT(token)) {
                String nombreUsuario = JwtProvider.getNombreUsuario(token);
                if (nombreUsuario != null) {
                    UserDetails userDetails = detalleUsuario.loadUserByUsername(nombreUsuario);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Obtiene el token JWT de la cabecera de autorizacion de la solicitud.
     * @param request La solicitud HTTP.
     * @return El token JWT si esta presente en la cabecera de autorizacion, de lo contrario, devuelve null.
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}

