package es.iesjandula.reaktor.strikes_server.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="huelga")

/**
 * Representa una huelga dentro del sistema.
 * <p>
 * Esta clase está mapeada como una entidad JPA a la tabla huelga.
 * Contiene información sobre la huelga, incluyendo fechas, estado
 * y los alumnos convocados.
 * </p>
 * <p>
 * Mantiene relaciones:
 * <ul>
 *   <li>OneToMany con AlumnoHuelga: lista de alumnos inscritos en la huelga.</li>
 *   <li>ManyToOne con Estado: estado actual de la huelga.</li>
 *   <li>OneToMany con CursoEtapaGrupoHuelga: lista de cursos que se pueden inscribir en la huelga.</li>
 * </ul>
 * </p>
 */
public class Huelga
{
    /**
     * Título de la huelga.
     * <p>
     * Actúa como clave primaria de la entidad. Longitud máxima de 25 caracteres.
     * </p>
     */
	@Id
	@Column(length = 25)
	private String nombre ; 
	
    /**
     * Fecha de inicio de la huelga.
     * <p>
     * Este campo es obligatorio.
     * </p>
     */
	@Column(nullable = false)
	private Date fechaInicio ;
	
    /**
     * Fecha de fin de la huelga.
     */
	@Column(nullable = false)
	private Date fechaFin ;
	
	 /**
     * Fecha de límite de inscripción a la huelga.
     */
	@Column(nullable = false)
	private Date fechaLimite ;
    /**
     * Url por la cual tienen acceso a inscripción los alumnos a la huelga.
     * <p>
     * Este campo es opcional, puede ser nulo si la huelga aún está en curso.
     * </p>
     */
	@Column
	private String urlGoogleScript ;
	
	/**
     * Lista de convocatorias de alumnos para esta huelga.
     * <p>
     * Relación de tipo OneToMany con AlumnoHuelga.
     * El atributo mappedBy = "alumno_huelga" indica que la relación
     * está mapeada desde la entidad AlumnoHuelga.
     * </p>
     */
	@OneToMany(mappedBy = "huelga")
	private List<AlumnoHuelga> alumnos ;
	
    /**
     * Nombre del estado.
     * <p>
     * Atributo que indica el estado en el que se encuentra la convocatoria de huelga.
     * </p>
     */
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoHuelga estado;
	/**
     * Lista de convocatorias de alumnos para esta huelga.  
     * <p>
     * Relación de tipo OneToMany con CursoEtapaGrupoHuelga.
     * El atributo mappedBy = "huelga" indica que la relación
     * está mapeada desde la entidad CursoEtapaGrupoHuelga.
     * </p>
     */
	@OneToMany(mappedBy = "huelga")
	private List<CursoEtapaGrupoHuelga> cursos ;
}
