package es.iesjandula.reaktor.strikes_server.models.ids;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la clave primaria compuesta de la entidad Convoca.
 * 
 * <p>Esta clase se utiliza para identificar de manera única una huelga mediante
 * el email del alumno y el título de la huelga.</p>
 * 
 * <p>Al implementar Serializable, puede ser utilizada como clave embebida en JPA.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AlumnoHuelgaId implements Serializable 
{

    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */
	private static final long serialVersionUID = 7375364774028493903L ;
	
    /**
     * Email del alumno que se inscribe.
     * Forma parte de la clave primaria compuesta.
     */
	private String email ;
    /**
     * Título de la huelga.
     * Forma parte de la clave primaria compuesta.
     */
	private String nombre ;
}
