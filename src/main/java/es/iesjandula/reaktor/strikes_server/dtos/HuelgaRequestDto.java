package es.iesjandula.reaktor.strikes_server.dtos;


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
public class HuelgaRequestDto 
{
	/**
     * Título de la huelga.
     * Identifica de forma única la huelga.
     */
    private String titulo;

    /**
     * Fecha de inicio de la huelga.
     */
    private Long fechaInicio;

    /**
     * Fecha de fin de la huelga.
     * Puede ser nula o vacía.
     */
    private Long fechaFin;
    
}


