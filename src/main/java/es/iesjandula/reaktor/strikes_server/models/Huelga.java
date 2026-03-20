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
<<<<<<< Updated upstream
 * Esta clase está mapeada como una entidad JPA a la tabla huelga.
 * Contiene información sobre la huelga, incluyendo fechas,
 * estado, enlaces de integración con Google Forms y los
 * alumnos convocados.
=======
 * Esta clase está mapeada como una entidad JPA a la tabla <b>huelga</b>.
 * Contiene toda la información necesaria para gestionar una convocatoria
 * de huelga, incluyendo:
>>>>>>> Stashed changes
 * </p>
 *
 * <ul>
<<<<<<< Updated upstream
 *   <li>OneToMany con AlumnoHuelga: alumnos que han respondido a la huelga.</li>
 *   <li>OneToMany con CursoEtapaGrupoHuelga: cursos que pueden participar en la huelga.</li>
=======
 *   <li>Fechas de inicio, fin y límite de inscripción.</li>
 *   <li>Estado actual de la huelga.</li>
 *   <li>Relaciones con alumnos y cursos.</li>
 *   <li>Datos técnicos necesarios para la integración con Google Forms y Google Sheets.</li>
>>>>>>> Stashed changes
 * </ul>
 *
 * <p>
 * El sistema utiliza Google Apps Script para:
 * </p>
 *
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
<<<<<<< Updated upstream
     */
    @Id
    @Column(length = 25, nullable = false)
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
     * Última fila procesada del Google Sheet.
     */
    @Column
    private Integer ultimaFilaProcesada = 0 ;

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
=======
     *
     * <p>
     * Actúa como clave primaria de la entidad.
     * Longitud máxima de 25 caracteres.
     * </p>
     */
    @Id
    @Column(length = 25)
    private String titulo;

    /**
     * Fecha de inicio de la huelga.
     *
     * <p>
     * Campo obligatorio.
     * </p>
     */
    @Column(nullable = false)
    private Date fechaInicio;

    /**
     * Fecha de fin de la huelga.
     *
     * <p>
     * Campo obligatorio.
     * Cuando esta fecha es superada, el scheduler
     * cambia automáticamente el estado a FINALIZADA.
     * </p>
     */
    @Column(nullable = false)
    private Date fechaFin;


    /**
     * URL del Web App de Google Apps Script.
     *
     * <p>
     * Se utiliza para comunicarse con el script que:
     * </p>
     * <ul>
     *   <li>Crea el formulario.</li>
     *   <li>Lee las respuestas.</li>
     *   <li>Elimina recursos cuando la huelga finaliza.</li>
     * </ul>
     */
    @Column
    private String urlGoogleScript;

    /**
     * URL pública del formulario de inscripción.
     *
     * <p>
     * Es la URL que se muestra en el frontend para que
     * los alumnos puedan inscribirse en la huelga.
     * </p>
     */
    @Column
    private String urlEncuestado;

    /**
     * ID del Google Form generado automáticamente.
     *
     * <p>
     * Se utiliza principalmente para poder eliminar
     * el formulario cuando la huelga finaliza.
     * </p>
     */
    @Column
    private String googleFormId;

    /**
     * ID del Google Spreadsheet donde se almacenan
     * las respuestas del formulario.
     *
     * <p>
     * Este identificador es necesario para que el scheduler
     * pueda consultar las inscripciones realizadas.
     * </p>
     */
    @Column
    private String googleSpreadsheetId;

    /**
     * Nombre de la hoja (pestaña) dentro del Spreadsheet
     * donde se guardan las respuestas.
     *
     * <p>
     * El scheduler utiliza este valor junto con el ID del
     * Spreadsheet para acceder correctamente a los datos.
     * </p>
     */
    @Column
    private String googleSheetName;

    /**
     * Control incremental del scheduler.
     *
     * <p>
     * Indica la última fila procesada del Spreadsheet.
     * </p>
     *
     * <p>
     * Permite procesar únicamente nuevas respuestas
     * sin volver a recorrer todas las anteriores.
     * </p>
     */
    @Column
    private int ultimaFilaProcesada = 0;

    /**
     * Estado actual de la huelga.
     *
     * <p>
     * Se almacena como texto en la base de datos.
     * Ejemplos:
     * CONVOCADA, FINALIZADA, CANCELADA...
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoHuelga estado;

    /**
     * Lista de alumnos inscritos en la huelga.
     *
     * <p>
     * Relación OneToMany con la entidad AlumnoHuelga.
     * </p>
     */
    @OneToMany(mappedBy = "huelga")
    private List<AlumnoHuelga> alumnos;

    /**
     * Lista de cursos que pueden participar en la huelga.
     *
     * <p>
     * Relación OneToMany con CursoEtapaGrupoHuelga.
     * </p>
     */
    @OneToMany(mappedBy = "huelga")
    private List<CursoEtapaGrupoHuelga> cursos;
>>>>>>> Stashed changes
}