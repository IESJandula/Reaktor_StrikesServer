package es.iesjandula.reaktor.strikes_server.dtos;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO utilizado para enviar información de una huelga
 * desde el backend hacia el cliente.
 *
 * Contiene los datos básicos de la huelga junto con
 * la URL del formulario y el número de participantes.
 */
@Getter
@Setter
@NoArgsConstructor
public class HuelgaResponseDto 
{
    /**
     * Título de la huelga.
<<<<<<< Updated upstream
     * Identifica de forma única la huelga enviada en la respuesta.
     */

=======
     */
>>>>>>> Stashed changes
    private String titulo;

    /**
     * Fecha de inicio en milisegundos.
     */
    private Long fechaInicio;

    /**
     * Fecha de fin en milisegundos.
     */
    private Long fechaFin;

    /**
     * Estado actual de la huelga.
     */
    private String estado;

    /**
     * URL pública del formulario.
     */
    private String urlEncuestado;

    /**
     * Número total de alumnos inscritos.
     */
    private Long numeroParticipantes;
<<<<<<< Updated upstream


    /**
     * Constructor utilizado desde el controlador.
=======

    /**
     * Constructor utilizado desde el controlador REST.
>>>>>>> Stashed changes
     */
    public HuelgaResponseDto(
            String titulo,
            Long fechaInicio,
            Long fechaFin,
            String estado,
            String urlEncuestado,
            Long numeroParticipantes
    ) {
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.urlEncuestado = urlEncuestado;
        this.numeroParticipantes = numeroParticipantes;
    }
}