package com.dawes.pdaw_Maria_Rosete.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.UsuarioRepository;

/**
 * Implementacion del servicio UserDetailsService que se utiliza para cargar los detalles de un usuario durante la autenticacion.
 * 
 * @author Maria Rosete
 */
@Service
public class DetalleUsuarioImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     *
     * @param nombre El nombre de usuario del usuario cuyos detalles se deben cargar.
     * @return Los detalles del usuario cargados como un UserDetails.
     * @throws UsernameNotFoundException Si no se encuentra el usuario con el nombre especificado.
     */
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        UsuarioVO usuario = usuarioRepository.buscarPorNombreUsuario(nombre).orElse(null);
        if (usuario == null)
            throw new UsernameNotFoundException("No existe el usuario");
        return usuario;
    }

}
