package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioRolImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioUsuarioImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test02Usuario {

    @Autowired
    private ServicioUsuarioImpl susuario;

    @Autowired
    private ServicioRolImpl srol;
          
    @Test
    public void test01Crear() {
    	
    	/**********insertar roles********************************/
    	RolVO rol1 = new RolVO(NombreRol.ADMINISTRADOR);
    	RolVO rol2 = new RolVO(NombreRol.REGISTRADO);
    	srol.saveAll(Arrays.asList(rol1, rol2));
 	
    	RolVO rolGuardado1 = srol.obtenerRolPorId(1);
    	RolVO rolGuardado2 = srol.obtenerRolPorId(2);
    	
    	UsuarioVO usuario1 = new UsuarioVO("Laura", "Álvarez Pérez", "laura_alvarez_perez@hotmail.com", "alvarezperez", rolGuardado1);
    	UsuarioVO usuario2 = new UsuarioVO("Ángel", "Suárez Vega", "angel_suarez_vega@hotmail.com", "suarezvega", rolGuardado2);
    	
    	UsuarioVO usuarioGuardado1 = susuario.save(usuario1);
    	UsuarioVO usuarioGuardado2 = susuario.save(usuario2);
    	
        assertEquals(usuario1, usuarioGuardado1);
        assertEquals(usuario2, usuarioGuardado2);   
    }
/*****************************************************************************************************************************************/
    @Test
    public void test02Modificar() {
        UsuarioVO usuario = susuario.findById(1).get();
        usuario.setNombre("LauraModificado");
        
        assertEquals("LauraModificado", susuario.save(usuario).getNombre());
    }
/*****************************************************************************************************************************************/

    @Test
    public void test03Eliminar() {
    	UsuarioVO usuario = new UsuarioVO("Carlos", "Pérez", "carlos_perez@example.com", "carlosperez", srol.findByNombreRol(NombreRol.REGISTRADO).get());
        UsuarioVO usuarioGuardado = susuario.save(usuario);
        susuario.deleteById(usuarioGuardado.getIdusuario());

        Optional<UsuarioVO> usuarioBorrado = susuario.findById(usuarioGuardado.getIdusuario());
        assertTrue(usuarioBorrado.isEmpty());
    }
    
/*****************************************************************************************************************************************/
    @Test
    public void test04FindById() {
        UsuarioVO usuario = new UsuarioVO("Ana", "López", "ana_lopez@example.com", "analopez", srol.findByNombreRol(NombreRol.REGISTRADO).get());
        UsuarioVO usuarioGuardado = susuario.save(usuario);
        UsuarioVO usuarioEncontrado = susuario.findById(usuarioGuardado.getIdusuario()).get();
        
        assertNotNull(usuarioEncontrado);
        assertEquals(usuarioGuardado.getIdusuario(), usuarioEncontrado.getIdusuario());
    }
    
/************************************************************************************************************************************************/    

    @Test
    public void test05FindByCorreo() {
        UsuarioVO usuario = new UsuarioVO("Sara", "Martínez", "sara_martinez@example.com", "saramartinez", srol.findByNombreRol(NombreRol.REGISTRADO).get());
        UsuarioVO usuarioGuardado = susuario.save(usuario);
        UsuarioVO usuarioEncontrado = susuario.findByCorreo("sara_martinez@example.com");
        
        assertNotNull(usuarioEncontrado);
        assertEquals("Sara", usuarioEncontrado.getNombre());
    }

 /**************************************************************************************************************************************************/
    @Test
    public void test06FindAll() {
        UsuarioVO usuario1 = new UsuarioVO("David", "Gómez", "david_gomez@example.com", "davidgomez", srol.findByNombreRol(NombreRol.REGISTRADO).get());
        UsuarioVO usuario2 = new UsuarioVO("Elena", "Ruiz", "elena_ruiz@example.com", "elenaruiz", srol.findByNombreRol(NombreRol.ADMINISTRADOR).get());
        susuario.save(usuario1);
        susuario.save(usuario2);
        Iterable<UsuarioVO> usuarios = susuario.findAll();
        
        assertEquals(6, ((List<UsuarioVO>) usuarios).size());
    }
    
    
/**************************************************************************************************************************************************/
    @Test
    public void test07FindByRol() {
        RolVO rol = srol.findByNombreRol(NombreRol.ADMINISTRADOR).get();
        List<UsuarioVO> usuarios = susuario.findByRol(rol);
        
        assertEquals("LauraModificado", usuarios.get(0).getNombre());
    }
   
/***************************************************************************************************************************************************/    

    @Test
    public void test08BuscarPorNombreUsuario() {
        
        Optional<UsuarioVO> usuarioEncontrado = susuario.buscarPorNombreUsuario("LauraModificado");
        
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("LauraModificado", usuarioEncontrado.get().getNombre());
    }
/****************************************************************************************************************************************************/    
    
}

