package es.iesjandula.reaktor.strikes_server.dtos;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO utilizado para enviar información de una huelga
 * desde el backend hacia el cliente.
 *
 * <p>Proporciona los datos principales de la huelga,
 * como su título, inicio y fin representativos.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuelgaResponseDto 
{
    /**
     * Título de la huelga.
     * Identifica de forma única la huelga enviada en la respuesta.
     */
    private String titulo;
    
    /**
     * Fecha y hora de inicio del evento.
     */
    private Date inicio;
    
    /**
     * Fecha y hora de fin del evento.
     */
    private Date fin;
}


