
    document.addEventListener("DOMContentLoaded", function() {
        // Función para buscar cursos por categoría
        function buscarCursosPorCategoria() {
            // Obtener el valor de la categoría seleccionada por el usuario
            var categoria = document.querySelector('#selectCategoria').value;

            // Realizar la solicitud GET al backend para buscar cursos por categoría
            fetch('http://localhost:8080/cursos/buscarCursosPorCategoria/' + encodeURIComponent(categoria), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al buscar cursos por categoría');
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
                    mostrarMensaje('No se encontraron cursos en la categoría seleccionada.');
                }
            })
            .catch(error => {
                console.error('Error al buscar cursos por categoría:', error);
                mostrarMensaje('Error al buscar cursos por categoría. Por favor, inténtalo de nuevo más tarde.');
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

            // Recorrer la lista de cursos y crear un elemento para cada uno
            for (const curso of cursos) {
                var cursoElemento = document.createElement('div');
                cursoElemento.textContent = curso.titulo + ' - ' + curso.descripcion;
                contenedorResultados.append(cursoElemento);
            }
        }

        // Agregar listener para el formulario de búsqueda por categoría
        document.querySelector('#formBusquedaCategoria').addEventListener('submit', function(event) {
            event.preventDefault(); 
            buscarCursosPorCategoria();
        });


    });
