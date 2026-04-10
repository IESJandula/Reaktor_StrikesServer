package es.iesjandula.reaktor.strikes_server.rest;



import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.strikes_server.dtos.HuelgaRequestDto;
import es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto;
import es.iesjandula.reaktor.strikes_server.dtos.PlantillaResponseDto;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.utils.Constants;
import es.iesjandula.reaktor.strikes_server.utils.StrikesServerException;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para la gestión de huelgas.
 */
@RestController
@RequestMapping("/strikes/manager")
@Slf4j
public class HuelgaRestController
{
    @Autowired
    private IHuelgaRepository huelgaRepository;
    
	/** URL permitida de CORS */
	@Value("${reaktor.scriptCreacionConsultaHuelga}")
	private String scriptCreacionConsultaHuelga ;
	
	/** URL permitida de CORS */
	@Value("${reaktor.scriptBorradoHuelga}")
	private String scriptBorradoHuelga ;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> crearHuelga(@RequestBody HuelgaRequestDto huelgaRequestDto)
    {
        try
        {
        	// Validamos los datos introducidos
            this.validarCreacionHuelga(huelgaRequestDto) ;

            // Recogemos los datos del formulario
            PlantillaResponseDto plantillaResponseDto = this.llamarScript(huelgaRequestDto) ;
           
            // Creamos la entidad
            this.crearHuelga(huelgaRequestDto, plantillaResponseDto) ;
            
            return ResponseEntity.ok().build() ;
        }
        catch (StrikesServerException excepcion)
        {
            return ResponseEntity.badRequest().body(excepcion.getBodyExceptionMessage()) ;
        }
        catch (Exception excepcion)
        {
        	// Logueamos
            log.error(Constants.ERR_SERVIDOR, excepcion) ;
            
            // Creamos excepción con la traza del error
            StrikesServerException strikesServerException = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion) ;
            
            // Devolvemos el mapa con el código, mensaje y traza de excepción 
            return ResponseEntity.status(500).body(strikesServerException.getBodyExceptionMessage()) ;

        }
    }

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @DeleteMapping("/")
    public ResponseEntity<?> borrarHuelga(@RequestHeader("titulo") String titulo)
    {
        try
        {
            if (titulo == null || titulo.isEmpty())
            {
            	log.error(Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
                throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE, Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
            }


            Huelga huelga = this.huelgaRepository.findById(titulo).orElseThrow(() ->
            {
                log.error(Constants.ERR_HUELGA_NO_EXISTE_DESC);
                return new StrikesServerException(Constants.ERR_HUELGA_NO_EXISTE_CODE,Constants.ERR_HUELGA_NO_EXISTE_DESC);
            });
                   

            // Llamar a Apps Script para borrar Form y Sheet
            this.borrarRecursosGoogle(huelga);
            
            // Comprobar primero si hay alumnos inscritos a esta huelga para borrar solo de la tabla relación
            
           
            // Borramos la huelga
            this.huelgaRepository.delete(huelga);

            // Devolvemos la respuesta
            return ResponseEntity.ok().build();
        }
        catch (StrikesServerException exception)
        {
            return ResponseEntity.badRequest().body(exception.getBodyExceptionMessage());
        }
        catch (Exception excepcion)
        {
        	// Logueamos
            log.error(Constants.ERR_SERVIDOR, excepcion);
            
            // Creamos excepción con la traza del error
            StrikesServerException strikesServerException = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion) ;
            
            // Devolvemos el mapa con el código, mensaje y traza de excepción
            return ResponseEntity.status(500).body(strikesServerException.getBodyExceptionMessage()) ;
        } 
    }

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @GetMapping("/")
    public ResponseEntity<?> obtenerHuelgas(@PageableDefault(size = 5, sort = "titulo") Pageable pageable)
    {

        try
        {
            Page<HuelgaResponseDto> pageHuelgaResponseDto = this.huelgaRepository.obtenerHuelgas(pageable) ;

            return ResponseEntity.ok(pageHuelgaResponseDto) ;
        }
        catch (Exception excepcion)
        {
        	// Logueamos
            log.error(Constants.ERR_SERVIDOR, excepcion) ;
            
            // Creamos excepción con la traza del error
            StrikesServerException strikesServerException = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion) ;
            
            // Devolvemos el mapa con el código, mensaje y traza de excepción
            return ResponseEntity.status(500).body(strikesServerException.getBodyExceptionMessage()) ;
        }
        

    }
    
    

    /* =========================
       MÉTODOS AUXILIARES  
    ========================= */

    private Date date(Long fecha) throws StrikesServerException
    {
        if (fecha == null || fecha <= 0)
        {
        	log.error(Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC);
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHA_INICIO_NULA_CODE,Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC) ;
        }
        return new Date(fecha) ;
    }
   

    private void validarCreacionHuelga(HuelgaRequestDto huelgaRequestDto) throws StrikesServerException
    {
        if (huelgaRequestDto.getTitulo() == null || huelgaRequestDto.getTitulo().isEmpty())
        {
        	log.error(Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
        	throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE,Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
        }

        if (huelgaRequestDto.getFechaInicio() == null || huelgaRequestDto.getFechaInicio() <= 0)
        {
        	log.error(Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC) ;
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHA_INICIO_NULA_CODE,Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC) ;
        }

        if (huelgaRequestDto.getFechaFin() != null && huelgaRequestDto.getFechaFin() < huelgaRequestDto.getFechaInicio())
        {
        	log.error(Constants.ERR_HUELGA_FECHAS_INCOHERENTES_DESC) ;
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHAS_INCOHERENTES_CODE,Constants.ERR_HUELGA_FECHAS_INCOHERENTES_DESC) ;
        }
        
        if (this.huelgaRepository.existsById(huelgaRequestDto.getTitulo()))
        {
        	log.error(Constants.ERR_HUELGA_EXISTE_DESC);
            throw new StrikesServerException(Constants.ERR_HUELGA_EXISTE_CODE, Constants.ERR_HUELGA_EXISTE_DESC) ;
        }
    }
    
    private PlantillaResponseDto llamarScript(HuelgaRequestDto huelgaRequestDto) throws StrikesServerException
    {
	    try 
	    {
	    	 // Crear body
	        Map<String, Object> body = new HashMap<>() ;
	        body.put("titulo", huelgaRequestDto.getTitulo()) ;

	        // Convertir a JSON
	        ObjectMapper objectMapper = new ObjectMapper() ;
	        String json = objectMapper.writeValueAsString(body) ;

	        // Cliente HTTP con redirects
	        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build() ;

	        // Petición
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.scriptCreacionConsultaHuelga))
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(json))
	                .build() ;

	        // Ejecutar
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()) ;

	        String rawResponse = response.body() ;

	        log.info("Respuesta de Google Script: {}", rawResponse);

	        // Validación básica
	        if (rawResponse == null || rawResponse.isEmpty()) 
	        {
	            throw new StrikesServerException(Constants.ERR_HUELGA_NO_CREA_FORMULARIO_CODE, Constants.ERR_HUELGA_NO_CREA_FORMULARIO_DESC) ;
	        }

	        // Evitar parsear HTML
	        if (rawResponse.trim().startsWith("<")) 
	        {
	            throw new StrikesServerException( Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR) ;
	        }

	        // Convertir a DTO
	        PlantillaResponseDto responseDto = objectMapper.readValue(rawResponse, PlantillaResponseDto.class) ;

	        // Validación de negocio
	        if (responseDto.getGoogleFormUrl() == null) 
	        {
	            throw new StrikesServerException(Constants.ERR_HUELGA_NO_CREA_FORMULARIO_CODE, Constants.ERR_HUELGA_NO_CREA_FORMULARIO_DESC) ;
	        }
	        return responseDto;
	    } 
	    catch (StrikesServerException exception) 
	    {
	    	log.error(Constants.ERR_HUELGA_EXISTE_DESC) ;
	        throw new StrikesServerException(Constants.ERR_HUELGA_EXISTE_CODE, Constants.ERR_HUELGA_EXISTE_DESC) ;
	    } 
	    catch (Exception exception) 
	    {
	        log.error(Constants.ERR_SERVIDOR, exception) ;
	        throw new StrikesServerException( Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, exception);
	    }
		
    }
    

    private void crearHuelga(HuelgaRequestDto huelgaRequestDto, PlantillaResponseDto plantillaResponseDto) throws StrikesServerException
    {
    	try
    	{
    		// Convertimos el timestamp (Long) a Date para la fecha de inicio
    		Date fechaInicio = this.date(huelgaRequestDto.getFechaInicio());

    		// Convertimos el timestamp (Long) a Date para la fecha de fin
    		Date fechaFin = this.date(huelgaRequestDto.getFechaFin());

    		// Creamos la entidad Huelga que se persistirá en base de datos
    		Huelga huelga = new Huelga();

    		// Asignamos el título recibido en el DTO
    		huelga.setTitulo(huelgaRequestDto.getTitulo());

    		// Establecemos la fecha de inicio de inscripciones
    		huelga.setFechaInicio(fechaInicio);

    		// Establecemos la fecha de fin de la huelga
    		huelga.setFechaFin(fechaFin);

    		// Inicializamos el estado de la huelga como CONVOCADA
    		huelga.setEstado(EstadoHuelga.CONVOCADA);

    		// Guardamos la URL pública del formulario generado en Google
    		huelga.setUrlEncuestado(plantillaResponseDto.getGoogleFormUrl());

    		// Guardamos el ID del formulario de Google
    		huelga.setGoogleFormId(plantillaResponseDto.getGoogleFormId());

    		// Guardamos el ID del spreadsheet asociado al formulario
    		huelga.setGoogleSpreadsheetId(plantillaResponseDto.getGoogleSpreadsheetId());

    		// Guardamos el nombre de la hoja donde se almacenan las respuestas
    		huelga.setGoogleSheetName(plantillaResponseDto.getGoogleSheetName());

    		// Persistimos la entidad en base de datos y forzamos sincronización inmediata
    		this.huelgaRepository.saveAndFlush(huelga);
    	}
    	catch (StrikesServerException exception) 
    	{
    		log.error(Constants.ERR_HUELGA_NO_CREA_HUELGA_DESC) ;
    		throw new StrikesServerException(Constants.ERR_HUELGA_NO_CREA_HUELGA_CODE, Constants.ERR_HUELGA_NO_CREA_HUELGA_DESC) ;
		}
    	catch (Exception exception) 
    	{
    		log.error(Constants.ERR_HUELGA_NO_CREA_HUELGA_DESC) ;
    		throw new StrikesServerException(Constants.ERR_HUELGA_NO_CREA_HUELGA_CODE, Constants.ERR_HUELGA_NO_CREA_HUELGA_DESC) ;
		}
    }
    
    private void borrarRecursosGoogle(Huelga huelga) throws StrikesServerException
    {
        try
        {
            // Validamos que existan los recursos de Google asociados a la huelga
            if (huelga.getGoogleFormId() == null || huelga.getGoogleSpreadsheetId() == null)
            {
                // Log de error indicando que no hay recursos que eliminar
                log.error(Constants.ERR_HUELGA_NO_ACTIVA_DESC);
                throw new StrikesServerException(Constants.ERR_HUELGA_NO_ACTIVA_CODE,Constants.ERR_HUELGA_NO_ACTIVA_DESC);
            }

            // Creamos el body de la petición que se enviará al Google Apps Script
            Map<String, Object> body = new HashMap<>() ;

            // Token de seguridad (para evitar llamadas no autorizadas)
            body.put("token", "MI_TOKEN_SUPER_SEGURO") ;

            // ID del formulario de Google a eliminar
            body.put("formId", huelga.getGoogleFormId()) ;

            // ID del spreadsheet asociado al formulario
            body.put("spreadsheetId", huelga.getGoogleSpreadsheetId()) ;

            // Construimos y ejecutamos la llamada HTTP al Google Script
            String rawResponse = webClientBuilder.build()

                // Indicamos que la petición será POST
                .post()

                // URL del script
                .uri(this.scriptBorradoHuelga)

                // Indicamos que enviamos JSON
                .contentType(MediaType.APPLICATION_JSON)

                // Enviamos el body con los datos necesarios
                .bodyValue(body)

                // Ejecutamos la petición HTTP
                .retrieve()

                // Convertimos la respuesta del servidor a String
                .bodyToMono(String.class)

                // Bloqueamos la ejecución hasta obtener respuesta (modo síncrono)
                .block();

            log.info("Recursos Google eliminados para la huelga {}", rawResponse) ;
            if (rawResponse == null || rawResponse.isEmpty() || rawResponse.trim().startsWith("<"))
            {
            	log.error(Constants.ERR_SERVIDOR) ;
                throw new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR) ;
            }
        }
        catch (StrikesServerException exception)
        {
        	log.error(Constants.ERR_HUELGA_NO_ACTIVA_DESC) ;
            throw new StrikesServerException(Constants.ERR_HUELGA_NO_ACTIVA_CODE, Constants.ERR_HUELGA_NO_ACTIVA_DESC) ;
        }
        catch (Exception exception)
        {
            log.error(Constants.ERR_SERVIDOR, exception) ;
            throw new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, exception) ;
        }
    }
    
}
