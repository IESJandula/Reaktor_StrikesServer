package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoHuelgaResquestDto
{
	/**
     * Título de la huelga.
     */
    private String titulo;
    
    /**
     * Fecha inicio de inscripciones.
     */
    private Long fechaInicio;

    /**
     * Fecha fin de inscripciiones.
     */
    private Long fechaFin;
}
