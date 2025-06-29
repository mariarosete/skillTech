package com.dawes.pdaw_Maria_Rosete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dawes.pdaw_Maria_Rosete.jwt.JwtAccesoDenegadoError;
import com.dawes.pdaw_Maria_Rosete.jwt.JwtAutenticacionError;
import com.dawes.pdaw_Maria_Rosete.jwt.JwtFiltroPeticiones;
import com.dawes.pdaw_Maria_Rosete.servicios.DetalleUsuarioImpl;

/**
 * Clase de configuracion para la seguridad de la aplicacion.
 * Configura Spring Security para la autenticacion y la autorizacion,
 * asi como la gestion de sesiones y el manejo de excepciones de seguridad.
 * 
 * @author Maria Rosete
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad {

    @Autowired
    private DetalleUsuarioImpl detalleUsuarioImpl;

    @Autowired
    private JwtAutenticacionError jwtAutenticacionError;
    
    @Autowired
    private JwtAccesoDenegadoError jwtAccesoDenegadoError;

    /**
     * Bean para crear el filtro de peticiones JWT.
     * @return El filtro de peticiones JWT.
     */
    @Bean
    JwtFiltroPeticiones jwtFiltroPeticiones() {
        return new JwtFiltroPeticiones();
    }

    /**
     * Bean para crear el codificador de contrasenas BCrypt.
     * @return El codificador de contrasenas BCrypt.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Bean para configurar el administrador de autenticacion.
     * @param authenticationConfiguration La configuración de autenticacion.
     * @return El administrador de autenticacion.
     * @throws Exception Si hay un error al obtener el administrador de autenticacion.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configuracion de la cadena de filtros de seguridad HTTP.
     * @param http La configuracion de seguridad HTTP.
     * @return La cadena de filtros de seguridad HTTP.
     * @throws Exception Si hay un error al configurar la seguridad HTTP.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests(authConfig -> {
            authConfig.requestMatchers("/usuarios/login").permitAll();
            authConfig.requestMatchers("/usuarios/insertar").permitAll();
            authConfig.requestMatchers("/cursos/buscarCursosPorTitulo/**").permitAll();
            authConfig.requestMatchers("/cursos/buscarCursosPorCategoria/**").permitAll();
            
            authConfig.anyRequest().authenticated();
        }).exceptionHandling().authenticationEntryPoint(jwtAutenticacionError).accessDeniedHandler(jwtAccesoDenegadoError).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.userDetailsService(detalleUsuarioImpl);
        http.addFilterBefore(jwtFiltroPeticiones(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
