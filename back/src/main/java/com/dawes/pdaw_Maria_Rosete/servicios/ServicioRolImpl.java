package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.RolRepository;
/**
 * Implementacion del servicio para la gestion de roles.
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioRolImpl implements ServicioRol {
	
	@Autowired
    private RolRepository rolRepository;

	/**
	 * Guarda un rol en la base de datos.
	 * 
	 * @param rol El rol a guardar.
	 * @return El rol guardado.
	 */
	@Override
    public RolVO guardarRol(RolVO rol) {
        return rolRepository.save(rol);
    }

    /**
     * Actualiza un rol en la base de datos.
     * 
     * @param id  El ID del rol a actualizar.
     * @param rol El nuevo rol con los datos actualizados.
     * @return El rol actualizado.
     */
    @Override
    public RolVO actualizarRol(Integer id, RolVO rol) {
        RolVO rolExistente = obtenerRolPorId(id);
        if (rolExistente != null) {
            rol.setIdrol(id);
            return rolRepository.save(rol);
        }
        return null;
    }

    /**
     * Elimina un rol de la base de datos por su ID.
     * 
     * @param id El ID del rol a eliminar.
     */
    @Override
    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }

    /**
     * Obtiene un rol por su ID.
     * 
     * @param id El ID del rol a obtener.
     * @return El rol encontrado.
     */
    @Override
    public RolVO obtenerRolPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene todos los roles almacenados en la base de datos.
     * 
     * @return Una coleccion iterable de todos los roles.
     */
    @Override
    public Iterable<RolVO> obtenerTodosLosRoles() {
    	return rolRepository.findAll();
    }

    /**
     * Guarda todos los roles dados en la base de datos.
     * 
     * @param roles La lista de roles a guardar.
     * @return Una coleccion iterable de los roles guardados.
     */
    @Override
    public Iterable<RolVO> saveAll(List<RolVO> roles) {
        return rolRepository.saveAll(roles);
    }

	/**
	 * Busca un rol por su nombre.
	 * 
	 * @param nombre El nombre del rol a buscar.
	 * @return El rol encontrado, o vacio si no se encuentra.
	 */
	@Override
	public Optional<RolVO> findByNombreRol(NombreRol nombre) {
		return rolRepository.findByNombre(nombre);
	}
}
