package com.dawes.pdaw_Maria_Rosete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCursoImpl;

@SpringBootTest
@ComponentScan("com.dawes.pdaw_Maria_Rosete")
@TestMethodOrder(MethodOrderer.MethodName.class)
class Test03Curso {

    @Autowired
    private ServicioCursoImpl scurso;

    @Test
    void test01Insertar() {
        CursoVO curso1 = new CursoVO("Este curso está diseñado para sumergirte en el emocionante mundo de la programación web y proporcionarte las habilidades esenciales para construir aplicaciones interactivas y dinámicas.", "Programación web", "Desarrollo Web con JavaScript");
        CursoVO cursoGuardado1 = scurso.save(curso1);
        
        assertEquals(curso1, cursoGuardado1);
    }
/*************************************************************************************************************************************************************************************************************************************************************************************************/
    @Test
    void test02FindByTitulo() {
        assertEquals("Programación web", scurso.findById(1).get().getCategoria());
    }
    
/***********************************************************************************************************/
    @Test
    void test03Modificar() {
        CursoVO curso = scurso.findById(1).get();
        curso.setCategoria("Programación web en entorno cliente");
        
        assertEquals("Programación web en entorno cliente", scurso.save(curso).getCategoria());
    }
    
/***********************************************************************************************************/
    /*
    @Test
    void test04Eliminar() {
        scurso.deleteByTitulo("Desarrollo Web con JavaScript");
        assertTrue(scurso.findByTitulo("Desarrollo Web con JavaScript").isEmpty());
    }
    */
    
/***********************************************************************************************************/
    @Test
    public void test05FindAll() {
        assertEquals(1, scurso.findAll().size());
    }
    
/************************************************************************************************************/    
    @Test
    void test06BuscarCursosPorCategoria() {
        CursoVO curso = new CursoVO("Descripcion de prueba", "Categoria de prueba", "título de prueba");
        scurso.save(curso);

        Iterable<CursoVO> cursosEncontrados = scurso.findByCategoria("Categoria de prueba");
        Optional<CursoVO> cursoEncontrado = ((List<CursoVO>) cursosEncontrados).stream().findFirst();

        assertTrue(cursoEncontrado.isPresent());
        assertEquals("título de prueba", cursoEncontrado.get().getTitulo());
    }
/************************************************************************************************************/

}

