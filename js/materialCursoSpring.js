document.addEventListener('DOMContentLoaded', function () {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');
    console.log('Token:', token); // Verificar que el token se obtiene correctamente

    // Hacer una solicitud Fetch para obtener los materiales del curso desde el servidor
    fetch('http://localhost:8080/materialCursos/buscarMaterialCursos', {
        method: 'GET', // Agregar el método GET
        headers: {
            'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
        }
    })
    .then(response => {
        console.log('Solicitud Fetch:', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }); // Depurar la solicitud Fetch completa
        return response.json();
    })
    .then(data => {
        // Iterar sobre los materiales y agregarlos a las listas correspondientes
        for (const material of data) {
            const { nombre, tipoMaterial, url } = material;

            // Crear elemento de lista y enlace
            const listItem = document.createElement('li');
            const link = document.createElement('a');
            link.href = url;

            // Asignar icono según el tipo de material
            let iconClass = '';
            switch (tipoMaterial) {
                case "PDF":
                    iconClass = 'fas fa-file-pdf';
                    break;
                case "VIDEO":
                    iconClass = 'fas fa-video';
                    break;
                case "ENLACE":
                    iconClass = 'fas fa-link';
                    break;
                default:
                    iconClass = 'fas fa-file';
            }

            // Crear elemento de icono
            const icon = document.createElement('i');
            icon.className = iconClass;

            // Texto del enlace
            link.append(icon);
            link.append(document.createTextNode(` ${nombre}`));

            // Seleccionar la lista correspondiente según el nombre del curso
            let materialsList;
            switch (nombre) {
                case "Introducción a Spring":
                    materialsList = document.querySelector('#introduccion-spring-materials-list');
                    break;
                case "Configuración de Spring":
                    materialsList = document.querySelector('#configuracion-spring-materials-list');
                    break;
                case "Inyección de dependencias":
                    materialsList = document.querySelector('#inyeccion-spring-materials-list');
                    break;
                default:
                    // Si no coincide con ningún nombre, continuar con la siguiente iteración
                    continue;
            }

            // Agregar enlace al elemento de la lista
            listItem.append(link);

            // Agregar elemento de lista a la lista correspondiente
            materialsList.append(listItem);
        }
    })
    .catch(error => {
        console.error('Error al obtener los materiales del curso:', error);
    });
});
