package es.iesjandula.reaktor.strikes_server.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.strikes_server.models.Alumno;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoRepository;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.services.AlumnoHuelgaService;
import es.iesjandula.reaktor.strikes_server.services.GoogleScriptService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HuelgaScheduler
{
    @Autowired
    private IHuelgaRepository huelgaRepository ;
    
    @Autowired
    private IAlumnoRepository alumnoRepository;
    
    @Autowired
    private AlumnoHuelgaService alumnoHuelgaService ;

    @Autowired
    private GoogleScriptService googleScriptService ;

    /**
     * Se ejecuta cada hora
     */
    //@Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 */1 * * * *")
    public void actualizarEstadosHuelga()
    {
        log.info("Iniciando scheduler de huelgas...") ;

        Date hoy = new Date() ;
        // Buscamos las huelgas que solo estan Convocadas
        List<Huelga> huelgas = huelgaRepository.findByEstado(EstadoHuelga.CONVOCADA) ;

        for (Huelga huelga : huelgas)
        {
            try
            {
                actualizarEstado(huelga, hoy) ;
                if (huelga.getEstado() == EstadoHuelga.CONVOCADA)
                {
                    procesarRespuestas(huelga) ;
                }
            }
            catch (Exception exception)
            {
                log.error("Error procesando huelga ID " + huelga.getTitulo()+ " : " + exception.getMessage()) ;
            }
        }
        huelgaRepository.saveAll(huelgas) ;
        log.info("Scheduler finalizado.") ;
    }

    // Actualizar estado si ha finalizado
    private void actualizarEstado(Huelga huelga, Date hoy)
    {
        if (huelga.getFechaFin() != null &&huelga.getFechaFin().before(hoy) && huelga.getEstado() == EstadoHuelga.CONVOCADA)
        {
            huelga.setEstado(EstadoHuelga.FINALIZADA) ;
            log.info("Huelga " + huelga.getTitulo() + "finalizada automáticamente") ;
        }
    }

    // Procesar respuestas nuevas
    private void procesarRespuestas(Huelga huelga)
    {
        if (huelga.getGoogleSpreadsheetId() == null || huelga.getGoogleSheetName() == null)
        {
            log.info("Huelga {} sin recursos Google configurados", huelga.getTitulo()) ;
            return ;
        }

        List<Map<String, Object>> respuestas = googleScriptService.obtenerRespuestas(huelga.getGoogleSpreadsheetId(), huelga.getGoogleSheetName()) ;
        log.info("Procesando {} respuestas para huelga {}", respuestas.size(), huelga.getTitulo()) ;

        for (Map<String, Object> respuesta : respuestas)
        {
            if (respuesta.get("Dirección de correo electrónico") == null)
            {
                continue ;
            }

            String email = ((String) respuesta.get("Dirección de correo electrónico")).toLowerCase().trim() ;
            String decision = (String) respuesta.get("¿Te inscribes en la huelga?") ;
            boolean participa = parseDecision(decision) ;
            Alumno alumno = alumnoRepository.findById(email).orElse(null) ;
            if (alumno == null)
            {
                log.error("Alumno no encontrado en BD: {}", email);
                continue ;
            }
            log.info("Procesando → {} ({})", email, participa) ;
            alumnoHuelgaService.registrarOActualizar(huelga, alumno, participa) ;
        }
    }

    // Métodos auxiliares

    private boolean parseDecision(String decision)
    {
        if (decision == null) return false;

        decision = decision.toLowerCase().trim();

        return decision.equals("sí") || decision.equals("si") ;
    }
}