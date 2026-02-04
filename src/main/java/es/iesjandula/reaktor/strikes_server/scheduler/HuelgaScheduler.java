package es.iesjandula.reaktor.strikes_server.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;

@Component
public class HuelgaScheduler
{
    @Autowired
    private IHuelgaRepository huelgaRepository;

    @Scheduled(cron = "0 0 * * * *") // cada hora
    public void actualizarEstadosHuelga()
    {
        Date hoy = new Date();
        List<Huelga> huelgas = this.huelgaRepository.findAll();

        for (Huelga huelga : huelgas)
        {
            if (huelga.getFechaFin() != null &&
                huelga.getFechaFin().before(hoy) &&
                huelga.getEstado() == EstadoHuelga.CONVOCADA)
            {
                huelga.setEstado(EstadoHuelga.FINALIZADA);
            }
        }

        this.huelgaRepository.saveAll(huelgas);
    }
}