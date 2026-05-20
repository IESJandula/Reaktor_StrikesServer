package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO utilizado para enviar información de los alumnos
 * asociados a una huelga desde el backend hacia el cliente.
 *
 * Contiene los datos identificativos del alumno junto con
 * su estado de participación en la huelga.
 *
 * El estado puede ser:
 * <ul>
 *   <li>SI → el alumno participa en la huelga</li>
 *   <li>NO → el alumno no participa en la huelga</li>
 *   <li>NS → el alumno no ha respondido al formulario</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class AlumnoHuelgaResponseDto
{
    /**
     * Nombre del alumno.
     */
    private String nombre;

    /**
     * Apellidos del alumno.
     */
    private String apellidos;

    /**
     * Estado de participación del alumno en la huelga.
     * <p>
     * Este valor se calcula en base a la información almacenada
     * en la tabla intermedia alumno_huelga:
     * </p>
     * <ul>
     *   <li>SI → existe registro y participa = true</li>
     *   <li>NO → existe registro y participa = false</li>
     *   <li>NS → no existe registro en alumno_huelga</li>
     * </ul>
     */
    private String estado;
}