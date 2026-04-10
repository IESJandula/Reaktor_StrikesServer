package es.iesjandula.reaktor.strikes_server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;

/**
 * Repositorio JPA para la entidad Huelga.
 * 
 * Proporciona:
 * - Operaciones CRUD automáticas gracias a JpaRepository
 * - Consultas personalizadas necesarias para el proyecto
 */
public interface IHuelgaRepository extends JpaRepository<Huelga, String>
{

    /**
     * Devuelve las huelgas paginadas ya transformadas a DTO.
     * 
     * Incluye el número de alumnos inscritos mediante COUNT.
     */
    @Query("""
        SELECT new es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto(
            h.titulo,
            h.fechaInicio,
            h.fechaFin,
            h.estado,
            h.urlEncuestado,
            COUNT(a)
        )
        FROM Huelga h
        LEFT JOIN h.alumnos a 
        GROUP BY  h.titulo,
	        h.fechaInicio,
	        h.fechaFin,
	        h.estado,
	        h.urlEncuestado
        """)
    Page<HuelgaResponseDto> obtenerHuelgas(Pageable pageable);


    /**
     * Devuelve todas las huelgas con un estado concreto.
     * 
     * Este método lo usa el Scheduler para procesar
     * únicamente las huelgas activas (CONVOCADA).
     */
    List<Huelga> findByEstado(EstadoHuelga estado);
}