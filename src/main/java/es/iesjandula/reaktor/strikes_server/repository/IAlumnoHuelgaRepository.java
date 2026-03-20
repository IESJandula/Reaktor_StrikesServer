package es.iesjandula.reaktor.strikes_server.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.strikes_server.models.AlumnoHuelga;
import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;


/**
 * Repositorio JPA para la entidad AlumnoHuelga.
 * 
 * <p>Proporciona operaciones CRUD básicas gracias a JpaRepository y
 * define consultas personalizadas según las necesidades del proyecto.</p>
 */
public interface IAlumnoHuelgaRepository extends JpaRepository<AlumnoHuelga, AlumnoHuelgaId>
{

}

