document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');
    
    // Función para cargar los cursos disponibles
    function cargarCursosDisponibles() {
        fetch('http://localhost:8080/cursos/buscarCursos', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los cursos disponibles');
            }
            return response.json();
        })
        .then(cursos => {
            const selectCurso = document.querySelector('#curso_idcurso'); 
            // Limpiar el select de cursos
            selectCurso.innerHTML = '';
            // Agregar opción por defecto
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Seleccione un curso';
            selectCurso.appendChild(defaultOption);
            // Agregar cada curso como opción en el select
            cursos.forEach(curso => {
                const option = document.createElement('option');
                option.value = curso.idcurso;
                option.textContent = curso.titulo;
                selectCurso.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error al cargar los cursos disponibles:', error);
        });
    }

    // Llamar a la función para cargar los cursos disponibles al cargar la página
    cargarCursosDisponibles();

    // Evento para enviar el formulario de inserción de contenido
    document.querySelector('#registroContenido').addEventListener('submit', function(event) { 
        event.preventDefault();
        // Obtener los valores del formulario
        var nombre = document.querySelector('#nombre').value; 
        var tipoMaterial = document.querySelector('#tipoMaterial').value; 
        var url = document.querySelector('#url').value; 
        var cursoId = document.querySelector('#curso_idcurso').value; 
       
        // Verificar que los campos no estén vacíos
        if (nombre === '' || tipoMaterial === '' || url === '' || cursoId === '') {
            console.error('Por favor, complete todos los campos.');
            return;
        }

        // Crear un objeto JSON con los datos del contenido
        var contenido = {
            nombre: nombre,
            tipoMaterial: tipoMaterial,
            url: url,
            curso: {
                idcurso: cursoId
            }
        };

        // Realizar la solicitud Fetch para insertar el contenido
        fetch('http://localhost:8080/materialCursos/insertar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            },
            body: JSON.stringify(contenido)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar el contenido del curso');
            }
            // Si se registra correctamente, mostrar el modal de éxito
            document.getElementById('mensajeExitoModal2').style.display = 'block';
            // Ocultar el modal después de 3 segundos
            setTimeout(function() {
                document.getElementById('mensajeExitoModal2').style.display = 'none';
            }, 3000); 
        })
        .catch(error => {
            console.error('Error al registrar el contenido del curso:', error);
        });
    });
    // Mostrar el modal al hacer clic en el enlace "Agregar Nuevo"
    document.querySelector('#crearContenido').addEventListener('click', function(event) {
        event.preventDefault();
        document.querySelector('#modal').style.display = 'block';
    });
    
    // Cerrar el modal al hacer clic en el botón "Cerrar"
    document.querySelector('#cerrarModal').addEventListener('click', function() {
        document.querySelector('#modal').style.display = 'none';
    });

    // Cerrar el modal de editarContacto al hacer clic en el botón "Cerrar"
    document.querySelector('#cerrarModal2').addEventListener('click', function() {
        document.querySelector('#editarModal').style.display = 'none';
    });
});
