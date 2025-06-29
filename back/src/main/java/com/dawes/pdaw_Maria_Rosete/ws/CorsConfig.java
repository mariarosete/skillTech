package com.dawes.pdaw_Maria_Rosete.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuracion para permitir el acceso CORS a la aplicacion.
 * 
 * @author Maria Rosete
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
 
    /**
     * Metodo para configurar las opciones CORS.
     * 
     * @param registry El registro de configuracion de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // Permitir todas las IPs
                .allowedMethods("GET", "POST", "PUT", "DELETE")  
                .allowCredentials(true)
                .maxAge(3600);
    }
}
