package es.iesjandula.reaktor.strikes_server.models;

import java.util.List;

import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curso_etapa_grupo")


/**
 * Representa un curso, etapa y grupo dentro del sistema.
 * <p>
 * Esta clase está mapeada como una entidad JPA a la tabla 
 * curso_etapa_grupo. Se utiliza una clave primaria compuesta
 * representada por CursoEtapaGrupoId.
 * </p>
 * <p>
 * Mantiene una relación uno-a-muchos con Alumno, donde un curso,
 * etapa y grupo puede tener varios alumnos asociados.
 * </p>
 */
public class CursoEtapaGrupo
{
    /**
     * Clave primaria compuesta del curso, etapa y grupo.
     * <p>
     * Representada mediante CursoEtapaGrupoId.
     * </p>
     */
    @EmbeddedId
    private CursoEtapaGrupoId cusroCursoEtapaGrupoId ;
    
    /**
     * Lista de alumnos que pertenecen a este curso, etapa y grupo.
     * <p>
     * Relación de tipo OneToMany con Alumno. El atributo
     * mappedBy = "cursoEtapaGrupo" indica que la relación
     * es bidireccional y está mapeada desde la entidad Alumno.
     * </p>
     */
    @OneToMany(mappedBy = "cursoEtapaGrupo")
    private List<Alumno> alumnos ;
}
