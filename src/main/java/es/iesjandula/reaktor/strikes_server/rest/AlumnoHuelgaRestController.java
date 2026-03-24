package es.iesjandula.reaktor.strikes_server.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.strikes_server.dtos.AlumnoHuelgaRequestDto;
import es.iesjandula.reaktor.strikes_server.models.Alumno;
import es.iesjandula.reaktor.strikes_server.models.AlumnoHuelga;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoRepository;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.utils.Constants;
import es.iesjandula.reaktor.strikes_server.utils.StrikesServerException;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para operaciones relacionadas con los alumnos
 * y sus inscripciones en huelgas.
 */
@RestController
@RequestMapping("/strikes/alumnos")
@Slf4j
public class AlumnoHuelgaRestController
{
    @Autowired
    private IAlumnoRepository alumnoRepository;

    @Autowired
    private IHuelgaRepository huelgaRepository;

    @Autowired
    private IAlumnoHuelgaRepository alumnoHuelgaRepository;

    /**
     * Endpoint que registra la inscripción de un alumno en una huelga.
     * Este endpoint es llamado por el scheduler cuando detecta una nueva
     * respuesta en el Google Sheet.
     *
     * Recibe:
     *  - titulo   → título de la huelga
     *  - email    → email del alumno
     *  - decision → SI / NO (si secunda la huelga)
     */
    @PostMapping("/inscripcion")
    public ResponseEntity<?> registrarInscripcion(@RequestBody AlumnoHuelgaRequestDto alumnoHuelgaRequestDto)
    {
        try
        {
        	// 
            log.info("Procesando inscripción en huelga {} para alumno {} participa {}",
                    alumnoHuelgaRequestDto.getTitulo(), alumnoHuelgaRequestDto.getEmail(), alumnoHuelgaRequestDto.getParticipa());

            // Validamos el título y el email
            this.validarDatos(alumnoHuelgaRequestDto) ;

            // Validamos si existe la huelga y se encuentra convocada
            Huelga huelga = this.validarHuelgaExiste(alumnoHuelgaRequestDto) ;
            
            // Validamos si existe el alumno
            Alumno alumno = this.validarAlumnoExiste(alumnoHuelgaRequestDto) ;             

            // Creamos la clave primaria
            AlumnoHuelgaId alumnoHuelgaId = new AlumnoHuelgaId(alumnoHuelgaRequestDto.getEmail(), alumnoHuelgaRequestDto.getTitulo());

            // crear relación alumno-huelga
            AlumnoHuelga alumnoHuelga = new AlumnoHuelga();
            alumnoHuelga.setAlumnoHuelgaId(alumnoHuelgaId);
            alumnoHuelga.setAlumno(alumno);
            alumnoHuelga.setHuelga(huelga);

            this.alumnoHuelgaRepository.saveAndFlush(alumnoHuelga);

            log.info("Inscripción guardada correctamente");

            return ResponseEntity.ok().build();
        }
        catch (StrikesServerException exception)
        {
            return ResponseEntity.badRequest().body(exception.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
        	// Logueamos
            log.error(Constants.ERR_SERVIDOR, exception);
            
            // Creamos excepción con la traza del error
            StrikesServerException strikesServerException = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, exception);
            
            // Devolvemos el mapa con el código, mensaje y traza de excepción
            return ResponseEntity.status(500).body(strikesServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Validación básica de datos recibidos.
     */
    private void validarDatos(AlumnoHuelgaRequestDto alumnoHuelgaRequestDto) throws StrikesServerException
    {
        if (alumnoHuelgaRequestDto.getTitulo() == null || alumnoHuelgaRequestDto.getTitulo().isEmpty())
        {
        	log.error(Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC);
            throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE, Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC);
        }

        if (alumnoHuelgaRequestDto.getEmail() == null || alumnoHuelgaRequestDto.getEmail().isEmpty())
        {
        	log.error(Constants.ERR_ALUMNO_EMAIL_NULO_VACIO_DESC);
            throw new StrikesServerException(Constants.ERR_ALUMNO_EMAIL_NULO_VACIO_CODE, Constants.ERR_ALUMNO_EMAIL_NULO_VACIO_DESC);
        }

    }
    private Huelga validarHuelgaExiste(AlumnoHuelgaRequestDto alumnoHuelgaRequestDto) throws StrikesServerException
    {
    	// Validamos si la huelga existe
        Optional<Huelga> optionalHuelga = huelgaRepository.findById(alumnoHuelgaRequestDto.getTitulo()) ;
        
        if (optionalHuelga.isEmpty())
        {
        	log.error(Constants.ERR_HUELGA_NO_EXISTE_DESC) ;
        	throw new StrikesServerException(Constants.ERR_HUELGA_NO_EXISTE_CODE, Constants.ERR_HUELGA_NO_EXISTE_DESC) ;
        }
        
        // Si llegamos a este punto es porque existe la huelga
        Huelga huelga = optionalHuelga.get() ;
        
        
        if (huelga.getEstado() != EstadoHuelga.CONVOCADA)
        {
        	log.error(Constants.ERR_HUELGA_NO_ACTIVA_DESC);
            throw new StrikesServerException(Constants.ERR_HUELGA_NO_ACTIVA_CODE, Constants.ERR_HUELGA_NO_ACTIVA_DESC);
        }
        return huelga ;
    }
    
    private Alumno validarAlumnoExiste(AlumnoHuelgaRequestDto alumnoHuelgaRequestDto) throws StrikesServerException
    {
    	
    	//Validamos si el alumno existe
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(alumnoHuelgaRequestDto.getEmail()) ;
        
        if (optionalAlumno.isEmpty())
        {
        	log.error(Constants.ERR_ALUMNO_NO_EXISTE_DESC) ;
        	throw new StrikesServerException(Constants.ERR_ALUMNO_NO_EXISTE_CODE, Constants.ERR_ALUMNO_NO_EXISTE_DESC) ;
        }
        return optionalAlumno.get() ;
    }
    
    
}