package es.iesjandula.reaktor.strikes_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Representa la clave primaria compuesta de la entidad Pertenece.
 */
@Data
@Embeddable
public class CursoEtapaGrupoHuelgaId implements Serializable 
{

    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */
	private static final long serialVersionUID = 7375364774028493903L ;
	
    /**
     * Clave primaria compuesta de la entidad CursoEtapaGrupo.
     */
    private CursoEtapaGrupoId cursoEtapaGrupoId;
	
    /**
     * Título de la huelga.
     * Forma parte de la clave primaria compuesta.
     */
	@Column(length = 100)
	private String titulo ;
	
}