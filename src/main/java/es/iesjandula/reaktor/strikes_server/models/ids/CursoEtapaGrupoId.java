package es.iesjandula.reaktor.strikes_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clave primaria compuesta para la entidad CursoEtapaGrupo.
 * 
 * Está formada por:
 * - curso
 * - etapa
 * - grupo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CursoEtapaGrupoId implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * Curso 
     */
    private String curso;

    /**
     * Etapa educativa 
     */
    private String etapa;

    /**
     * Grupo 
     */
    private String grupo;
}