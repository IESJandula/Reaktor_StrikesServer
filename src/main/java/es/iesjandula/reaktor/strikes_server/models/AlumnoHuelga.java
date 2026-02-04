package es.iesjandula.reaktor.strikes_server.models;

import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "alumno_huelga")

/**
 * Entidad que representa la relación de convocatoria
 * entre un alumno y una huelga.
 * <p>
 * Esta tabla actúa como una entidad intermedia que permite
 * modelar una relación muchos-a-muchos entre Alumno
 * y Huelga, utilizando una clave primaria compuesta.
 * </p>
 * 
 * La clave primaria está formada por el identificador del alumno
 * y el identificador de la huelga, definidos en ConvocaId.
 */
public class AlumnoHuelga
{

    /**
     * Clave primaria compuesta de la entidad. Está formada por el email del alumno y el título de la huelga.
     */
    @EmbeddedId
    private AlumnoHuelgaId alumnoHuelgaId ;

    /**
     * Alumno que convoca o participa en la huelga.
     * <p>
     * Relación de tipo ManyToOne con Alumno.
     * El atributo MapsId("email") indica que este campo
     * forma parte de la clave primaria compuesta.
     * </p>
     */
    @ManyToOne
    @MapsId("email") 
    @JoinColumn(name = "email", nullable = false)
    private Alumno alumno ;

    /**
     * Huelga convocada.
     * <p>
     * Relación de tipo code ManyToOne con Huelga.
     * El atributo MapsId("titulo") indica que este campo
     * forma parte de la clave primaria compuesta.
     * </p>
     */
    @ManyToOne
    @MapsId("nombre") // 
    @JoinColumn(name = "nombre", nullable = false)
    private Huelga huelga ;
}

