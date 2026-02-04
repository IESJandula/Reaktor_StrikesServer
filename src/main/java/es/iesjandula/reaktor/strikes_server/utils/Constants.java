package es.iesjandula.reaktor.strikes_server.utils;

/**
 * Clase de constantes utilizadas en el proyecto Huelgas.
 * 
 * <p>Contiene mensajes de éxito y códigos/mensajes de error para Huelga, Alumno, 
 * CursoEtapaGrupo, Estado, Convoca y errores generales del servidor.</p>
 * 
 * <p>Estas constantes se utilizan en controladores, servicios y excepciones para
 * mantener consistencia en los mensajes y códigos de error en todo el proyecto.</p>
 */
public class Constants
{
    // --- Mensajes generales ---
	/** Mensaje al agregar un elemento correctamente */
    public static final String ELEMENTO_AGREGADO = "Elemento agregado correctamente." ;
    
    /** Mensaje al modificar un elemento correctamente */
    public static final String ELEMENTO_MODIFICADO = "Elemento modificado correctamente." ;
    
    /** Mensaje al eliminar un elemento correctamente */
    public static final String ELEMENTO_ELIMINADO = "Elemento eliminado correctamente." ;
    
    /** Mensaje al mostrar un elemento correctamente */
    public static final String ELEMENTO_MOSTRADO = "Elemento mostrado correctamente." ;


    // --- Errores de Huelga ---
    /** Código de error genérico para Huelga */
    public static final Integer ERR_HUELGA_CODE = 5 ;
    public static final String ERR_HUELGA_DESC = "HUELGA_ERROR" ;
    
    /** Código y mensaje cuando el título de la huelga es nulo o vacío */
    public static final Integer ERR_HUELGA_TITULO_NULO_VACIO_CODE = 6 ;
    public static final String ERR_HUELGA_TITULO_NULO_VACIO_DESC = "El título de la huelga no puede ser nulo ni vacío." ;
    
    /** Código y mensaje cuando la huelga ya existe */
    public static final Integer ERR_HUELGA_EXISTE_CODE = 7 ;
    public static final String ERR_HUELGA_EXISTE_DESC = "La huelga ya existe en el sistema." ;
    
    /** Código y mensaje cuando la huelga no existe */
    public static final Integer ERR_HUELGA_NO_EXISTE_CODE = 8 ;
    public static final String ERR_HUELGA_NO_EXISTE_DESC = "La huelga no existe en el sistema." ;
    
    /** Código y mensaje cuando la huelga no existe */
    public static final Integer ERR_HUELGA_FECHA_INICIO_NULA_CODE = 8 ;
    public static final String ERR_HUELGA_FECHA_INICIO_NULA_DESC = "La fecha de inicio no puede ser nula o vacia." ;

    /** Código y mensaje cuando el estado de la huelga no existe */
    public static final Integer ERR_HUELGA_ESTADO_NO_EXISTE_CODE = 21 ;
    public static final String ERR_HUELGA_ESTADO_NO_EXISTE_DESC = "El estado de la huelga no existe" ;
    
    /** Código y mensaje cuando el estado de la huelga es nulo */
    public static final Integer ERR_HUELGA_ESTADO_NULO_CODE = 22 ;
    public static final String ERR_HUELGA_ESTADO_NULO_DESC = "Estado nulo" ;
    
    /** Código y mensaje cuando la fecha de inicio de la huelga es inferior a la fecha fin */
    public static final Integer ERR_HUELGA_FECHAS_INCOHERENTES_CODE = 23 ;
    public static final String ERR_HUELGA_FECHAS_INCOHERENTES_DESC = "La fecha de fin no puede ser anterior a la fecha de inicio." ;
    
    /** Código y mensaje cuando la fecha límite de la huelga no se ha insertado */
    public static final Integer ERR_HUELGA_FECHA_LIMITE_NO_EXISTE_CODE = 24 ;
    public static final String ERR_HUELGA_FECHA_LIMITE_NO_EXISTE_DESC = "La fecha límite de inscripción debe ser insertada." ;
    // --- Errores de Alumno ---
    /** Código de error genérico para Alumno*/
    public static final Integer ERR_ALUMNO_CODE = 9 ;
    public static final String ERR_ALUMNO_DESC = "ALUMNO_ERROR" ;
    
    /** Código y mensaje cuando el email del alumno es nulo o vacío */
    public static final Integer ERR_ALUMNO_EMAIL_NULO_VACIO_CODE = 10 ;
    public static final String ERR_ALUMNO_EMAIL_NULO_VACIO = "El email del alumno no puede ser nulo ni vacío." ;
    
    /** Código y mensaje cuando el alumno ya existe */
    public static final Integer ERR_ALUMNO_EXISTE_CODE = 11 ;
    public static final String ERR_ALUMNO_EXISTE_DESC = "El alumno ya existe en el sistema." ;
    
    /** Código y mensaje cuando el evento no existe */
    public static final Integer ERR_ALUMNO_NO_EXISTE_CODE = 13 ;
    public static final String ERR_AWLUMNO_NO_EXISTE_DESC = "El alumno no existe en el sistema." ;
 
    // --- Errores de CursoEtapaGrupo ---
    /** Código de error genérico para CursoEtapaGrupo */
    public static final Integer ERR_CURSOETAPAGRUPO_CODE = 14 ;
    public static final String ERR_CURSOETAPAGRUPO_DESC = "CURSOETAPAGRUPO_ERROR" ;
    
    /** Código y mensaje cuando EL CursoEtapaGrupo es nulo */
    public static final Integer ERR_CURSOETAPAGRUPO_NULO_CODE = 15 ;
    public static final String ERR_CURSOETAPAGRUPO_NULO_DESC = "El CursoEtapaGrupo no puede ser nulo." ;
    
    /** Código y mensaje cuando el CursoEtapaGrupo no está asociado a una huelga */
    public static final Integer ERR_CURSOETAPAGRUPO_EVENTO_NULO_CODE = 16 ;
    public static final String ERR_CURSOETAPAGRUPO_EVENTO_NULO = "El CursoEtapaGrupo debe estar asociado a un evento." ;
    
    /** Código y mensaje cuando el CursoEtapaGrupo ya existe */
    public static final Integer ERR_CURSOETAPAGRUPO_EXISTE_CODE = 17 ;
    public static final String ERR_RECORDATORIO_EXISTE = "El CursoEtapaGrupo ya existe en el sistema." ;
    
    /** Código y mensaje cuando el CursoEtapaGrupo no existe */
    public static final Integer ERR_CURSOETAPAGRUPO_NO_EXISTE_CODE = 18 ;
    public static final String ERR_CURSOETAPAGRUPO_NO_EXISTE = "El CursoEtapaGrupono existe en el sistema." ;
    
    

 	// --- Error de Servidor---
    /** Código y mensaje para errores generales del servidor */
    public static final Integer ERR_SERVIDOR_CODE = 20 ;
    public static final String ERR_SERVIDOR = "Error de servidor." ;

	
}
