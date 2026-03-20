package es.iesjandula.reaktor.strikes_server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.iesjandula.reaktor.strikes_server.models.Alumno;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para consulta y validación de alumnos.
 */
@RestController
@RequestMapping("/alumnos")
@Slf4j
public class AlumnoRestController
{
    @Autowired
    private IAlumnoRepository alumnoRepository;

    /**
     * Obtener todos los alumnos
     */
    @GetMapping("/")
    public ResponseEntity<?> obtenerAlumnos()
    {
        try
        {
            return ResponseEntity.ok(alumnoRepository.findAll());
        }
        catch (Exception exception)
        {
            log.error("Error obteniendo alumnos", exception);
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    /**
     * Comprobar si un alumno existe en la base de datos
     */
    /**
     * Obtener alumno por nombre y apellidos
     */
    @GetMapping("/alumno/buscar")
    public ResponseEntity<?> buscarAlumnoPorNombreYApellidos( @RequestParam String nombre, @RequestParam String apellidos)
    {
        try
        {
            List<Alumno> alumnos = alumnoRepository.findByNombreIgnoreCaseAndApellidosIgnoreCase(nombre, apellidos);

            if (alumnos.isEmpty())
            {
                return ResponseEntity.ok("No se encontraron alumnos");
            }

            return ResponseEntity.ok(alumnos);
        }
        catch (Exception exception)
        {
            log.error("Error buscando alumno", exception);
            return ResponseEntity.status(500).body("Error interno");
        }
    }
    
    @GetMapping("/alumno/buscar-invertido")
    public ResponseEntity<?> buscarAlumnoPorApellidosYNombre( @RequestParam String apellidos, @RequestParam String nombre)
    {
        try
        {
            List<Alumno> alumnos = alumnoRepository.findByApellidosIgnoreCaseAndNombreIgnoreCase(apellidos, nombre);

            if (alumnos.isEmpty())
            {
                return ResponseEntity.ok("No se encontraron alumnos");
            }

            return ResponseEntity.ok(alumnos);
        }
        catch (Exception e)
        {
            log.error("Error buscando alumno", e);
            return ResponseEntity.status(500).body("Error interno");
        }
    }
}