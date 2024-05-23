document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Realizar la solicitud GET para obtener los cursos
    fetch('https://skilltechback-42717f57a83b.herokuapp.com/cursos/buscarCursos', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al obtener los cursos');
        }
        return response.json();
    })
    .then(cursos => {
        
        // Procesar los datos recibidos y mostrar los cursos en la página
        const tbodyCursos = document.querySelector('#tbodyCursos');

        for (const curso of cursos) {
            const fila = document.createElement('tr');
        
            // Agregar el contenido HTML a la fila
            fila.innerHTML = `
                <td>${curso.titulo}</td>
                <td>${curso.descripcion}</td>
                <td>${curso.categoria}</td>
                <td>
                    <span class="icono-modificar">
                        <i class="fas fa-pencil-alt accion modificar" data-idcurso="${curso.idcurso}"></i> 
                    </span>
                    <span class="icono-eliminar">
                        <i class="fas fa-trash-alt accion eliminar" data-idcurso="${curso.idcurso}"></i>
                    </span>
                </td>
            `;
        
            tbodyCursos.append(fila);
        }
        

        // Agregar listener de eventos a los botones de modificar
        const botonesModificar = document.querySelectorAll('.modificar');
        for (const boton of botonesModificar) {
            boton.addEventListener('click', function() {
                const idCurso = boton.getAttribute('data-idcurso');
                mostrarFormularioEdicion(idCurso);
            });
        }

        // Agregar listener de eventos a los botones de eliminar
        const botonesEliminar = document.querySelectorAll('.eliminar');
        for (const boton of botonesEliminar) {
            boton.addEventListener('click', function() {
                const idCurso = boton.getAttribute('data-idcurso');
                mostrarModalConfirmacion(idCurso);
            });
        }
    })
    .catch(error => {
        console.error('Error al obtener los cursos:', error);
    });

    /********************************************************************* */
    function mostrarModalConfirmacion(idCurso) {
        document.querySelector('#confirmModal').style.display = 'block';  // Mostrar el modal de confirmación

        // Manejar el clic en el botón "Sí, eliminar"
        document.querySelector('#confirmarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none'; // Ocultar el modal de confirmación
            eliminarCurso(idCurso);
        });

        // Manejar el clic en el botón "Cancelar"
        document.querySelector('#cancelarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none';
        });
    }


    // Función para eliminar un curso
    function eliminarCurso(idCurso) {
        fetch(`https://skilltechback-42717f57a83b.herokuapp.com/cursos/eliminar/${idCurso}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar el curso');
            }
            return response.json();
        })
        .then(data => {
            // Eliminar la fila de la tabla correspondiente al curso eliminado
            const filaAEliminar = document.querySelector(`.eliminar[data-idcurso="${idCurso}"]`).closest('tr');
            filaAEliminar.remove();
            console.log(data.message);
            
        })
        .catch(error => {
            console.error('Error al eliminar el curso:', error);
        });

    }

    /*******Editar******************************************* */

    function mostrarFormularioEdicion(idCurso) {
        document.querySelector('#editarModal').style.display = 'block'; // Mostrar el modal de edición

        // Realizar la solicitud GET para obtener los detalles del curso
        fetch(`https://skilltechback-42717f57a83b.herokuapp.com/cursos/buscarCursos/${idCurso}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los detalles del curso');
            }
            return response.json();
        })
        .then(curso => {
            // Rellenar los campos del formulario con los datos del curso obtenidos
            document.querySelector('#editarTitulo').value = curso.titulo || '';
            document.querySelector('#editarDescripcion').value = curso.descripcion || '';
            document.querySelector('#editarCategoria').value = curso.categoria || '';
            
        })
        .catch(error => {
            console.error('Error al obtener los detalles del curso:', error);
        });

        // Agregar evento de submit al formulario para enviar los datos modificados
        document.querySelector('#formularioEdicion').addEventListener('submit', function(event) {
            event.preventDefault(); 
        
            // Crear un objeto con los datos del formulario
            const formData = new FormData(document.querySelector('#formularioEdicion'));
            const data = {
                titulo: formData.get('titulo'),
                descripcion: formData.get('descripcion'),
                categoria: formData.get('categoria')
            };

            // Realizar la solicitud PUT para modificar los datos del curso
            fetch(`https://skilltechback-42717f57a83b.herokuapp.com/cursos/modificar/${idCurso}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
                },
                body: JSON.stringify(data) // Convertir el objeto a JSON
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al modificar el curso');
                }
                // Si se modifican correctamente, mostrar el modal de éxito
                document.getElementById('mensajeExitoModal').style.display = 'block';
                // Ocultar el modal después de 3 segundos
                setTimeout(function() {
                    document.getElementById('mensajeExitoModal').style.display = 'none';
                }, 3000); 
            })
            .then(data => {
                console.log('Curso modificado correctamente:', data);
                
            })
            .catch(error => {
                console.error('Error al modificar el curso:', error);
            });
        });
    }
});