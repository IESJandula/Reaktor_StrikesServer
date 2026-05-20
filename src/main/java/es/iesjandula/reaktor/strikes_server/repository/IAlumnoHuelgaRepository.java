package es.iesjandula.reaktor.strikes_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaResponseDto;
import es.iesjandula.reaktor.strikes_server.models.AlumnoHuelga;
import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;

/**
 * Repositorio JPA para la entidad AlumnoHuelga.
 * 
 * Proporciona consultas específicas para obtener alumnos
 * según su participación en una huelga.
 */
public interface IAlumnoHuelgaRepository extends JpaRepository<AlumnoHuelga, AlumnoHuelgaId>
{
    /**
     * Obtiene todos los alumnos con su estado de participación.
     */
    @Query("""
        SELECT new es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaResponseDto(
            a.nombre,
            a.apellidos,
            CASE 
                WHEN ah.participa = true THEN 'SI'
                WHEN ah.participa = false THEN 'NO'
                ELSE 'NS'
            END
        )
        FROM Alumno a
        LEFT JOIN AlumnoHuelga ah 
            ON a.email = ah.alumno.email 
            AND ah.huelga.titulo = :titulo
        WHERE (:curso IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.curso = :curso)
		    AND (:etapa IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.etapa = :etapa)
		    AND (:grupo IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.grupo = :grupo)
	    """)
    List<AlumnoHuelgaResponseDto> obtenerTodos(String titulo, String curso, String etapa, String grupo);

    /**
     * Obtiene solo los alumnos que SI participan en la huelga (SI).
     */
    @Query("""
        SELECT new es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaResponseDto(
            a.nombre,
            a.apellidos,
            'SI'
        )
        FROM AlumnoHuelga ah
        JOIN ah.alumno a
        WHERE ah.huelga.titulo = :titulo
	        AND ah.participa = true
	    	AND (:curso IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.curso = :curso)
		    AND (:etapa IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.etapa = :etapa)
		    AND (:grupo IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.grupo = :grupo)
    """)
    List<AlumnoHuelgaResponseDto> obtenerSi(String titulo, String curso, String etapa, String grupo);
    
    /**
     * Obtiene los alumnos que han respondido NO a la huelga (NO).
     */
    @Query("""
        SELECT new es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaResponseDto(
            a.nombre,
            a.apellidos,
            'NO'
        )
		FROM AlumnoHuelga ah
        JOIN ah.alumno a
        WHERE ah.huelga.titulo = :titulo
	        AND ah.participa = false
		    AND (:curso IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.curso = :curso)
		    AND (:etapa IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.etapa = :etapa)
		    AND (:grupo IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.grupo = :grupo)
    """)

    List<AlumnoHuelgaResponseDto> obtenerNo(String titulo, String curso, String etapa, String grupo);
    
    
    /**
     * Obtiene los alumnos que NO han respondido a la huelga (NS).
     */
	@Query("""
	    SELECT new es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaResponseDto(
	        a.nombre,
	        a.apellidos,
	        'NS'
	    )
	    FROM Alumno a
	    WHERE (:curso IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.curso = :curso)
	        AND (:etapa IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.etapa = :etapa)
	        AND (:grupo IS NULL OR a.cursoEtapaGrupo.cursoEtapaGrupoId.grupo = :grupo)
	        AND a.email NOT IN (
	            SELECT ah.alumno.email
	            FROM AlumnoHuelga ah
	            WHERE ah.huelga.titulo = :titulo
	        )
	""")
    List<AlumnoHuelgaResponseDto> obtenerNs(String titulo, String curso, String etapa, String grupo);

	// Comprueba si existen inscripciones para una huelga
	boolean existsByAlumnoHuelgaIdTitulo(String titulo);
	
	// Borra las relaciones alumno-huelga asociadas
	void deleteByAlumnoHuelgaIdTitulo(String titulo);
	
    // Obtiene la inscripción de un alumno en una huelga concreta para actualizar su decisión.
    AlumnoHuelga findByAlumnoHuelgaIdEmailAndAlumnoHuelgaIdTitulo(String email, String titulo);
}