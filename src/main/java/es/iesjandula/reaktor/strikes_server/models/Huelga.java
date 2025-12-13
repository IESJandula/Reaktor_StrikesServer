package es.iesjandula.reaktor.strikes_server.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 *   <li>OneToMany con Convoca: lista de alumnos convocados.</li>
 *   <li>ManyToOne con Estado: estado actual de la huelga.</li>
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
	private String titulo ; 
	
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
     * <p>
     * Este campo es opcional, puede ser nulo si la huelga aún está en curso.
     * </p>
     */
	@Column
	private Date fechaFin ;
	
	/**
     * Lista de convocatorias de alumnos para esta huelga.
     * <p>
     * Relación de tipo OneToMany con Convoca.
     * El atributo mappedBy = "huelga" indica que la relación
     * está mapeada desde la entidad Convoca.
     * </p>
     */
	@OneToMany(mappedBy = "huelga")
	private List<Convoca> convocas ;
	
    /**
     * Estado actual de la huelga.
     * <p>
     * Relación de tipo ManyToOne con Estado.
     * Indica en qué estado se encuentra la huelga (por ejemplo, planificada, en curso, finalizada).
     * </p>
     */
	@ManyToOne
	@JoinColumn(name = "estado")
	private Estado estado ;
}
