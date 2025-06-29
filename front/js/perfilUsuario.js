document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');

    function getIdUsuarioFromToken(token) {
        const tokenParts = token.split('.');
        const payload = JSON.parse(atob(tokenParts[1]));
        return payload.userId;
    }

    const idUsuario = getIdUsuarioFromToken(token);

    fetch(`https://skilltechback-42717f57a83b.herokuapp.com/usuarios/buscarUsuarios/${idUsuario}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => response.json())
    .then(usuario => {
        document.querySelector('#editarNombre').value = usuario.nombre || '';
        document.querySelector('#editarApellidos').value = usuario.apellidos || '';
        document.querySelector('#editarCorreo').value = usuario.correo || '';
    })
    .catch(error => console.error('Error al obtener los detalles del usuario:', error));

    document.querySelector('#formularioEdicion').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(document.querySelector('#formularioEdicion'));
        const data = {
            nombre: formData.get('nombre'),
            apellidos: formData.get('apellidos'),
            correo: formData.get('correo'),
            password: formData.get('password') || '',
            rol: {
                idrol: 2, // ID del rol "REGISTRADO"
                nombre: 'REGISTRADO'
            }
        };

        fetch(`https://skilltechback-42717f57a83b.herokuapp.com/usuarios/modificar/${idUsuario}`, {
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
            document.getElementById('mensajeExitoModal').style.display = 'block';
            setTimeout(() => {
                document.getElementById('mensajeExitoModal').style.display = 'none';
            }, 3000);
        })
        .catch(error => console.error('Error al modificar los detalles del usuario:', error));
    });
});