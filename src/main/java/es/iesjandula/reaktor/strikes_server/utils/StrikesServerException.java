package es.iesjandula.reaktor.strikes_server.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Excepción personalizada para el sistema de calendario.
 * 
 * <p>Permite manejar errores con un código y mensaje específicos,
 * y opcionalmente almacenar la excepción original.</p>
 */
public class StrikesServerException extends Exception
{

	private static final long serialVersionUID = -9080070553502409936L ;
	
	/** Código de error asociado a la excepción */
	private Integer codigo ;
	
	/** Mensaje de error asociado a la excepción */
	private String mensaje ;
	

    /** Excepción original (opcional) */
	private Throwable excepcion ;
	
    /**
     * Constructor principal sin excepción original.
     * 
     * @param codigo Código de error
     * @param mensaje Mensaje de error
     */
	public StrikesServerException(Integer codigo, String mensaje)
	{
		super() ;
		
		this.codigo=codigo ;
		this.mensaje= mensaje ;
	}
	
    /**
     * Constructor con excepción original.
     * 
     * @param codigo Código de error
     * @param mensaje Mensaje de error
     * @param excepcion Excepción original asociada
     */
	public StrikesServerException(Integer movieErrorId, String message, Throwable excepcion)
	{
		super(message, excepcion) ;
		
		this.codigo=movieErrorId ;
		this.mensaje= message ;
		this.excepcion= excepcion ;
	}
	
	 /**
     * Construye un cuerpo de respuesta para enviar al cliente con detalles de la excepción.
     * 
     * <p>Incluye el código de error, mensaje y, si existe, el stack trace de la excepción original.</p>
     * 
     * @return Mapa con los datos de la excepción listo para serializar en JSON
     */
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("codigo", this.codigo) ;
		mapBodyException.put("message", this.mensaje) ;
		
		if (this.excepcion != null)
		{
			String stackTrace = ExceptionUtils.getStackTrace(this.excepcion) ;
			mapBodyException.put("excepcion", stackTrace) ;
		}
		
		return mapBodyException ;
	}

    /**
     * Devuelve el código de error de la excepción.
     * 
     * @return Código de error
     */
	public Integer getCodigo()
	{
		return this.codigo ;
	}	
}
