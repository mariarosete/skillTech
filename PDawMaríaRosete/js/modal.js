document.addEventListener('DOMContentLoaded', function() {
    // Función para abrir la ventana modal de registro
    function abrirModalRegistro() {
        var modalRegistro = document.querySelector("#modalRegistro");
        modalRegistro.classList.remove("fade-out"); 
        modalRegistro.classList.add("fade-in"); 
        modalRegistro.style.display = "block"; 
    }

    // Agregar evento de clic al enlace "Regístrate" en la ventana modal de inicio de sesión
    document.querySelector("#mostrarRegistro").addEventListener("click", function(event) {
        event.preventDefault(); 
        abrirModalRegistro(); 
    });

    // Función para abrir y cerrar la ventana modal de inicio de sesión
    function abrirCerrarModal() {
        var modal = document.querySelector("#modal");
        if (modal.classList.contains("fade-in")) {
            modal.classList.remove("fade-in"); 
            modal.classList.add("fade-out"); 
            setTimeout(function() {
                modal.style.display = "none"; 
            }, 500); 
        } else {
            modal.classList.remove("fade-out");
            modal.classList.add("fade-in"); 
            modal.style.display = "block"; 
        }
    }

    // Agregar evento de clic al botón de abrir modal
    document.querySelector("#abrirModal").addEventListener("click", abrirCerrarModal);

    // Cerrar el modal de inicio de sesión al hacer clic en el botón "Cerrar"
    document.querySelector('#cerrarModal').addEventListener('click', abrirCerrarModal);

    // Cerrar el modal de registro al hacer clic en el botón "Cerrar"
    document.querySelector('#cerrarModal2').addEventListener('click', function() {
        var modalRegistro = document.querySelector("#modalRegistro");
        modalRegistro.classList.remove("fade-in"); 
        modalRegistro.classList.add("fade-out"); 
        setTimeout(function() {
            modalRegistro.style.display = "none"; 
        }, 500); 
    });
});
