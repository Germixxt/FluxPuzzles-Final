package com.duoc.fluxpuzzles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot: FluxPuzzles.
 * 
 * Esta clase marca el punto de entrada de la aplicación y activa:
 * - Component scanning: busca beans en el paquete com.duoc.fluxpuzzles
 * - Auto-configuration: configura automáticamente Spring según las librerías en classpath
 * - Configuration properties: carga application.properties
 * 
 * La aplicación proporciona una API REST con autenticación JWT para:
 * - Gestionar usuarios (registro, login)
 * - Administrar puzzles (crear, leer, actualizar, eliminar)
 * - Obtener retos diarios desde una API externa
 */
@SpringBootApplication
public class FluxpuzzlesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxpuzzlesApplication.class, args);
	}

}
