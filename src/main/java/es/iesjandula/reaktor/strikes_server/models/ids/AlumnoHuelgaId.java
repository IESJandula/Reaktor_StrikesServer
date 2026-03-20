package es.iesjandula.reaktor.strikes_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clave primaria compuesta para la entidad AlumnoHuelga.
 * 
 * Está formada por:
 * - email del alumno
 * - título de la huelga
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AlumnoHuelgaId implements Serializable
{

	/**
	 * Serial version UID para garantizar la compatibilidad durante la serialización.
	 */
	private static final long serialVersionUID = 8507887047247296011L;


	/**
     * Email del alumno
     */
    private String email;

    /**
     * Título de la huelga
     */
    private String titulo;
}