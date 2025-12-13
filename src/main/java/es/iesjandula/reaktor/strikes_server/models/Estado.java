package es.iesjandula.reaktor.strikes_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="estado")

/**
 * Representa un estado dentro del sistema.
 * <p>
 * Esta clase está mapeada como una entidad JPA a la tabla estado.
 * Contiene únicamente un nombre que identifica de manera única cada estado.
 * </p>
 * <p>
 * Se utiliza principalmente para definir estados de otras entidades
 * o para clasificaciones dentro del sistema.
 * </p>
 */
public class Estado
{

    /**
     * Nombre del estado.
     * <p>
     * Actúa como clave primaria de la tabla. Tiene una longitud máxima de 25 caracteres.
     * </p>
     */
	@Id
	@Column(length = 25)
	private String nombre ;
}
