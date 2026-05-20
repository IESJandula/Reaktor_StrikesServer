package es.iesjandula.reaktor.strikes_server.models;

import java.util.List;

import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;



/**
 * Representa la entidad CursoEtapaGrupo.
 * 
 * <p>Esta clase se utiliza para representar un curso etapa grupo.</p>
 */
@Data
@Entity
public class CursoEtapaGrupo
{
    /**
     * Clave primaria compuesta del curso, etapa y grupo.
     * <p>
     * Representada mediante CursoEtapaGrupoId.
     * </p>
     */
    @EmbeddedId
    private CursoEtapaGrupoId cursoEtapaGrupoId ;
    
    /**
     * Lista de alumnos que pertenecen a este curso, etapa y grupo.
     */
    @OneToMany(mappedBy = "cursoEtapaGrupo")
    private List<Alumno> alumnos ;
}
