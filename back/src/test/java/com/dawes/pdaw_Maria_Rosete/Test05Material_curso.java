package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;

import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO.TipoMaterial;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCursoImpl;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioMaterialCursoImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test05Material_curso {

    @Autowired
    private ServicioMaterialCursoImpl smaterial;

    @Autowired
    private ServicioCursoImpl scurso;

    @Test
    void test01Insertar() {
    	
    	 /**********insertar curso********************************/
    	CursoVO curso1 = new CursoVO("Este curso está diseñado para sumergirte en el emocionante mundo de la programación web y proporcionarte las habilidades esenciales para construir aplicaciones interactivas y dinámicas.", "Programación web", "Desarrollo Web con JavaScript");
        CursoVO cursoGuardado1 = scurso.save(curso1);
        
        /**********insertar material del curso********************************/
        MaterialCursoVO material1 = new MaterialCursoVO("Pdf JavaScript", TipoMaterial.PDF, "http://url-material-1.com", cursoGuardado1);
        MaterialCursoVO materialGuardado1 = smaterial.save(material1);
        
        assertEquals(material1.getNombre(), materialGuardado1.getNombre());
        assertEquals(material1.getTipoMaterial(), materialGuardado1.getTipoMaterial());
        assertEquals(material1.getUrl(), materialGuardado1.getUrl());
        assertEquals(material1.getCurso().getIdcurso(), materialGuardado1.getCurso().getIdcurso());
    }
/***************************************************************************************************************************************************************************************************************************************************************************************/
    @Test
    void test02FindByNombre() {
        MaterialCursoVO material = smaterial.findByNombre("Pdf JavaScript").orElse(null);
        
        assertEquals("Pdf JavaScript", material.getNombre());
        assertEquals(TipoMaterial.PDF, material.getTipoMaterial());
        assertEquals("http://url-material-1.com", material.getUrl());
    }
    
/***********************************************************************************************************/    
    @Test
    void test03Modificar() {
    	
    	MaterialCursoVO material1 = smaterial.findById(1).get();
    	material1.setNombre("Pdf JavaScript Modificado");
        
        assertEquals("Pdf JavaScript Modificado", smaterial.save(material1).getNombre());
    }
    

 /*********************************************************************************************************/
    /*
    @Test
    void test04EliminarPorId() {
        Optional<MaterialCursoVO> materialOptional = smaterial.findByNombre("Pdf JavaScript");
        
        if (materialOptional.isPresent()) {
            MaterialCursoVO material = materialOptional.get();
            smaterial.deleteById(material.getIdmaterialCurso());
            assertFalse(smaterial.findByNombre("Pdf JavaScript").isPresent());
        } else {
            fail("Material no encontrado");
        }
    }
     */
/***********************************************************************************************************/
}

