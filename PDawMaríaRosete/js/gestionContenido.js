document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Realizar la solicitud GET para obtener los materiales de cursos
    fetch('http://localhost:8080/materialCursos/buscarMaterialCursos', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al obtener los materiales de cursos');
        }
        return response.json();
    })
    .then(materiales => {
        // Procesar los datos recibidos y mostrar los materiales en la página
        const tbodyMateriales = document.querySelector('#tbodyMateriales'); 

        for (const material of materiales) {
            const fila = document.createElement('tr');

            // Agregar el contenido HTML a la fila
            fila.innerHTML = `
                <td>${material.nombre}</td>
                <td>${material.tipoMaterial}</td>
                <td>${material.url}</td>
                <td>${material.curso.titulo}</td>
                <td>
                    <i class="fas fa-pencil-alt accion modificar" data-idmaterialCurso="${material.idmaterialCurso}"></i> 
                    <i class="fas fa-trash-alt accion eliminar" data-idmaterialCurso="${material.idmaterialCurso}"></i>
                </td>
            `;

            tbodyMateriales.append(fila);
        }

        // Agregar listener de eventos a los botones de modificar
        const botonesModificar = document.querySelectorAll('.modificar');
        for (const boton of botonesModificar) {
            boton.addEventListener('click', function() {
                const idMaterial = boton.getAttribute('data-idmaterialCurso');
                mostrarFormularioEdicion(idMaterial);
            });
        }

        // Agregar listener de eventos a los botones de eliminar
        const botonesEliminar = document.querySelectorAll('.eliminar');
        for (const boton of botonesEliminar) {
            boton.addEventListener('click', function() {
                const idMaterial = boton.getAttribute('data-idmaterialCurso');
                mostrarModalConfirmacion(idMaterial);
            });
        }
    })
    .catch(error => {
        console.error('Error al obtener los materiales de cursos:', error);
    });

    /********************************************************************* */
    function mostrarModalConfirmacion(idmaterialCurso) {
        if (!idmaterialCurso) {
            console.error('El ID del material de curso es indefinido.');
            return;
        }

        document.querySelector('#confirmModal').style.display = 'block';  // Mostrar el modal de confirmación

        // Manejar el clic en el botón "Sí, eliminar"
        document.querySelector('#confirmarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none'; // Ocultar el modal de confirmación
            eliminarMaterialCurso(idmaterialCurso);
        });

        // Manejar el clic en el botón "Cancelar"
        document.querySelector('#cancelarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none';
        });
    }

    // Función para eliminar un material de curso
    function eliminarMaterialCurso(idmaterialCurso) {
        fetch(`http://localhost:8080/materialCursos/eliminar/${idmaterialCurso}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar el material de curso');
            }
            return response.json();
        })
        .then(data => {
            // Eliminar la fila de la tabla correspondiente al material de curso eliminado
            const filaAEliminar = document.querySelector(`.eliminar[data-idmaterialCurso="${idmaterialCurso}"]`).closest('tr');
            filaAEliminar.remove();
            console.log(data.message);
        })
        .catch(error => {
            console.error('Error al eliminar el material de curso:', error);
        });
    }

    /*******Editar******************************************* */

    function mostrarFormularioEdicion(idmaterialCurso) {
        if (!idmaterialCurso) {
            console.error('El ID del material de curso es indefinido.');
            return;
        }

        // Actualizar el valor del campo oculto con el ID del material del curso
        document.querySelector('#editarIdMaterialCurso').value = idmaterialCurso;

        document.querySelector('#editarModal').style.display = 'block'; // Mostrar el modal de edición

        // Realizar la solicitud GET para obtener los detalles del material de curso
        fetch(`http://localhost:8080/materialCursos/buscarMaterialCursos/${idmaterialCurso}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los detalles del material de curso');
            }
            return response.json();
        })
        .then(material => {
            // Rellenar los campos del formulario con los datos del material de curso obtenidos
            document.querySelector('#editarNombre').value = material.nombre || ''; 
            document.querySelector('#editarTipoMaterial').value = material.tipoMaterial || ''; 
            document.querySelector('#editarURL').value = material.url || ''; 
        })
        .catch(error => {
            console.error('Error al obtener los detalles del material de curso:', error);
        });

        // Agregar evento de submit al formulario para enviar los datos modificados
        document.querySelector('#formularioEdicionContenido').addEventListener('submit', function(event) { 
            event.preventDefault(); 
        
            // Crear un objeto con los datos del formulario
            const formData = new FormData(document.querySelector('#formularioEdicionContenido')); 
            const data = {
                nombre: formData.get('nombre'),
                tipoMaterial: formData.get('tipoMaterial'),
                url: formData.get('url')
            };

            // Realizar la solicitud PUT para modificar los datos del material de curso
            fetch(`http://localhost:8080/materialCursos/modificar/${idmaterialCurso}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
                },
                body: JSON.stringify(data) // Convertir el objeto a JSON
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al modificar el contenido del curso');
                }
                // Si se modifican correctamente, mostrar el modal de éxito
                document.getElementById('mensajeExitoModal').style.display = 'block';
                // Ocultar el modal después de 3 segundos
                setTimeout(function() {
                    document.getElementById('mensajeExitoModal').style.display = 'none';
                }, 3000); 
            })
            .then(data => {
                console.log('Contenido del curso modificado correctamente:', data);
                
            })
            .catch(error => {
                console.error('Error al modificar el contenido del curso:', error);
            });
        });
    }
});
