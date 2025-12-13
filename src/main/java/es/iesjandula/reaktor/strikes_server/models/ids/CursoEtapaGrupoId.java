package es.iesjandula.reaktor.strikes_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la clave primaria compuesta de la entidad CursoEtapaGrupo.
 * 
 * <p>Esta clase se utiliza para identificar de manera única el curso al que pertenece el alumno mediante
 * el curso, la etapa y el grupo al que pertenece.</p>
 * 
 * <p>Al implementar Serializable, puede ser utilizada como clave embebida en JPA.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CursoEtapaGrupoId implements Serializable 
{

    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */
	private static final long serialVersionUID = 7375364774028493903L ;
	
    /**
     * Curso al que pertenece el alumno.
     * Forma parte de la clave primaria compuesta.
     */
	private Integer curso ;
	
    /**
     * Etapa a la que pertenece el alumno.
     * Forma parte de la clave primaria compuesta.
     */
	private String etapa ;
	
    /**
     * Grupo al que pertenece el alumno.
     * Forma parte de la clave primaria compuesta.
     */
	private String grupo ;	
	
}

