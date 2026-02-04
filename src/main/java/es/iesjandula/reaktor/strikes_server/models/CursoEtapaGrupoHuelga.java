package es.iesjandula.reaktor.strikes_server.models;

import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoHuelgaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curso_etapa_grupo_huelga")

/**
 * Entidad que representa la relación de Huelga con CursoEtapaGrupo.
 * <p>
 * Esta tabla actúa como una entidad intermedia que permite
 * modelar una relación muchos-a-muchos entre CursoEtapaGrupo
 * y Huelga, utilizando una clave primaria compuesta.
 * </p>
 * 
 * La clave primaria está formada por lo identificadores de CursoEtapaGrupo
 * y el identificador de la huelga, definidos en CursoEtapaGrupoHuelgaId.
 */
public class CursoEtapaGrupoHuelga
{

    /**
     * Clave primaria compuesta de la entidad. Está formada por el email del alumno y el título de la huelga.
     */
    @EmbeddedId
    private CursoEtapaGrupoHuelgaId cursoEtapaGrupoHuelgaId ;

    /**
     * Curso, etapa y grupo al que pertenece la relación.
     * <p>
     * Relación de tipo ManyToOne con la entidad CursoEtapaGrupo.
     * </p>
     */
    @ManyToOne
    @MapsId("cursoEtapaGrupoId")
    private CursoEtapaGrupo cursoEtapaGrupo;

    /**
     * Huelga asociada al curso, etapa y grupo.
     * <p>
     * Relación de tipo ManyToOne con la entidad Huelga.
     * </p>
     */
    @ManyToOne
    @MapsId("nombre")
    private Huelga huelga; 
    
	/**
	 * Año escolar en el que se produce la huelga
	 */
	 private String anyoEscolar;
}