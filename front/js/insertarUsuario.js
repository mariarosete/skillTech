document.addEventListener('DOMContentLoaded', function() {

    document.querySelector(".botonEnviar").addEventListener('click', function(event) {
        event.preventDefault();

        // Obtener los valores del formulario
        var nombre = document.querySelector('#nombre').value; 
        var apellidos = document.querySelector('#apellidos').value; 
        var correo = document.querySelector('#correo').value; 
        var password = document.querySelector('#password').value; 
        var rolSeleccionado = document.querySelector('#rol').options[document.querySelector('#rol').selectedIndex].text; 

        console.log('Rol seleccionado:', rolSeleccionado); // Agregar esta línea para verificar el valor del rol seleccionado

        // Verificar que los campos no estén vacíos
        if (nombre === '' || apellidos === '' || correo === '' || password === '' || rolSeleccionado === '') {
            console.error('Por favor, complete todos los campos.');
            return;
        }

        // Crear un objeto JSON con los datos del usuario
        var usuario = {
            nombre: nombre,
            apellidos: apellidos,
            correo: correo,
            password: password,
            rol: rolSeleccionado // Asignar el texto del rol al objeto usuario
        };

        // Realizar la solicitud Fetch
        fetch('https://skilltechback-42717f57a83b.herokuapp.com/usuarios/insertar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuario)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar el usuario');
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
    document.querySelector('#crearUsuario').addEventListener('click', function(event) {
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
