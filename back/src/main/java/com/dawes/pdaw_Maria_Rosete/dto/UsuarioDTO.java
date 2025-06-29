package com.dawes.pdaw_Maria_Rosete.dto;


import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

/**
 * Clase que representa un DTO (Data Transfer Object) para un usuario.
 * Representa la información que se transfiere entre el cliente y el servidor.
 * 
 * @author Maria Rosete
 */
public class UsuarioDTO {

	 private int id;
	    
	    /**
	     * El nombre del usuario.
	     */
	    @NotNull
	    @NotBlank
	    private String nombre;
	    
	    /**
	     * Los apellidos del usuario.
	     */
	    @NonNull
	    private String apellidos;

	    /**
	     * El correo electrónico del usuario.
	     */
	    @NonNull
	    @Column(unique=true)
	    private String correo;
	        
	    /**
	     * La contraseña del usuario.
	     */
	    @NotNull
	    @NotBlank
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private String password;
	    
	    /**
	     * El rol del usuario.
	     */
	    @NotNull
	    private NombreRol rol;
	    
	    /**
	     * El token de autenticación del usuario.
	     */
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
	    private String token;
	    
		public String getNombre() {
			return nombre;
		}
	
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	
		public String getPassword() {
			return password;
		}
	
		public void setClave(String password) {
			this.password = password;
		}
	
		public NombreRol getRol() {
			return rol;
		}
	
		public void setRol(NombreRol rol) {
			this.rol = rol;
		}
	
		public Integer getId() {
			return id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}
	
		public String getToken() {
			return token;
		}
	
		public void setToken(String token) {
			this.token = token;
		}
		
		public String getApellidos() {
			return apellidos;
		}
	
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}
	
		public String getCorreo() {
			return correo;
		}
	
		public void setCorreo(String correo) {
			this.correo = correo;
		}
	
		public void setPassword(String password) {
			this.password = password;
		}
	
	
	
	
}
