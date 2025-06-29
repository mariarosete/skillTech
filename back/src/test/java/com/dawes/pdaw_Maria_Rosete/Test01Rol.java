package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioRolImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test01Rol {

    @Autowired
    private ServicioRolImpl srol;

    @Test
    public void test01GuardarRol() {
        RolVO rol1 = new RolVO(NombreRol.ADMINISTRADOR);
        RolVO rolGuardado1 = srol.guardarRol(rol1);
        assertEquals(rol1.getNombre(), rolGuardado1.getNombre());

        RolVO rol2 = new RolVO(NombreRol.REGISTRADO);
        RolVO rolGuardado2 = srol.guardarRol(rol2);
        assertEquals(rol2.getNombre(), rolGuardado2.getNombre());
    }
/************************************************************************************************/    
   /*
    @Test
    public void test02EliminarRol() {
        Integer idRolAEliminar = 2; 
        srol.eliminarRol(idRolAEliminar);
        assertNull(srol.obtenerRolPorId(idRolAEliminar));
    }
   */ 
/************************************************************************************************/
    @Test
    public void test03ObtenerRolPorId() {
        Integer idRolExistente = 1; 
        
        RolVO rolObtenido = srol.obtenerRolPorId(idRolExistente);
        assertEquals(idRolExistente, rolObtenido.getIdrol());
    }
/************************************************************************************************/
    @Test
    public void test04ObtenerTodosLosRoles() {
        Iterable<RolVO> roles = srol.obtenerTodosLosRoles();
        assertNotNull(roles);
       
    }
    
 /***********************************************************************************************/   
    @Test
    public void test05BuscarRolPorNombre() {
        NombreRol nombreBuscado = NombreRol.ADMINISTRADOR;
        java.util.Optional<RolVO> rolesEncontrados = srol.findByNombreRol(nombreBuscado);

        assertTrue(rolesEncontrados.isPresent());
    }
/************************************************************************************************/
 
}