package es.iesjandula.reaktor.strikes_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación Reaktor Events Server.
 * <p>
 * Esta clase arranca la aplicación Spring Boot y configura el escaneo de
 * componentes dentro del paquete "es.iesjandula". También habilita la
 * programación de tareas (scheduling) automáticas.
 * </p>
 * 
 * @author Mónica Luna
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages =
{ "es.iesjandula" })

/**
 * Método principal que lanza la aplicación Spring Boot.
 * 
 * @param args los argumentos de línea de comandos (opcional).
 */
public class ReaktorStrikesServerApplication
{

	
	public static void main(String[] args)
	{
		SpringApplication.run(ReaktorStrikesServerApplication.class, args) ;
	}
}
