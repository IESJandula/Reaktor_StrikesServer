package es.iesjandula.reaktor.strikes_server.models;

/**
 * Enum que representa los posibles estados de una huelga.
 */
public enum EstadoHuelga
{
    /**
     * La huelga ha sido convocada y está activa.
     */
    CONVOCADA("Convocada"),

    /**
     * La huelga ya ha finalizado.
     */
    FINALIZADA("Finalizada") ;
	
    private final String descripcion ;
	
	 EstadoHuelga(String descripcion) 
	 {
		 this.descripcion = descripcion ;
	 }

	    public String getDescripcion() 
	    {
	        return descripcion ;
	    }
}