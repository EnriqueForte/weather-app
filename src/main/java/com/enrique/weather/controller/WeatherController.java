package com.enrique.weather.controller;

import org.springframework.web.bind.annotation.RestController;

import com.enrique.weather.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<String> getWeather(@PathVariable String city) {
        // Llama al servicio para obtener la respuesta personalizada
        String weatherInfo = weatherService.getWeather(city.trim());
        return ResponseEntity.ok(weatherInfo);
    }
}