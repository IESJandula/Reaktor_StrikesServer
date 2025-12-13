package es.iesjandula.reaktor.strikes_server.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO utilizado para enviar información de un alumno
 * desde el backend hacia el cliente.
 *
 * <p>Proporciona los datos principales del alumno,
 * como su email, nombre y apellidos representativos.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponseDto 
{
    /**
     * Email del alumno.
     * Identifica de forma única la categoría enviada en la respuesta.
     */
    private String email;
    
    /**
     * Nombre del alumno.
     * Representa el nombre del alumno.
     */
    private String nombre;
    
    /**
     * Apellidos del alumno.
     * Representa los apellidos del alumno.
     */
    private String apellidos;
}

