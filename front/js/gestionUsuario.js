document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Realizar la solicitud GET para obtener los usuarios
    fetch('https://skilltechback-42717f57a83b.herokuapp.com/usuarios/buscarUsuarios', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al obtener los usuarios');
        }
        return response.json();
    })
    .then(usuarios => {
        // Procesar los datos recibidos y mostrar los usuarios en la página
        const tbodyUsuarios = document.getElementById('tbodyUsuarios');

        for (const usuario of usuarios) {
            const fila = document.createElement('tr');
        
            // Agregar el contenido HTML a la fila
            fila.innerHTML = `
                <td>${usuario.nombre}</td>
                <td>${usuario.apellidos}</td>
                <td>${usuario.correo}</td>
                <td>${usuario.rol.nombre}</td>
                <td>
                    <span class="icono-modificar">
                        <i class="fas fa-edit accion modificar" data-idusuario="${usuario.idusuario}"></i> 
                    </span>
                    <span class="icono-eliminar">
                        <i class="fas fa-trash-alt accion eliminar" data-idusuario="${usuario.idusuario}"></i>
                    </span>
                </td>
            `;
        
            tbodyUsuarios.append(fila);
        }
        

        // Agregar listener de eventos a los botones de modificar
        const botonesModificar = document.querySelectorAll('.modificar');
        for (const boton of botonesModificar) {
            boton.addEventListener('click', function() {
                const idUsuario = boton.getAttribute('data-idusuario');
                mostrarFormularioEdicion(idUsuario);
            });
        }

        // Agregar listener de eventos a los botones de eliminar
        const botonesEliminar = document.querySelectorAll('.eliminar');
        for (const boton of botonesEliminar) {
            boton.addEventListener('click', function() {
                const idUsuario = boton.getAttribute('data-idusuario');
                mostrarModalConfirmacion(idUsuario);
            });
        }
    })
    .catch(error => {
        console.error('Error al obtener los usuarios:', error);
    });

    /**************************************************** */

    function mostrarModalConfirmacion(idUsuario) {
        document.querySelector('#confirmModal').style.display = 'block';  // Mostrar el modal de confirmación

        // Manejar el clic en el botón "Sí, eliminar"
        document.querySelector('#confirmarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none'; // Ocultar el modal de confirmación
            eliminarUsuario(idUsuario);
        });

        // Manejar el clic en el botón "Cancelar"
        document.querySelector('#cancelarEliminar').addEventListener('click', function() {
            document.querySelector('#confirmModal').style.display = 'none';
        });
    }

    function eliminarUsuario(idUsuario) {
        // Primero, eliminar las inscripciones del usuario
        fetch(`https://skilltechback-42717f57a83b.herokuapp.com/inscripciones/eliminarInscripcionesUsuario/${idUsuario}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar las inscripciones del usuario');
            }
            // Después de eliminar las inscripciones, eliminar las solicitudes de soporte del usuario
            return fetch(`https://skilltechback-42717f57a83b.herokuapp.com/solicitaSoporte/eliminarSolicitudesUsuario/${idUsuario}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar las solicitudes de soporte del usuario');
            }
            // Después de eliminar las solicitudes de soporte, eliminar al usuario
            return fetch(`https://skilltechback-42717f57a83b.herokuapp.com/usuarios/eliminar/${idUsuario}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar el usuario');
            }
            // Si todo está bien, eliminar la fila de la tabla correspondiente al usuario eliminado
            const filaAEliminar = document.querySelector(`.eliminar[data-idusuario="${idUsuario}"]`).closest('tr');
            filaAEliminar.remove();
            console.log('Usuario eliminado exitosamente');
        })
        .catch(error => {
            console.error('Error al eliminar el usuario:', error);
        });
    }
    
    /************************Editar***************************************************************** */
    function mostrarFormularioEdicion(idUsuario) {
        document.querySelector('#editarModal').style.display = 'block'; // Mostrar el modal de edición

        // Realizar la solicitud GET para obtener los detalles del usuario
        fetch(`https://skilltechback-42717f57a83b.herokuapp.com/usuarios/buscarUsuarios/${idUsuario}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los detalles del usuario');
            }
            return response.json();
        })
        .then(usuario => {
            // Rellenar los campos del formulario con los datos del usuario obtenidos
            document.querySelector('#editarNombre').value = usuario.nombre || '';
            document.querySelector('#editarApellidos').value = usuario.apellidos || '';
            document.querySelector('#editarCorreo').value = usuario.correo || '';
            // No mostramos la contraseña, asumo que el campo tiene el id 'editarPassword'
            document.querySelector('#editarPassword').value = ''; 
            
            // Rellenar el campo de selección de rol con el rol actual del usuario
            if (usuario.rol && usuario.rol.nombre) {
                const rolNombre = usuario.rol.nombre.toUpperCase(); // Asegurarse de que el nombre esté en mayúsculas
                document.querySelector('#editarRol').value = rolNombre;
            } else {
                console.error('Rol no válido:', usuario.rol);
            }

            // Cargar la foto de perfil del usuario en el formulario de edición
            const profileImage = localStorage.getItem(`profileImage_${idUsuario}`); // Obtener la imagen de perfil del almacenamiento local asociada a este usuario
            const previewFotoPerfil = document.getElementById('previewFotoPerfil');
            if (profileImage) {
                previewFotoPerfil.src = profileImage;
            } else {
                // Si no hay una imagen de perfil guardada en el almacenamiento local, usar una imagen predeterminada
                previewFotoPerfil.src = '/imagenes/Usuario.png';
            }
        })
        .catch(error => {
            console.error('Error al obtener los detalles del usuario:', error);
        });

        // Agregar evento de submit al formulario para enviar los datos modificados
        document.querySelector('#formularioEdicion').addEventListener('submit', function(event) {
            event.preventDefault(); 

            //Capturar el texto seleccionado del campo de selección de rol
            const selectedRoleText = document.querySelector('#editarRol').value;
            let selectedRoleValue; // Declarar la variable selectedRoleValue

            // Asignar el valor correspondiente al rol seleccionado
            if (selectedRoleText === 'ADMINISTRADOR') {
                selectedRoleValue = 1; // Si el rol es 'ADMINISTRADOR', asignar el valor 1
            } else if (selectedRoleText === 'REGISTRADO') {
                selectedRoleValue = 2; // Si el rol es 'REGISTRADO', asignar el valor 2
            } else {
                console.error('Rol no válido:', selectedRoleText);
            }

            // Crear un objeto con los datos del formulario
            const formData = new FormData(document.querySelector('#formularioEdicion'));
            const data = {
                nombre: formData.get('nombre'),
                apellidos: formData.get('apellidos'),
                correo: formData.get('correo'),
                password: formData.get('password') || '', // Envía una cadena vacía si el campo de contraseña está vacío en el formulario
                rol: {
                    idrol: selectedRoleValue, // Enviar el valor correspondiente al rol seleccionado
                    nombre: selectedRoleText // Enviar el texto del rol seleccionado
                }
            };

            // Realizar la solicitud PUT para modificar los datos del usuario
            fetch(`https://skilltechback-42717f57a83b.herokuapp.com/usuarios/modificar/${idUsuario}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token') // Incluir el token JWT en el header de Authorization
                },
                body: JSON.stringify(data) // Convertir el objeto a JSON
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al modificar el usuario');
                }
                // Si se modifican correctamente, mostrar el modal de éxito
                document.getElementById('mensajeExitoModal').style.display = 'block';
                // Ocultar el modal después de 3 segundos
                setTimeout(function() {
                    document.getElementById('mensajeExitoModal').style.display = 'none';
                }, 3000); 
            })
            
            .then(data => {
                console.log('Usuario modificado correctamente:', data);
            })
            .catch(error => { 
                console.error('Error al modificar el usuario:', error);    
            });
        });
    }
});
