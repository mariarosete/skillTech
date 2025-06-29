package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.SolicitaSoporteVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO.TipoMaterial;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCursoImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioInscripcionImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioMaterialCursoImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioRolImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioSolicitaSoporteImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioUsuarioImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test06Solicita_soporte {

    @Autowired
    private ServicioSolicitaSoporteImpl ssoporte;

    @Autowired
    private ServicioUsuarioImpl susuario;

    @Autowired
    private ServicioMaterialCursoImpl smaterial;
    
    @Autowired
    private ServicioRolImpl srol;
    
    @Autowired
    private ServicioInscripcionImpl sinscripcion;
    
    @Autowired
    private ServicioCursoImpl scurso;


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
        CursoVO curso1 = new CursoVO("Descripción de prueba", "Categoría de prueba", "Título de prueba");
        scurso.save(curso1);
        
        /**********insertar inscripcion***************************/
        InscripcionVO inscripcionLaura = new InscripcionVO((UsuarioVO) usu1, curso1);
        InscripcionVO inscripcionGuardada = sinscripcion.save(inscripcionLaura);

        /**********insertar material del curso*********************/
        MaterialCursoVO material1 = new MaterialCursoVO("Pdf JavaScript", TipoMaterial.PDF, "http://url-material-1.com", curso1);
        MaterialCursoVO materialGuardado1 = smaterial.save(material1);
        
        /**********insertar soporte*********************/
        SolicitaSoporteVO soporte1 = new SolicitaSoporteVO(susuario.findById(1).get(), smaterial.findByNombre("Pdf JavaScript").get(), "No me carga el video", "Lo hemos solucionado");
        SolicitaSoporteVO soporteGuardado1 = ssoporte.save(soporte1);
        
        assertEquals(soporte1, soporteGuardado1);
    }

/*******************************************************************************************************************************************************************************************/   
    @Test
    void test02FindByUsuarioAndMaterialCurso() {
        SolicitaSoporteVO soporte = ssoporte.findByUsuarioAndMaterialCurso(1, 1).orElse(null);
        
        assertEquals("No me carga el video", soporte.getDescripcion());
        
    }

 /**************************************************************************************************/   
   /* 
    @Test
    void test03EliminarPorId() {
        Optional<SolicitaSoporteVO> soporteOptional = ssoporte.findByUsuarioAndMaterialCurso(1, 1);
        assertTrue(soporteOptional.isPresent());
        
        SolicitaSoporteVO soporte = soporteOptional.get();
        ssoporte.deleteById(soporte.getIdsolicitaSoporte());
        assertFalse(ssoporte.findByUsuarioAndMaterialCurso(1, 1).isPresent());
    }
    */

/****************************************************************************************************/
}

