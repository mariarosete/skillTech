document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Obtener el ID del usuario del token JWT
    const idusuario = getIdUsuarioFromToken(token);

    // Realizar la solicitud GET para obtener las inscripciones del usuario actual
    fetch(`https://skilltechback-42717f57a83b.herokuapp.com/inscripciones/buscarInscripcionesUsuario/${idusuario}`, {
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
        const submenuInscripciones = document.querySelector('#submenuInscripciones');

        if (inscripciones.length === 0) {
            // Manejar el caso en que el usuario no tiene inscripciones
        } else {
            for (const inscripcion of inscripciones) {
                const listItem = document.createElement('li');
                const enlaceCurso = document.createElement('a');
                enlaceCurso.textContent = inscripcion.curso.titulo;
                enlaceCurso.href = `/usuarios/html/registrado/pagina_curso${inscripcion.curso.idcurso}.html`;
                listItem.append(enlaceCurso);
                submenuInscripciones.append(listItem);
            }
        }
    })
    .catch(error => {
        console.error('Error al obtener las inscripciones:', error);
    });
});

// Función para obtener el ID de usuario del token JWT
function getIdUsuarioFromToken(token) {
    const tokenParts = token.split('.');
    const payload = JSON.parse(atob(tokenParts[1]));
    return payload.userId;
}
