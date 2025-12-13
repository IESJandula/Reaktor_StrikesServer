package es.iesjandula.reaktor.strikes_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
