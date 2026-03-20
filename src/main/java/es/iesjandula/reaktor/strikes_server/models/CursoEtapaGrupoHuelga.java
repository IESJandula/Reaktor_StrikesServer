package es.iesjandula.reaktor.strikes_server.models;

import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoHuelgaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumns;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curso_etapa_grupo_huelga")

/**
 * Entidad que representa la relación de Huelga con CursoEtapaGrupo.
 */
public class CursoEtapaGrupoHuelga
{

    /**
     * Clave primaria compuesta formada por:
     * curso, etapa, grupo y título de la huelga.
     */
    @EmbeddedId
    private CursoEtapaGrupoHuelgaId cursoEtapaGrupoHuelgaId ;

    /**
     * Curso, etapa y grupo al que pertenece la relación.
     */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "curso", referencedColumnName = "curso"),
        @JoinColumn(name = "etapa", referencedColumnName = "etapa"),
        @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    })
    private CursoEtapaGrupo cursoEtapaGrupo;

    /**
     * Huelga convocada.
     * <p>
     * Relación de tipo code ManyToOne con Huelga.
     * El atributo MapsId("titulo") indica que este campo
     * forma parte de la clave primaria compuesta.
     * </p>
     */
    @ManyToOne
<<<<<<< Updated upstream
    @MapsId("titulo") 
    @JoinColumn(name = "titulo", nullable = false)
    private Huelga huelga ;
=======
    @MapsId("titulo")
    private Huelga huelga; 
>>>>>>> Stashed changes
    
	/**
	 * Año escolar en el que se produce la huelga
	 */
	 private String anyoEscolar;
}