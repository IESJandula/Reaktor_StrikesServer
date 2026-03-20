package es.iesjandula.reaktor.strikes_server.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.services.GoogleScriptService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HuelgaScheduler
{
    @Autowired
    private IHuelgaRepository huelgaRepository;

    @Autowired
    private GoogleScriptService googleScriptService;

    /**
     * Se ejecuta cada hora
     */
    @Scheduled(cron = "0 0 * * * *")
    public void actualizarEstadosHuelga()
    {
        log.info("Iniciando scheduler de huelgas...");

        Date hoy = new Date();
        List<Huelga> huelgas = huelgaRepository.findByEstado(EstadoHuelga.CONVOCADA);

        for (Huelga huelga : huelgas)
        {
            try
            {
                actualizarEstado(huelga, hoy);

                if (huelga.getEstado() == EstadoHuelga.CONVOCADA)
                {
                    procesarRespuestas(huelga);
                }
            }
            catch (Exception e)
            {
                log.error("Error procesando huelga ID {}: {}",
                          huelga.getTitulo(),
                          e.getMessage());
            }
        }

        huelgaRepository.saveAll(huelgas);

        log.info("Scheduler finalizado.");
    }

    // ==========================================
    // Actualizar estado si ha finalizado
    // ==========================================
    private void actualizarEstado(Huelga huelga, Date hoy)
    {
        if (huelga.getFechaFin() != null &&huelga.getFechaFin().before(hoy) && huelga.getEstado() == EstadoHuelga.CONVOCADA)
        {
            huelga.setEstado(EstadoHuelga.FINALIZADA);
            log.info("Huelga " + huelga.getTitulo() + "finalizada automáticamente");
        }
    }

    // ==========================================
    // Procesar respuestas nuevas
    // ==========================================
    private void procesarRespuestas(Huelga huelga)
    {
        if (huelga.getGoogleSpreadsheetId() == null ||
            huelga.getGoogleSheetName() == null)
        {
            log.warn("Huelga {} sin recursos Google configurados", huelga.getTitulo());
            return;
        }

        List<Map<String, Object>> respuestas =
            googleScriptService.obtenerRespuestas(
                huelga.getGoogleSpreadsheetId(),
                huelga.getGoogleSheetName());

        if (respuestas.isEmpty())
            return;

        int ultimaFilaProcesada =
            huelga.getUltimaFilaProcesada() != null
                ? huelga.getUltimaFilaProcesada()
                : 0;

        for (int i = ultimaFilaProcesada; i < respuestas.size(); i++)
        {
            Map<String, Object> respuesta = respuestas.get(i);

            String email =
                ((String) respuesta.get("Correo electrónico"))
                .toLowerCase()
                .trim();

            String decision =
                (String) respuesta.get("¿Te inscribes en la huelga?");

            if (correoYaRegistrado(huelga, email))
                continue;

            procesarInscripcion(huelga, email, decision);
        }

        huelga.setUltimaFilaProcesada(respuestas.size());
    }

    // ==========================================
    // Métodos auxiliares
    // ==========================================
    private boolean correoYaRegistrado(Huelga huelga, String email)
    {
        // TODO: Implementar consulta real a BD
        return false;
    }

    private void procesarInscripcion(Huelga huelga, String email, String decision)
    {
    	log.info("Nueva respuesta recibida en huelga {} : {} {}", 
                huelga.getTitulo(), 
                email, 
                decision) ;

    }
}