package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.dto.UsuarioDTO;
import com.dawes.pdaw_Maria_Rosete.dto.UsuarioLoginDTO;
import com.dawes.pdaw_Maria_Rosete.jwt.JwtProvider;
import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.RolRepository;
import com.dawes.pdaw_Maria_Rosete.repositorio.UsuarioRepository;

/**
 * Implementacion del servicio para la gestion de usuarios
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuarioDTO Los datos del usuario a crear.
     * @return El usuario creado.
     * @throws Exception Si el rol especificado en el DTO no existe en la base de datos.
     */
    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) throws Exception {
        UsuarioVO usuario = usuarioMapper.toUsuario(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        RolVO rol = rolRepository.findByNombre(usuarioDTO.getRol())
                .orElseThrow(() -> new Exception("No existe el rol en la base de datos, inserte primero"));
        usuario.setRol(rol);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    /**
     * Autentica a un usuario en el sistema.
     * 
     * @param usuarioLoginDTO Los datos de inicio de sesion del usuario.
     * @return El DTO del usuario autenticado, incluyendo el token JWT.
     */
    @Override
    public UsuarioDTO login(UsuarioLoginDTO usuarioLoginDTO) {
        
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getNombreUsuario(), usuarioLoginDTO.getClave()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        UsuarioVO usuario = usuarioRepository.buscarPorNombreUsuario(usuarioLoginDTO.getNombreUsuario())
                .orElse(null);
        if (usuario == null) {
           
            return null;
        }

        // Obtener el nombre del rol del usuario
        NombreRol nombreRol = usuario.getRol().getNombre();

        // Generar el token JWT 
        String token = JwtProvider.generarTokenJWT(usuarioLoginDTO.getNombreUsuario(), usuario.getIdusuario());

        // Mapear el usuario a un DTO
        UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);

        // Asignar el nombre del rol y el token al DTO
        usuarioDTO.setRol(nombreRol);
        usuarioDTO.setToken(token);
        return usuarioDTO;
    }

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id El ID del usuario a eliminar.
     */
    @Override
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id El ID del usuario a buscar.
     * @return El usuario encontrado, si existe.
     */
    @Override
    public Optional<UsuarioVO> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return Una lista de todos los usuarios.
     */
    @Override
    public Iterable<UsuarioVO> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su correo electronico.
     * 
     * @param correo El correo electronico del usuario a buscar.
     * @return El usuario encontrado, si existe.
     */
    @Override
    public UsuarioVO findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    /**
     * Busca usuarios por su rol.
     * 
     * @param rol El rol de los usuarios a buscar.
     * @return Una lista de usuarios que tienen el rol especificado.
     */
    @Override
    public List<UsuarioVO> findByRol(RolVO rol) {
        return usuarioRepository.findByRol(rol);
    }

    /**
     * Guarda los cambios realizados en un usuario.
     * 
     * @param updatedUsuario El usuario actualizado.
     * @return El usuario actualizado.
     */
    @Override
    public UsuarioVO save(UsuarioVO updatedUsuario) {
        return usuarioRepository.save(updatedUsuario);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param nombre El nombre de usuario a buscar.
     * @return El usuario encontrado, si existe.
     */
    @Override
    public Optional<UsuarioVO> buscarPorNombreUsuario(String nombre) {
        return usuarioRepository.buscarPorNombreUsuario(nombre);
    }

}
