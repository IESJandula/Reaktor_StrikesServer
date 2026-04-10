package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;

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
     * Identifica de forma única la huelga enviada en la respuesta.
     */
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

    /**
     * Constructor utilizado desde el controlador REST.
     */
    public HuelgaResponseDto(String titulo, Date fechaInicio, Date fechaFin, EstadoHuelga estado, String urlEncuestado, Long numeroParticipantes) 
    {
        this.titulo = titulo;
        this.fechaInicio = fechaInicio != null ? fechaInicio.getTime() : null;
        this.fechaFin = fechaFin != null ? fechaFin.getTime() : null;
        this.estado = estado.name();
        this.urlEncuestado = urlEncuestado;
        this.numeroParticipantes = numeroParticipantes;
    }
}