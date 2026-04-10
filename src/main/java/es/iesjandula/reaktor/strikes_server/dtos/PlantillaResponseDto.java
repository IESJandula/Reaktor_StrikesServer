package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.Data;

/**
 * DTO utilizado para enviar información que se obtiene de crear huelga
 * <p>
 * Proporciona el nombre del formulario, el id del formulario, el id de la hoja de cálculo de Google Sheet y el nombre de la hoja de cálculo.
 * </p>
 */
@Data
public class PlantillaResponseDto
{

	/**
	 * Url del formulario
	 */
    String googleFormUrl ; 
    
    /**
     * Id del formulario
     */
    String googleFormId ;
    
    /**
     * Id de la hoja de cálculo de Google Sheet
     */
    String googleSpreadsheetId ;
    
	/**
	 * Nombre de la hoja de cálculo
	 */
    String googleSheetName ;
}
