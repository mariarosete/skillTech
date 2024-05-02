window.onload = function () {
    
    const cursosContainer = document.querySelector('.cursos-container');
    const infoCursos = document.querySelector(".info-cursos");

    async function cargarCursos() {
        const respuesta = await fetch('/json/cursos.json');
        return (await respuesta.json());
    }

    async function mostrarCursosCompletos() {
        const cursos = await cargarCursos();
        infoCursos.innerHTML = ""; // Limpiar el contenedor de información

        for (const curso of cursos) {
           //Ruta de las imagenes
            const rutaImagen = `/imagenes/${curso.imagen}`;

            // Construir la ficha completa con estructura HTML
            const fichaCompleta = `
                <div class="rotate-container">
                    <div class="ficha-back">
                        <h4>${curso.nombre}</h4>
                        <p>${curso.info}</p>
                        <button onclick="volverFicha(this)" class="btn-rotate">
                        <i class="fas fa-chevron-left"></i>&nbsp;Volver
                        </button>
                    </div>
                    <div class="ficha-front">
                        <h4>${curso.nombre}</h4>
                        <a href="${curso.enlace}?idcurso=${curso.id}" target="_blank"> <!-- Agregar el idcurso como parámetro -->
                            <img src="${rutaImagen}" onclick="accederEnlace('${curso.enlace}')">
                         </a>
                         <button class="btn-rotate" onclick="rotarFicha(this)">
                         <i class="fas fa-chevron-right"></i>&nbsp; Info
                     </button>
                    </div>
                </div>
            `;

            infoCursos.innerHTML += fichaCompleta;
        }
    }

    /*// Función para acceder al enlace cuando se hace clic en la imagen
    function accederEnlace(enlace) {
        window.location.href = enlace;
    }*/

    mostrarCursosCompletos();
};