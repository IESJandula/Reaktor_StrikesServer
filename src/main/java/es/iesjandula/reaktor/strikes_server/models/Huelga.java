package es.iesjandula.reaktor.strikes_server.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="huelga")

/**
 * Representa una huelga dentro del sistema.
 *
 * <p>
 * Esta clase está mapeada como una entidad JPA a la tabla huelga.
 * Contiene información sobre la huelga, incluyendo fechas,
 * estado, enlaces de integración con Google Forms y los
 * alumnos convocados.
 * <p>
 * El sistema utiliza Google Apps Script para:
 * </p>
 * <ul>
 *   <li>Generar automáticamente un formulario de inscripción.</li>
 *   <li>Crear un Spreadsheet donde se almacenan las respuestas.</li>
 *   <li>Procesar las respuestas de forma incremental mediante un scheduler.</li>
 * </ul>
 */
public class Huelga
{

    /**
     * Título de la huelga.
     */
    @Id
    @Column(length = 100, nullable = false)
    private String titulo ;

    /**
     * Fecha de inicio de la huelga.
     */
    @Column(nullable = false)
    private Date fechaInicio ;

    /**
     * Fecha de finalización de la huelga.
     */
    @Column(nullable = false)
    private Date fechaFin ;

    /**
     * Identificador del formulario de Google Forms asociado
     * a la convocatoria de huelga.
     */
    @Column(length = 500)
    private String googleFormId ;

    /**
     * Nombre de la hoja dentro del Google Spreadsheet donde
     * se almacenan las respuestas del formulario.
     */
    @Column(length = 500)
    private String googleSheetName ;

    /**
     * Identificador del Google Spreadsheet donde se almacenan
     * las respuestas del formulario.
     */
    @Column(length = 500)
    private String googleSpreadsheetId ;

    /**
     * URL del formulario que los alumnos deben rellenar
     * para indicar si secundan o no la huelga.
     */
    @Column(length = 500)
    private String urlEncuestado ;

    /**
     * URL del Google Apps Script que permite consultar
     * las respuestas almacenadas en el Google Spreadsheet.
     */
    @Column(length = 500)
    private String urlGoogleScript ;

    /**
     * Estado actual de la huelga.
     */
    @Enumerated(EnumType.STRING)
    @Column
    private EstadoHuelga estado ;

    /**
     * Lista de alumnos que han respondido a la huelga.
     */
    @OneToMany(mappedBy = "huelga", fetch = FetchType.LAZY)
    private List<AlumnoHuelga> alumnos ;

    /**
     * Lista de cursos que pueden participar en la huelga.
     */
    @OneToMany(mappedBy = "huelga", fetch = FetchType.LAZY)
    private List<CursoEtapaGrupoHuelga> cursos ;
    
}