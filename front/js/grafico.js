document.addEventListener('DOMContentLoaded', function() {
    // Cargar los datos desde el archivo JSON para el gráfico de inscripciones
    fetch('/json/cursosGrafica.json')
        .then(response => response.json())
        .then(data => {
            const cursos = data.cursos;

            // Contar la cantidad de inscripciones por categoría
            const categorias = {};
            cursos.forEach(curso => {
                const categoria = curso.categoria;
                categorias[categoria] = categorias[categoria] ? categorias[categoria] + curso.inscripciones : curso.inscripciones;
            });

            // Extraer los nombres de las categorías y las cantidades de inscripciones
            const nombresCategorias = Object.keys(categorias);
            const cantidadesInscripciones = Object.values(categorias);

            // Crear el contexto del gráfico
            const ctx = document.getElementById('graficoInscripciones').getContext('2d');

            // Configuración de los datos del gráfico
            const dataConfig = {
                labels: nombresCategorias,
                datasets: [{
                    label: 'Inscripciones por Categoría',
                    data: cantidadesInscripciones,
                    backgroundColor: [
                        '#FF6384', // Rojo
                        '#36A2EB', // Azul
                        '#FFCE56', // Amarillo
                        '#4BC0C0', // Verde azulado
                        '#9966FF', // Morado
                    ],
                    borderWidth: 1,
                    borderColor: '#fff', // Color del borde
                    hoverBorderColor: '#000', // Color del borde al pasar el ratón
                }]
            };

            const config = {
                type: 'pie',
                data: dataConfig,
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Inscripciones por Categoría',
                            font: { weight: 'bold', size: 16 } // Estilo del título
                        },
                        legend: {
                            position: 'bottom' // Posición de la leyenda
                        },
                        tooltip: {
                            enabled: true,
                            backgroundColor: 'rgba(0,0,0,0.8)',
                            titleFont: { weight: 'bold' },
                            callbacks: {
                                label: function(context) {
                                    return ` ${context.label}: ${context.parsed}%`;
                                }
                            }
                        }
                    },
                    animation: {
                        animateRotate: true, // Animación de rotación
                        animateScale: true // Animación de escala
                    }
                }
            };

            // Crear el gráfico usando Chart.js
            new Chart(ctx, config);
        })
        .catch(error => console.error('Error al cargar el archivo JSON:', error));

    // Función para actualizar el contador de usuarios
    function actualizarContadorUsuarios() {
        // Aquí podrías realizar una petición al servidor para obtener el número de usuarios registrados
        // Pero como en este caso no tenemos una fuente de datos real, simplemente vamos a establecer un número aleatorio
        const numeroUsuarios = Math.floor(Math.random() * 20); // Número aleatorio entre 0 y 100
        document.getElementById('numeroUsuarios').textContent = numeroUsuarios;
    }

    // Obtener el total de inscripciones al cargar la página
    actualizarContadorUsuarios();

    // Manejar el registro de un nuevo usuario
    document.getElementById('registrarUsuario').addEventListener('click', function() {
        // Aquí podrías enviar una petición al servidor para registrar un nuevo usuario
        // Pero como en este caso no tenemos una funcionalidad real de registro, simplemente vamos a actualizar el contador
        actualizarContadorUsuarios();
    });
});