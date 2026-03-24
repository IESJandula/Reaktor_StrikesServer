package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.Data;

@Data
public class AlumnoHuelgaRequestDto
{
	/**
     * Título de la huelga.
     */
    private String titulo ;
    
	/**
     * Email del alumno.
     */
    private String email ;
    
	/**
	 * Respuesta del alumno.
     */
    private Boolean participa;
}
