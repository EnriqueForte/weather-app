🌦️ Weather App
Weather App es una aplicación web desarrollada en Java Spring Boot que permite a los usuarios consultar información meteorológica en tiempo real para cualquier ciudad. La aplicación utiliza datos de la API de terceros Visual Crossing Weather API y almacena resultados en caché utilizando Redis para optimizar el rendimiento.

🚀 Características principales
Consulta en tiempo real: Permite a los usuarios ingresar el nombre de una ciudad y obtener información meteorológica actualizada.
Datos detallados del clima:
Temperatura actual (en grados Celsius).
Temperatura máxima y mínima.
Descripción general de las condiciones climáticas.
Almacenamiento en caché con Redis:
Reduce las solicitudes repetitivas a la API externa.
Los datos de cada ciudad se almacenan durante 12 horas.
Interfaz amigable:
Diseño responsivo utilizando Bootstrap.
Iconos dinámicos representativos del clima (soleado, lluvioso, nublado, etc.) mediante Font Awesome.

🛠️ Tecnologías utilizadas
Backend:
Java Spring Boot.
Redis para almacenamiento en caché.
Visual Crossing Weather API (para datos meteorológicos).
Frontend:
HTML5, CSS3 y JavaScript.
Bootstrap (para diseño responsivo).
Font Awesome (para íconos dinámicos).
Herramientas adicionales:
Git y GitHub para control de versiones.
Postman para pruebas de API.

🖥️ Cómo usar la aplicación
Instalación:

Clona este repositorio:
bash
Copiar
Editar
git clone https://github.com/EnriqueForte/weather-app.git
cd weather-app
Configura las dependencias con Maven:
bash
Copiar
Editar
mvn clean install
Configura Redis:

Asegúrate de tener Redis instalado y ejecutándose en tu sistema.
La configuración de Redis está en el archivo application.properties.
Clave de la API:

Obtén una clave gratuita de Visual Crossing Weather API.
Configura la clave como variable de entorno:
bash
Copiar
Editar
export VISUAL_CROSSING_API_KEY=tu_clave_api
Ejecuta la aplicación:

bash
Copiar
Editar
mvn spring-boot:run
La aplicación estará disponible en: http://localhost:9090.
Uso de la interfaz:

Ingresa una ciudad en el cuadro de texto.
Haz clic en el botón "Buscar".
Observa los datos del clima junto con un ícono representativo.

📜 Licencia
Este proyecto está licenciado bajo la MIT License.

🤝 Contribuciones
¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar esta aplicación, no dudes en abrir un issue o enviar un pull request.
