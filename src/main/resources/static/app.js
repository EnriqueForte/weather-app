document.getElementById('getWeather').addEventListener('click', async function () {
    const city = document.getElementById('cityInput').value;
    const resultDiv = document.getElementById('weatherResult');
    const iconDiv = document.getElementById('weatherIcon'); // Div para los iconos

    if (!city) {
        resultDiv.textContent = 'Por favor, ingrese una ciudad';
        resultDiv.classList.remove('d-none', 'alert-info');
        resultDiv.classList.add('alert-danger');
        iconDiv.innerHTML = ''; // Limpiar iconos
        return;
    }

    try {
        const response = await fetch(`/api/weather/${city}`);
        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        const data = await response.text();
        resultDiv.textContent = data;
        resultDiv.classList.remove('d-none', 'alert-danger');
        resultDiv.classList.add('alert-info');

        // Extraer descripción del clima del texto de respuesta
        const descriptionMatch = data.match(/Descripción: (.*)/);
        if (!descriptionMatch) {
            throw new Error("No se pudo extraer la descripción del clima.");
        }
        const description = descriptionMatch[1];

        // Determinar el ícono según la descripción
        let iconClass = '';
        if (description.includes('Rain')) {
            iconClass = 'fas fa-cloud-showers-heavy'; // Ícono de lluvia---
        } else if (description.includes('Cloudy')) {
            iconClass = 'fas fa-cloud'; // Ícono de nubes---
        } else if (description.includes('Sunny') || description.includes('Clear')) {
            iconClass = 'fas fa-sun'; // Ícono de sol---
        } else {
            iconClass = 'fas fa-smog'; // Ícono por defecto (niebla o desconocido)
        }

        // Mostrar el ícono
        iconDiv.innerHTML = `<i class="${iconClass} fa-3x"></i>`;
    } catch (error) {
        resultDiv.textContent = 'Error al obtener los datos del clima.';
        resultDiv.classList.remove('d-none', 'alert-info');
        resultDiv.classList.add('alert-danger');
        iconDiv.innerHTML = ''; // Limpiar iconos
        console.error(error);
    }
});
