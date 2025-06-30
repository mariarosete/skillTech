document.addEventListener('DOMContentLoaded', function() {
    // Obtener el token JWT almacenado en localStorage
    const token = localStorage.getItem('token');

    document.querySelector(".botonEnviar").addEventListener('click', function(event) {
        event.preventDefault();
        // Obtener los valores del formulario
        var titulo = document.querySelector('#titulo').value; 
        var descripcion = document.querySelector('#descripcion').value; 
        var categoria = document.querySelector('#categoria').value; 
       
        // Verificar que los campos no estén vacíos
        if (titulo === '' || descripcion === '' || categoria === '' ) {
            console.error('Por favor, complete todos los campos.');
            return;
        }

        // Crear un objeto JSON con los datos del curso
        var curso = {
            descripcion: descripcion,
            categoria: categoria,
            titulo: titulo,
        };

        // Realizar la solicitud Fetch
        fetch('http://localhost:8080/cursos/insertar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluir el token JWT en el header de Authorization
            },
            body: JSON.stringify(curso)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar el curso');
            }
            // Si se registra correctamente, mostrar el modal de éxito
            document.getElementById('mensajeExitoModal2').style.display = 'block';
            // Ocultar el modal después de 3 segundos
            setTimeout(function() {
                document.getElementById('mensajeExitoModal2').style.display = 'none';
            }, 3000); 
        })
        .catch(error => {
            console.error('Error al registrar el usuario:', error);
        });
    });

    // Mostrar el modal al hacer clic en el enlace "Agregar Nuevo"
    document.querySelector('#crearCurso').addEventListener('click', function(event) {
        event.preventDefault();
        document.querySelector('#modal').style.display = 'block';
    });
    // Cerrar el modal de agregarContacto al hacer clic en el botón "Cerrar"
    document.querySelector('#cerrarModal').addEventListener('click', function() {
        document.querySelector('#modal').style.display = 'none';
    });

   // Cerrar el modal de editarContacto al hacer clic en el botón "Cerrar"
   document.querySelector('#cerrarModal2').addEventListener('click', function() {
    document.querySelector('#editarModal').style.display = 'none';
});

});
