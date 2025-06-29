package com.dawes.pdaw_Maria_Rosete.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Clase que maneja el error de autenticacion JWT.
 * 
 * @author Maria Rosete
 */
@Component
public class JwtAutenticacionError implements AuthenticationEntryPoint {

    /**
     * Maneja el error de autenticacion.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param authException La excepcion de autenticacion.
     * @throws IOException Si hay un error de E/S.
     * @throws ServletException Si hay un error de servlet.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "USUARIO NO AUTORIZADO");
    }

}
