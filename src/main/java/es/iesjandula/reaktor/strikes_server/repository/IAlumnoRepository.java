package es.iesjandula.reaktor.strikes_server.repository;

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

}
