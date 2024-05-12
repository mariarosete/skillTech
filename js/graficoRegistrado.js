document.addEventListener('DOMContentLoaded', function() {
    // Cargar los datos desde el archivo JSON
    fetch('/json/cursoGraficaRegistrado.json')
        .then(response => response.json())
        .then(data => {
            const cursos = data.cursos;

            // Extraer las categorías y las cantidades de cursos
            const categorias = cursos.map(curso => curso.categoria);
            const cantidades = cursos.map(curso => curso.cantidad_cursos);

            // Asignar un color diferente a cada categoría
            const colores = categorias.map(categoria => {
                switch (categoria) {
                    case 'Desarrollo web':
                        return '#FF6384'; 
                    case 'Programación':
                        return '#36A2EB'; 
                    case 'Bases de datos':
                        return '#FFCE56';
                    case 'Seguridad':
                        return '#4BC0C0'; 
                    case 'Tecnologias':
                        return '#9966FF'; 
                    default:
                        return '#000000'; 
                }
            });

            // Crear el contexto del gráfico
            const ctx = document.getElementById('graficoCursosPorCategoria').getContext('2d');

            // Configurar los datos del gráfico
            const dataConfig = {
                labels: categorias,
                datasets: [{
                    label: '',
                    data: cantidades,
                    backgroundColor: colores, // Colores de las barras
                    borderWidth: 1
                }]
            };

            // Configurar las opciones del gráfico
            const options = {
                scales: {
                    y: {
                        beginAtZero: true 
                    }
                },
                plugins: {
                    legend: {
                        labels: {
                            boxWidth: 0 // Oculta el cuadro de color
                        }
                    },
                    title: {
                        display: true,
                        text: 'Distribución de Cursos por Categoría',
                        font: {
                            size: 18,
                            weight: 'bold'
                        }
                    }
                }
            };

            // Crear el gráfico de barras
            new Chart(ctx, {
                type: 'bar',
                data: dataConfig,
                options: options
            });
        })
        .catch(error => console.error('Error al cargar el archivo JSON:', error));
});
