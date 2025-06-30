
    document.addEventListener("DOMContentLoaded", function() {
        // Función para buscar cursos por nombre
        function buscarCursosPorTitulo() {
            // Obtener el valor del nombre ingresado por el usuario
            var titulo = document.querySelector('#inputTitulo').value;

            // Realizar la solicitud GET al backend para buscar cursos por nombre
            fetch('http://localhost:8080/cursos/buscarCursosPorTitulo/' + encodeURIComponent(titulo), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al buscar cursos por titulo');
                }
                return response.json();
            })
            .then(data => {
                // Limpiar el contenedor de resultados antes de mostrar los nuevos resultados
                limpiarResultados();

                // Verificar si se encontraron cursos
                if (data && data.length > 0) {
                    // Mostrar los cursos encontrados en la página
                    mostrarCursos(data);
                } else {
                    // Mostrar un mensaje indicando que no se encontraron cursos
                    mostrarMensaje('No se encontraron cursos con el titulo especificado.');
                }
            })
            .catch(error => {
                console.error('Error al buscar cursos por titulo:', error);
                mostrarMensaje('Error al buscar cursos por titulo. Por favor, inténtalo de nuevo más tarde.');
            });
        }

        // Función para limpiar el contenedor de resultados
        function limpiarResultados() {
            var contenedorResultados = document.querySelector('#contenedorResultados');
            contenedorResultados.innerHTML = '';
        }

        // Función para mostrar un mensaje en la página
        function mostrarMensaje(mensaje) {
            var contenedorResultados = document.querySelector('#contenedorResultados');
            var mensajeElemento = document.createElement('div');
            mensajeElemento.textContent = mensaje;
            contenedorResultados.append(mensajeElemento);
        }

        // Función para mostrar los cursos en la página
        function mostrarCursos(cursos) {
            var contenedorResultados = document.querySelector('#contenedorResultados');

            // Recorrer la lista de cursos
            for (const curso of cursos) {
                // Crear enlaces para todos los cursos
                var enlaceCurso = document.createElement('a');
                enlaceCurso.textContent = curso.titulo + ' - ' + curso.descripcion;
                enlaceCurso.className = 'enlace-curso';

                // Verificar si el curso es uno de los específicos con URL
                if (curso.titulo === 'Curso Html') {
                    enlaceCurso.href = '/usuarios/html/anonimo/cursoHtmlAnonimo.html';
                } else if (curso.titulo === 'Curso Java') {
                    enlaceCurso.href = '/usuarios/html/anonimo/cursoJavaAnonimo.html';
                } else if (curso.titulo === 'Curso JavaScript') {
                    enlaceCurso.href = '/usuarios/html/anonimo/cursoJavascriptAnonimo.html';
                } else if (curso.titulo === 'Curso Spring') {
                    enlaceCurso.href = '/usuarios/html/anonimo/cursoSpringAnonimo.html';
                } else {
                    enlaceCurso.href = '#'; // Enlace predeterminado para los cursos sin URL específica
                }

                contenedorResultados.append(enlaceCurso);
                contenedorResultados.append(document.createElement('br')); // Agregar un salto de línea entre cada enlace
            }
        }


        // Agregar listener para el formulario de búsqueda por nombre
        document.querySelector('#formBusquedaTitulo').addEventListener('submit', function(event) {
            event.preventDefault(); 
            buscarCursosPorTitulo();
        });
    });

