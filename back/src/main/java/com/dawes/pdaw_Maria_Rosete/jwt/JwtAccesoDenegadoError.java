package com.dawes.pdaw_Maria_Rosete.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Clase que maneja el error de acceso denegado en la autenticacion JWT.
 * 
 *  @author Maria Rosete
 */
@Component
public class JwtAccesoDenegadoError implements AccessDeniedHandler {

    /**
     * Maneja el error de acceso denegado.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param accessDeniedException La excepción de acceso denegado.
     * @throws IOException Si hay un error de E/S.
     * @throws ServletException Si hay un error de servlet.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "USUARIO NO AUTORIZADO");
    }

}
