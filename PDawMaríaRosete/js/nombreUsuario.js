document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Obtener el nombre de usuario del token JWT
    const nombreUsuario = getNombreUsuarioFromToken(token);

    // Actualizar el contenido del elemento HTML con el nombre de usuario
    const nombreUsuarioElement = document.getElementById('nombreUsuario');
    nombreUsuarioElement.textContent = nombreUsuario;
});

// Función para obtener el nombre de usuario del token JWT
function getNombreUsuarioFromToken(token) {
    const tokenParts = token.split('.');
    const payload = JSON.parse(atob(tokenParts[1]));
    return payload.sub;
}
