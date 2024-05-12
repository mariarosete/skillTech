
let atras = document.getElementById('atras');
let adelante = document.getElementById('adelante');
let imagen = document.getElementById('img');
let puntos = document.getElementById('puntos');
let texto = document.getElementById('texto');
let actual = 0;

// Función asíncrona para cargar los datos desde cursos.json
async function cargarCursos() {
  
    const respuesta = await fetch('/json/cursos.json');
    return await respuesta.json();
}

// Función principal que se ejecuta al cargar la página
async function inicializarCarrusel() {
    imagenes = await cargarCursos();
  
    // Mostrar la primera imagen al cargar la página
    mostrarImagen();
  
    // Actualizar el texto y los puntos para la primera imagen
    actualizarTexto();
    actualizarPuntos();
  
    atras.addEventListener('click', function(){
      actual -= 1;
  
      if (actual === -1){
        actual = imagenes.length - 1;
      }
  
      mostrarImagen();
      actualizarTexto();
      actualizarPuntos();
    });
  
    adelante.addEventListener('click', function(){
      actual += 1;
  
      if (actual === imagenes.length){
        actual = 0;
      }
  
      mostrarImagen();
      actualizarTexto();
      actualizarPuntos();
    });

    // Agregar evento de clic a la imagen para redirigir al enlace del curso
    imagen.addEventListener('click', function () {
      const enlace = imagenes[actual].enlace;
      const idcurso = imagenes[actual].id; // Suponiendo que el ID del curso está disponible en el JSON
      if (enlace) {
          window.location.href = `${enlace}?idcurso=${idcurso}`;
      }
    });
}

// Función para mostrar la imagen actual
function mostrarImagen() {
    const rutaImagen = `/imagenes/${imagenes[actual].imagen}`;
    // Cambiar la imagen por un enlace que redirija al enlace del curso
    imagen.innerHTML = `<a href="${imagenes[actual].enlace}" target="_blank"><img class="img" src="${rutaImagen}" alt="imagen curso" loading="lazy"></a>`;
}

// Función para actualizar el texto con la información del curso actual
function actualizarTexto() {
    texto.innerHTML = `
        <h3>${imagenes[actual].nombre}</h3>
        <p>${imagenes[actual].info}</p>
    `;
}

// Función para actualizar los puntos indicadores
function actualizarPuntos() {
    puntos.innerHTML = "";
    for (let i = 0; i < imagenes.length; i++){
        if(i === actual){
            puntos.innerHTML += '<p class="bold">.</p>';
        }
        else{
            puntos.innerHTML += '<p>.</p>';
        }
    } 
}

// Llamar a la función principal para inicializar el carrusel
inicializarCarrusel();
