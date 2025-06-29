package com.dawes.pdaw_Maria_Rosete.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase que representa un usuario del sistema.
 * 
 * @author Maria Rosete
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="usuarios")
public class UsuarioVO implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador unico del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true)
    private int idusuario;

    /**
     * Nombre del usuario.
     */
    @NonNull
    private String nombre;

    /**
     * Apellidos del usuario.
     */
    @NonNull
    private String apellidos;

    /**
     * Correo electronico del usuario.
     */
    @NonNull
    @Column(unique=true)
    private String correo;

    /**
     * Contrasena del usuario.
     */
    @NonNull
    private String password;

    /**
     * Rol del usuario.
     */
    @NonNull 
    @ManyToOne
    @JoinColumn(name="idrol")
    private RolVO rol;

    /**
     * Metodo que devuelve los roles del usuario como autoridades.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(rol.getNombre().name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    /**
     * Metodo que devuelve la contrasena del usuario.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Metodo que devuelve el correo electronico del usuario como nombre de usuario.
     */
    @Override
    public String getUsername() {
        return correo;
    }

    /**
     * Metodo que indica si la cuenta del usuario ha expirado (siempre devuelve true).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Metodo que indica si la cuenta del usuario está bloqueada (siempre devuelve true).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Metodo que indica si las credenciales del usuario han expirado (siempre devuelve true).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Metodo que indica si el usuario esta habilitado (siempre devuelve true).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }       
}

