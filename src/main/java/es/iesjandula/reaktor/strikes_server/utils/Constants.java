package es.iesjandula.reaktor.strikes_server.utils;

/**
 * Clase de constantes utilizadas en el proyecto Huelgas.
 * 
 * <p>
 * Contiene mensajes de éxito y códigos/mensajes de error para Huelga,
 * Alumno, CursoEtapaGrupo y errores generales del servidor.
 * </p>
 * 
 * <p>
 * Estas constantes se utilizan en controladores, servicios y excepciones para
 * mantener consistencia en los mensajes y códigos de error en todo el proyecto.
 * </p>
 */
public class Constants
{

    // ========================================
    // Mensajes generales
    // ========================================

    /** Mensaje al agregar un elemento correctamente */
    public static final String ELEMENTO_AGREGADO = "Elemento agregado correctamente.";

    /** Mensaje al modificar un elemento correctamente */
    public static final String ELEMENTO_MODIFICADO = "Elemento modificado correctamente.";

    /** Mensaje al eliminar un elemento correctamente */
    public static final String ELEMENTO_ELIMINADO = "Elemento eliminado correctamente.";

    /** Mensaje al mostrar un elemento correctamente */
    public static final String ELEMENTO_MOSTRADO = "Elemento mostrado correctamente.";

    // ========================================
    // Errores de Huelga
    // ========================================

    /** Código de error genérico para Huelga */
    public static final Integer ERR_HUELGA_CODE = 1;
    public static final String ERR_HUELGA_DESC = "HUELGA_ERROR";

    /** Código cuando el título de la huelga es nulo o vacío */
    public static final Integer ERR_HUELGA_TITULO_NULO_VACIO_CODE = 2;
    public static final String ERR_HUELGA_TITULO_NULO_VACIO_DESC = "El título de la huelga no puede ser nulo ni vacío.";

    /** Código cuando la huelga ya existe */
    public static final Integer ERR_HUELGA_EXISTE_CODE = 3;
    public static final String ERR_HUELGA_EXISTE_DESC = "La huelga ya existe en el sistema.";

    /** Código cuando la huelga no existe */
    public static final Integer ERR_HUELGA_NO_EXISTE_CODE = 4;
    public static final String ERR_HUELGA_NO_EXISTE_DESC = "La huelga no existe en el sistema.";

    /** Código cuando la fecha de inicio es nula o inválida */
    public static final Integer ERR_HUELGA_FECHA_INICIO_NULA_CODE = 5;
    public static final String ERR_HUELGA_FECHA_INICIO_NULA_DESC = "La fecha de inicio de la huelga no puede ser nula o inválida.";

    /** Código cuando las fechas de la huelga son incoherentes */
    public static final Integer ERR_HUELGA_FECHAS_INCOHERENTES_CODE = 6;
    public static final String ERR_HUELGA_FECHAS_INCOHERENTES_DESC = "La fecha de fin no puede ser anterior a la fecha de inicio.";
    
    /** Código cuando la huelga no está activa */
	public static final Integer ERR_HUELGA_NO_ACTIVA_CODE = 16;
	public static final String ERR_HUELGA_NO_ACTIVA_DESC = "La huelga no está abierta a inscripciones";

    // ========================================
    // Errores de Alumno
    // ========================================

    /** Código de error genérico para Alumno */
    public static final Integer ERR_ALUMNO_CODE = 7;
    public static final String ERR_ALUMNO_DESC = "ALUMNO_ERROR";

    /** Código cuando el email del alumno es nulo o vacío */
    public static final Integer ERR_ALUMNO_EMAIL_NULO_VACIO_CODE = 8;
    public static final String ERR_ALUMNO_EMAIL_NULO_VACIO_DESC = "El email del alumno no puede ser nulo ni vacío.";

    /** Código cuando el alumno ya existe */
    public static final Integer ERR_ALUMNO_EXISTE_CODE = 9;
    public static final String ERR_ALUMNO_EXISTE_DESC = "El alumno ya existe en el sistema.";

    
    /** Código cuando el alumno no existe */
    public static final Integer ERR_ALUMNO_NO_EXISTE_CODE = 10;
    public static final String ERR_ALUMNO_NO_EXISTE_DESC = "El alumno no existe en el sistema.";

    // ========================================
    // Errores de CursoEtapaGrupo
    // ========================================

    /** Código de error genérico para CursoEtapaGrupo */
    public static final Integer ERR_CURSOETAPAGRUPO_CODE = 11;
    public static final String ERR_CURSOETAPAGRUPO_DESC = "CURSO_ETAPA_GRUPO_ERROR";

    /** Código cuando el CursoEtapaGrupo es nulo */
    public static final Integer ERR_CURSOETAPAGRUPO_NULO_CODE = 12;
    public static final String ERR_CURSOETAPAGRUPO_NULO_DESC = "El CursoEtapaGrupo no puede ser nulo.";

    /** Código cuando el CursoEtapaGrupo ya existe */
    public static final Integer ERR_CURSOETAPAGRUPO_EXISTE_CODE = 13;
    public static final String ERR_CURSOETAPAGRUPO_EXISTE_DESC = "El CursoEtapaGrupo ya existe en el sistema.";

    /** Código cuando el CursoEtapaGrupo no existe */
    public static final Integer ERR_CURSOETAPAGRUPO_NO_EXISTE_CODE = 14;
    public static final String ERR_CURSOETAPAGRUPO_NO_EXISTE_DESC = "El CursoEtapaGrupo no existe en el sistema.";

    // ========================================
    // Error de servidor
    // ========================================

    /** Código para errores generales del servidor */
    public static final Integer ERR_SERVIDOR_CODE = 15;
    public static final String ERR_SERVIDOR = "Se ha producido un error interno en el servidor.";


}