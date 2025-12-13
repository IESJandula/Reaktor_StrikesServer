package es.iesjandula.reaktor.strikes_server.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa a un alumno en la base de datos.
 * Hereda de la clase Persona e incluye información específica 
 * como curso y grupo.
 * 
 * Esta clase está mapeada como entidad JPA a la tabla "alumno".
 * 
 * @author Mónica Luna
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="alumno")
public class Alumno 
{
	
    /**
     * Correo electrónico del alumno. Actúa como clave primaria de la entidad y debe ser único.
     */
	@Id
    @Column(length = 50, nullable = false)
    private String email ;


    /**
     * Nombre del alumno.
     */
    @Column(length = 50, nullable = false)
    private String nombre ;

    /**
     * Apellidos del alumno.
     */
    @Column(length = 100, nullable = false)
    private String apellidos ;
    
    /**
     * Curso, etapa y grupo al que pertenece el alumno.
     * Relación de tipo ManyToOne, donde varios alumnos
     * pueden pertenecer al mismo curso, etapa y grupo.
     */
    @ManyToOne
    @JoinColumn(name = "curso", referencedColumnName = "curso")
    @JoinColumn(name = "etapa", referencedColumnName = "etapa")
    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    private CursoEtapaGrupo cursoEtapaGrupo ;
}

