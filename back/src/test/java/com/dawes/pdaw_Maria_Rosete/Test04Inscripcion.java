package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCursoImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioInscripcionImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioRolImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioUsuarioImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test04Inscripcion {

    @Autowired
    private ServicioInscripcionImpl sinscripcion;

    @Autowired
    private ServicioUsuarioImpl susuario;

    @Autowired
    private ServicioCursoImpl scurso;

    @Autowired
    private ServicioRolImpl srol;

    @Test
    void test01Insertar() {
    	
    	/**********insertar roles********************************/
    	RolVO rol1 = new RolVO(NombreRol.ADMINISTRADOR);
    	RolVO rol2 = new RolVO(NombreRol.REGISTRADO);
    	srol.saveAll(Arrays.asList(rol1, rol2));
       
    	RolVO rolGuardado1 = srol.obtenerRolPorId(1);
    	RolVO rolGuardado2 = srol.obtenerRolPorId(2);

    	/**********insertar usuarios********************************/
        UsuarioVO usu1 = new UsuarioVO("Laura", "Álvarez Pérez", "laura_alvarez_perez@hotmail.com", "alvarezperez", rolGuardado1);
        UsuarioVO usu2 = new UsuarioVO("Ángel", "Suárez Vega", "angel_suarez_vega@hotmail.com", "suarezvega", rolGuardado2);

        susuario.save((UsuarioVO) usu1);
        susuario.save((UsuarioVO) usu2);

        /**********insertar cursos********************************/
        CursoVO cursoPrueba = new CursoVO("Descripción de prueba", "Categoría de prueba", "Título de prueba");
        scurso.save(cursoPrueba);

        /**********insertar inscripcion********************************/
        InscripcionVO inscripcionLaura = new InscripcionVO((UsuarioVO) usu1, cursoPrueba);
        InscripcionVO inscripcionGuardada = sinscripcion.save(inscripcionLaura);

        assertEquals(inscripcionLaura, inscripcionGuardada);
    }
/*************************************************************************************************************************************************/
    @Test
    void test02FindByUsuarioAndCurso() {
        UsuarioVO usuario = susuario.findById(1).get();
        CursoVO curso = scurso.findById(1).get();
        InscripcionVO inscripcion = sinscripcion.findByUsuarioAndCurso(usuario, curso).get();
        
        assertEquals(usuario, inscripcion.getUsuario());
        assertEquals(curso, inscripcion.getCurso());
    }
/********************************************************************************************************/
   /* 
    @Test
    void test03Eliminar() {
    	
    	UsuarioVO usuario = susuario.findById(1).get();
        CursoVO curso = scurso.findById(1).get();
    	sinscripcion.deleteById(1);
        assertTrue(sinscripcion.findByUsuarioAndCurso(usuario, curso).isEmpty());
    }
    
    */
/********************************************************************************************************/   
}
