document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Realizar la solicitud GET para obtener las inscripciones
    fetch('http://localhost:8080/inscripciones/buscarInscripciones', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al obtener las inscripciones');
        }
        return response.json();
    })
    .then(inscripciones => {
        // Procesar los datos recibidos y mostrar las inscripciones en la página
        const tbodyInscripciones = document.querySelector('#tbodyInscripciones');

        for (const inscripcion of inscripciones) {
            const fila = document.createElement('tr');
        
            // Agregar el contenido HTML a la fila
            fila.innerHTML = `
                <td>${inscripcion.usuario.nombre} ${inscripcion.usuario.apellidos}</td>
                <td>${inscripcion.curso.titulo}</td>
                <td class="accion-columna">
                    <div class="centrar-vertical">
                        <i class="fas fa-trash-alt accion eliminar" data-idinscripcion="${inscripcion.idinscripcion}"></i>
                    </div>
                </td>
            `;
        
            tbodyInscripciones.append(fila);
        }
        // Agregar listener de eventos a los botones de eliminar
        const botonesEliminar = document.querySelectorAll('.eliminar');
        for (const boton of botonesEliminar) {
            boton.addEventListener('click', function() {
                const idInscripcion = boton.getAttribute('data-idinscripcion');
                mostrarModalConfirmacion(idInscripcion);
            });
        }
    })
    .catch(error => {
        console.error('Error al obtener las inscripciones:', error);
    });

    function mostrarModalConfirmacion(idInscripcion) {
        document.querySelector('#confirmModal').style.display = 'block';  // Mostrar el modal de confirmación

        // Manejar el clic en el botón "Sí, eliminar"
        document.querySelector('#confirmarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none'; // Ocultar el modal de confirmación
            eliminarInscripcion(idInscripcion);
        });

        // Manejar el clic en el botón "Cancelar"
        document.querySelector('#cancelarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none';
        });
    }

    // Función para eliminar una inscripción
    function eliminarInscripcion(idInscripcion) {
        fetch(`http://localhost:8080/inscripciones/eliminar/${idInscripcion}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar la inscripción');
            }
            return response.json();
        })
        .then(data => {
            // Eliminar la fila de la tabla correspondiente a la inscripción eliminada
            const filaAEliminar = document.querySelector(`.eliminar[data-idinscripcion="${idInscripcion}"]`).closest('tr');
            filaAEliminar.remove();
            console.log(data.message);

            // Mostrar el modal de éxito
            document.getElementById('mensajeExitoModal').style.display = 'block';
            // Ocultar el modal después de 3 segundos
            setTimeout(function() {
                document.getElementById('mensajeExitoModal').style.display = 'none';
            }, 3000); 
        })
        .catch(error => {
            console.error('Error al eliminar la inscripción:', error);
        });
    }
});
