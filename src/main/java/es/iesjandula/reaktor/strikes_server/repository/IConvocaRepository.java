package es.iesjandula.reaktor.strikes_server.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.strikes_server.models.Convoca;
import es.iesjandula.reaktor.strikes_server.models.ids.ConvocaId;


/**
 * Repositorio JPA para la entidad Convoca.
 * 
 * <p>Proporciona operaciones CRUD básicas gracias a JpaRepository y
 * define consultas personalizadas según las necesidades del proyecto.</p>
 */
public interface IConvocaRepository extends JpaRepository<Convoca, ConvocaId>
{

}

