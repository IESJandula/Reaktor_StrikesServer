package es.iesjandula.reaktor.strikes_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.strikes_server.models.Alumno;

/**
 * Repositorio JPA para la entidad Alumno.
 * 
 * <p>Proporciona operaciones CRUD básicas gracias a JpaRepository y
 * define consultas personalizadas según las necesidades del proyecto.</p>
 */
public interface IAlumnoRepository extends JpaRepository<Alumno, String>
{

	List<Alumno> findByNombreIgnoreCaseAndApellidosIgnoreCase(String nombre, String apellidos);
	
	List<Alumno> findByApellidosIgnoreCaseAndNombreIgnoreCase(String apellidos, String nombre);

}
