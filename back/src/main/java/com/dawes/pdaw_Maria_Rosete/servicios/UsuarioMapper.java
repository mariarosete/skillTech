package com.dawes.pdaw_Maria_Rosete.servicios;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dawes.pdaw_Maria_Rosete.dto.UsuarioDTO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Interfaz que define metodos para mapear entre objetos DTO y entidades de Usuario.
 * 
 * @author Maria Rosete
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    /**
     * Convierte un objeto de tipo UsuarioVO a UsuarioDTO.
     * 
     * @param usuario El usuario a convertir.
     * @return El usuario convertido a DTO.
     */
    @Mapping(source = "rol.nombre", target = "rol")
    public UsuarioDTO toUsuarioDTO(UsuarioVO usuario);

    /**
     * Convierte un objeto de tipo UsuarioDTO a UsuarioVO.
     * 
     * @param usuarioDTO El DTO de usuario a convertir.
     * @return El DTO de usuario convertido a entidad.
     */
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "idusuario", ignore = true)
    public UsuarioVO toUsuario(UsuarioDTO usuarioDTO);

}
