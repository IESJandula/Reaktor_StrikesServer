package es.iesjandula.reaktor.strikes_server.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.strikes_server.models.CursoEtapaGrupoHuelga;
import es.iesjandula.reaktor.strikes_server.models.ids.CursoEtapaGrupoHuelgaId;

/**
 * Repositorio JPA para la entidad CursoEtapaGrupoHuelga.
 * 
 * <p>
 * Proporciona operaciones CRUD automáticas gracias a JpaRepository
 * y permite definir consultas personalizadas para gestionar
 * las relaciones entre huelgas y cursos, etapas y grupos.
 * </p>
 */
public interface ICursoEtapaGrupoHuelgaRepository extends JpaRepository<CursoEtapaGrupoHuelga, CursoEtapaGrupoHuelgaId>
{


}
