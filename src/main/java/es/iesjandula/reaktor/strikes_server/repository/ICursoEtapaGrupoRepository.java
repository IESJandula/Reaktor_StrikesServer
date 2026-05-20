package es.iesjandula.reaktor.strikes_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.iesjandula.reaktor.strikes_server.dtos.CursoEtapaGrupoResponseDto;
import es.iesjandula.reaktor.strikes_server.models.CursoEtapaGrupo;
import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoId;

/**
 * Repositorio JPA para la entidad CursoEtapaGrupo.
 * 
 * <p>Proporciona operaciones CRUD básicas gracias a JpaRepository y
 * define consultas personalizadas según las necesidades del proyecto.</p>
 */
public interface ICursoEtapaGrupoRepository extends JpaRepository<CursoEtapaGrupo, CursoEtapaGrupoId>
{

    /**
     * Obtiene todas las combinaciones de curso, etapa y grupo.
     */
    @Query("""
        SELECT new es.iesjandula.reaktor.strikes_server.dtos.CursoEtapaGrupoResponseDto(
            c.cursoEtapaGrupoId.curso,
            c.cursoEtapaGrupoId.etapa,
            c.cursoEtapaGrupoId.grupo
        )
        FROM CursoEtapaGrupo c
    """)
    List<CursoEtapaGrupoResponseDto> obtenerCombinaciones();
}
