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
        const listaInscripciones = document.querySelector('#listaInscripciones');

        if (inscripciones.length === 0) {
            const mensajeContainer = document.createElement('div');
            mensajeContainer.classList.add('mensaje-container');

            const icono = document.createElement('i');
            icono.classList.add('fas', 'fa-exclamation-triangle', 'icono-no-inscrito');

            const noInscritoMessage = document.createElement('span');
            noInscritoMessage.textContent = 'Todavía no estás inscrito en ningún curso.';
            noInscritoMessage.classList.add('no-inscrito-message');

            mensajeContainer.append(icono);
            mensajeContainer.append(noInscritoMessage);
            listaInscripciones.append(mensajeContainer);
        
        } else {
            for (const inscripcion of inscripciones) {
                const listItem = document.createElement('li');
                listItem.classList.add('inscripcion');

                const titulo = document.createElement('div');
                titulo.textContent = inscripcion.curso.titulo;
                titulo.classList.add('curso-titulo');

                const categoria = document.createElement('div');
                categoria.innerHTML = `<i class="fas fa-tags"></i> Categoría: ${inscripcion.curso.categoria}`;
                categoria.classList.add('curso-detalle');
    
                const descripcion = document.createElement('div');
                descripcion.innerHTML = `<i class="fas fa-file-alt"></i> Descripción: ${inscripcion.curso.descripcion}`;
                descripcion.classList.add('curso-detalle');

                const enlaceCurso = document.createElement('a');
                enlaceCurso.innerHTML = "<i class='fas fa-eye'></i> Ver curso";
                enlaceCurso.href = `/usuarios/html/registrado/pagina_curso${inscripcion.curso.idcurso}.html`;
                enlaceCurso.classList.add('enlace-curso');

                listItem.append(titulo);
                listItem.append(categoria);
                listItem.append(descripcion);
                listItem.append(enlaceCurso);

                listaInscripciones.append(listItem);
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
