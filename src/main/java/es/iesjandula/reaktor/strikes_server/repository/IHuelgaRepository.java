package es.iesjandula.reaktor.strikes_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto;
import es.iesjandula.reaktor.strikes_server.models.Huelga;


/**
 * Repositorio JPA para la entidad Huelga.
 * 
 * <p>Proporciona operaciones CRUD básicas gracias a JpaRepository y
 * define consultas personalizadas según las necesidades del proyecto.</p>
 */
public interface IHuelgaRepository extends JpaRepository<Huelga, String>
{

	@Query("SELECT new es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto(h.nombre, h.fechaInicio, h.fechaFin, h.estado, COUNT(a)) " +
		       "FROM Huelga h " +
		       "JOIN h.alumnos a " +
		       "GROUP BY h.nombre, h.fechaInicio, h.fechaFin, h.estado")
			Page<HuelgaResponseDto>findHuelgasResponseDto(Pageable pageable);
}
