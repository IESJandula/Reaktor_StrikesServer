package es.iesjandula.reaktor.strikes_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Clave primaria compuesta para la entidad CursoEtapaGrupo.
 * 
 * Está formada por:
 * - curso
 * - etapa
 * - grupo
 */
@Data
@Embeddable
public class CursoEtapaGrupoId implements Serializable
{
    /**
	 * Serial version UID para garantizar la compatibilidad durante la serialización.
	 */
	private static final long serialVersionUID = -1460415907859275226L;

	/**
     * Número de curso.
     * Forma parte de la clave primaria compuesta.
     */
    @Column
    private String curso;

	/**
     * Etapa del curso.
     * Forma parte de la clave primaria compuesta.
     */
    @Column(length = 50)
    private String etapa;

    /**
     * Grupo al que pertenece el curso
     * Forma parte de la clave primaria compuesta.
     */
    @Column(length = 2)
    private String grupo;
}