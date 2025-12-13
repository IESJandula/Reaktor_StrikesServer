package es.iesjandula.reaktor.strikes_server.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO utilizado para enviar información de un curso, etapa y grupo del alumno
 * desde el backend hacia el cliente.
 *
 * <p>Proporciona los datos principales de curso,
 * como su etapa y grupo representativo.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoEtapaGrupoResponseDto 
{
    /**
     * Número de curso.
     * Identifica de forma única el curso enviado en la respuesta.
     */
    private Integer curso;
    
    /**
     * Etapa de curso.
     * Identifica de forma única la etapa enviada en la respuesta.
     */
    private String etapa;
    
    /**
     * Ggrupo de curso.
     * Identifica de forma única el grupo enviado en la respuesta.
     */
    private String grupo;
}

