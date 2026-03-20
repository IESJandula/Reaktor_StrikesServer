package es.iesjandula.reaktor.strikes_server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.strikes_server.models.Alumno;
import es.iesjandula.reaktor.strikes_server.models.AlumnoHuelga;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoRepository;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.utils.Constants;
import es.iesjandula.reaktor.strikes_server.utils.StrikesServerException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
    public ResponseEntity<?> registrarInscripcion(@RequestBody Map<String,String> body)
    {
        try
        {
            String titulo = body.get("titulo");
            String email = body.get("email").toLowerCase().trim();
            String decision = body.get("decision");

            log.info("Procesando inscripción en huelga {} para alumno {} decisión {}",
                    titulo, email, decision);

            validarDatos(titulo, email);

            Huelga huelga = huelgaRepository.findById(titulo).orElseThrow(() ->
                            new StrikesServerException(Constants.ERR_HUELGA_NO_EXISTE_CODE, Constants.ERR_HUELGA_NO_EXISTE_DESC));
            if (huelga.getEstado() != EstadoHuelga.CONVOCADA)
            {
                throw new StrikesServerException(Constants.ERR_HUELGA_NO_ACTIVA_CODE, Constants.ERR_HUELGA_NO_ACTIVA_DESC);
            }

            Alumno alumno = alumnoRepository.findById(email).orElseThrow(() ->
                            new StrikesServerException(Constants.ERR_ALUMNO_NO_EXISTE_CODE, Constants.ERR_ALUMNO_NO_EXISTE_DESC));

            // comprobar duplicado
            AlumnoHuelgaId id = new AlumnoHuelgaId(email, titulo);

            if (alumnoHuelgaRepository.existsById(id))
            {
                log.info("El alumno {} ya estaba registrado en la huelga {}", email, titulo);
                return ResponseEntity.ok("El alumno ya estaba registrado en la huelga");
            }

            // crear relación alumno-huelga
            AlumnoHuelga alumnoHuelga = new AlumnoHuelga();
            alumnoHuelga.setAlumnoHuelgaId(id);
            alumnoHuelga.setAlumno(alumno);
            alumnoHuelga.setHuelga(huelga);

            alumnoHuelgaRepository.save(alumnoHuelga);

            log.info("Inscripción guardada correctamente");

            return ResponseEntity.ok("Inscripción registrada correctamente");
        }
        catch (StrikesServerException exception)
        {
            return ResponseEntity.badRequest()
                    .body(exception.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            log.error("Error registrando inscripción", exception);

            return ResponseEntity.status(500).body("Error interno registrando inscripción");
        }
    }

    /**
     * Validación básica de datos recibidos.
     */
    private void validarDatos(String titulo, String email) throws StrikesServerException
    {
        if (titulo == null || titulo.isEmpty())
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE, Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC);
        }

        if (email == null || email.isEmpty())
        {
            throw new StrikesServerException(Constants.ERR_ALUMNO_EMAIL_NULO_VACIO_CODE, Constants.ERR_ALUMNO_EMAIL_NULO_VACIO_DESC);
        }
    }
}