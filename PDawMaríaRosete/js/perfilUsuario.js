document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Definir la variable idUsuario antes de llamar a la función getIdUsuarioFromToken
    let idUsuario;


   /*Divide el token JWT en sus partes, decodifica la parte central y devuelve el ID de usuario 
   extraído del payload(parte central de un token JWT que contiene la información codificada, como datos de usuario.)*/
    // Función para obtener el ID de usuario del token JWT
    function getIdUsuarioFromToken(token) {
        const tokenParts = token.split('.');
        const payload = JSON.parse(atob(tokenParts[1]));
        return payload.userId;
    }

    // Obtener el ID del usuario del token JWT
    idUsuario = getIdUsuarioFromToken(token);

    // Realizar la solicitud GET para obtener los detalles del usuario autenticado
    fetch(`http://localhost:8080/usuarios/buscarUsuarios/${idUsuario}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al obtener los detalles del usuario');
        }
        return response.json();
    })
    .then(usuario => {
        // Rellenar los campos del formulario con los datos del usuario
        document.querySelector('#editarNombre').value = usuario.nombre || '';
        document.querySelector('#editarApellidos').value = usuario.apellidos || '';
        document.querySelector('#editarCorreo').value = usuario.correo || '';
    })
    .catch(error => {
        console.error('Error al obtener los detalles del usuario:', error);
    });

    // Manejar el evento de envío del formulario
    document.querySelector('#formularioEdicion').addEventListener('submit', function(event) {
        event.preventDefault(); 

        // Crear un objeto con los datos del formulario
        const formData = new FormData(document.querySelector('#formularioEdicion'));
        const data = {
            nombre: formData.get('nombre'),
            apellidos: formData.get('apellidos'),
            correo: formData.get('correo'),
            // Obtener la contraseña ingresada por el usuario
            password: formData.get('password')
        };

        // Realizar la solicitud PUT para modificar los datos del usuario
        fetch(`http://localhost:8080/usuarios/modificar/${idUsuario}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al modificar los detalles del usuario');
            }
            // Si se modifican correctamente, mostrar el modal de éxito
            document.getElementById('mensajeExitoModal').style.display = 'block';
        
            // Ocultar el modal después de 3 segundos
            setTimeout(function() {
                document.getElementById('mensajeExitoModal').style.display = 'none';
            }, 3000); 
        })
        .catch(error => {
            console.error('Error al modificar los detalles del usuario:', error);
        });
        
        
    });
});
