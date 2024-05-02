document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    // Realizar la solicitud GET para obtener los cursos
    fetch('http://localhost:8080/materialCursos/buscarMaterialCursos', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token 
        }
    })
    .then(response => response.json())
    .then(data => {
        // Obtener el select del formulario
        var selectMaterialCurso = document.querySelector("#material_curso");
        selectMaterialCurso.innerHTML = "";

        // Iterar sobre los datos y crear opciones para cada material del curso
        for (const materialCurso of data) {
            var option = document.createElement("option");
            option.value = materialCurso.idmaterialCurso;
            option.text = materialCurso.nombre;
            selectMaterialCurso.appendChild(option);
        }
    })
    .catch(error => {
        console.error('Error al obtener los materiales del curso:', error);
    });

    // Escuchar el evento de envío del formulario de soporte
    document.querySelector("#registro").addEventListener('submit', function(event) {
        event.preventDefault();

        // Obtener la descripción del problema
        var descripcion = document.querySelector('#descripcion').value;
        var idMaterialCurso = document.querySelector('#material_curso').value; // Obtener el ID del material del curso seleccionado
        console.log('ID del material del curso seleccionado:', idMaterialCurso);

        // Verificar que la descripción no esté vacía
        if (descripcion === '') {
            console.error('Por favor, ingrese una descripción del problema.');
            return;
        }

        // Crear un objeto JSON con los datos del soporte
        var datosSoporte = {
            usuario: {
                idusuario: getIdUsuarioFromToken(token) // ID de usuario obtenido del token JWT
            },
            materialCurso: {
                idmaterialCurso: idMaterialCurso // ID del material del curso seleccionado
            },
            descripcion: descripcion,
            respuesta: "" // Dejamos la respuesta vacía
        };

        // Realizar la solicitud Fetch para enviar el formulario de soporte
        fetch('http://localhost:8080/solicitaSoporte/insertar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token 
            },
            body: JSON.stringify(datosSoporte)
        })
        .then(response => {
            if (response.ok) {
                
            } else {
                document.querySelector('#solicitudSoporteExitosaMessage').style.display = 'block';
                
                setTimeout(function() {
                    document.querySelector('#solicitudSoporteExitosaMessage').style.display = 'none';
                }, 3000);  
            }
        })
        .catch(error => {   
           
        }); 
        
    });

    // Al cargar la página, solicitar la respuesta del administrador solo para el usuario actual y mostrarla en la sección correspondiente
    fetch('http://localhost:8080/solicitaSoporte/buscarSoporte', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token 
        }
    })
    .then(response => response.json())
    .then(data => {
        // Filtrar las solicitudes de soporte para obtener solo las del usuario actual
        const solicitudesUsuarioActual = data.filter(solicitud => solicitud.usuario.idusuario === getIdUsuarioFromToken(token));

        // Verificar si hay solicitudes del usuario actual con respuesta del administrador
        const solicitudesConRespuesta = solicitudesUsuarioActual.filter(solicitud => solicitud.respuesta !== null && solicitud.respuesta !== undefined);

        if (solicitudesConRespuesta.length > 0) {
            // Si se encontraron solicitudes con respuesta del administrador, mostrar las respuestas en la sección correspondiente
            const respuestaAdmin = document.querySelector('#respuestaAdmin');
            respuestaAdmin.innerHTML = ''; // Limpiar el contenido previo

            for (const solicitud of solicitudesConRespuesta) {
                const respuestaElement = document.createElement('p');
                respuestaElement.innerHTML = `<strong><i class="fas fa-reply"></i> Solicitud Nº ${solicitud.idsolicitaSoporte}:</strong> ${solicitud.respuesta}`;
                respuestaAdmin.append(respuestaElement);
            }

            document.querySelector('#respuestaAdminSection').style.display = 'block';

        } else {
            // Si no se encontraron solicitudes con respuesta del administrador para el usuario actual, mostrar un mensaje indicando que no hay respuesta disponible

            const mensajeContainer = document.createElement('div');
            mensajeContainer.classList.add('mensaje-container');
        
            const icono = document.createElement('i');
            icono.classList.add('fas', 'fa-exclamation-triangle', 'icono-no-respuesta');
        
            const mensaje = document.createElement('span');
            mensaje.textContent = 'No hay respuesta todavía.';
            respuestaAdmin.style.textAlign = 'center';
            mensaje.classList.add('no-respuesta-message');
        
            mensajeContainer.append(icono);
            mensajeContainer.append(mensaje);
            respuestaAdmin.append(mensajeContainer);
        }
        
    })
    .catch(error => {
        console.error('Error al obtener la respuesta del administrador:', error);
    });
});
