package com.enrique.weather.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class WeatherService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String apiKey = System.getenv("VISUAL_CROSSING_API_KEY");

    public String getWeather(String city) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("La clave de API de Visual Crossing no está configurada. Verifica la variable de entorno.");
        }

        String cacheKey = "weather:" + city;
        String cacheData = redisTemplate.opsForValue().get(cacheKey);

        // Si los datos están en caché, devolverlos
        if (cacheData != null) {
            System.out.println("Datos encontrados en caché para: " + city);
            return cacheData;
        }

        // URL de la API
        String apiUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + apiKey;
        System.out.println("Consultando clima para: " + city);
        System.out.println("Usando URL: " + apiUrl);

        try {
            // Realizar la solicitud HTTP
            @SuppressWarnings("deprecation")
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Verificar el código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error: Código de respuesta de la API: " + responseCode);
            }

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            // Procesar el JSON
            JSONObject jsonResponse = new JSONObject(inline.toString());
            JSONObject today = jsonResponse.getJSONArray("days").getJSONObject(0);

            // Extraer temperaturas y convertir a Celsius
            double tempFahrenheit = today.getDouble("temp");
            double tempMaxFahrenheit = today.getDouble("tempmax");
            double tempMinFahrenheit = today.getDouble("tempmin");

            double tempCelsius = (tempFahrenheit - 32) * 5 / 9;
            double tempMaxCelsius = (tempMaxFahrenheit - 32) * 5 / 9;
            double tempMinCelsius = (tempMinFahrenheit - 32) * 5 / 9;

            // Crear una respuesta personalizada
            String result = String.format(
                "Clima para %s:\nTemperatura actual: %.2f °C\nTemperatura máxima: %.2f °C\nTemperatura mínima: %.2f °C\nDescripción: %s",
                city, tempCelsius, tempMaxCelsius, tempMinCelsius, today.getString("description")
            );

            // Guardar en caché la respuesta personalizada
            redisTemplate.opsForValue().set(cacheKey, result);
            redisTemplate.expire(cacheKey, 12, TimeUnit.HOURS); // Establecer tiempo de expiración
            System.out.println("Datos almacenados en caché para: " + city);

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Error al comunicarse con la API de Visual Crossing: " + e.getMessage(), e);
        }
    }
}