package es.iesjandula.reaktor.strikes_server.rest;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.strikes_server.dtos.HuelgaRequestDto;
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
    
    

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> crearHuelga(@RequestBody HuelgaRequestDto dto)
    {
        try
        {
            validarCrearHuelga(dto.getNombre(),dto.getFechaInicio(),dto.getFechaFin(), dto.getFechaLimite()) ;

            Date fechaInicio = toDate(dto.getFechaInicio()) ;
            Date fechaFin = dto.getFechaFin() != null ? toDate(dto.getFechaFin()): null ;
            Date fechaLimite = dto.getFechaLimite() != null ? toDate(dto.getFechaLimite()) : null ;

            if (this.huelgaRepository.existsById(dto.getNombre()))
            {
                throw new StrikesServerException(Constants.ERR_HUELGA_EXISTE_CODE, Constants.ERR_HUELGA_EXISTE_DESC) ;
            }

            Huelga huelga = new Huelga() ;
            huelga.setNombre(dto.getNombre()) ;
            huelga.setFechaInicio(fechaInicio) ;
            huelga.setFechaFin(fechaFin) ;
            huelga.setFechaLimite(fechaLimite) ;
            huelga.setEstado(EstadoHuelga.CONVOCADA) ;
            huelga.setUrlGoogleScript(dto.getUrlGoogleScript()) ;

            this.huelgaRepository.saveAndFlush(huelga) ;
            log.info(Constants.ELEMENTO_AGREGADO) ;

            return ResponseEntity.ok().build();
        }
        catch (StrikesServerException excepcion)
        {
            return ResponseEntity.badRequest().body(excepcion.getBodyExceptionMessage());
        }
        catch (Exception excepcion)
        {
        	StrikesServerException huelgaExcepcion = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion) ;
        	log.error("Error genérico al crear huelga", excepcion);
            return ResponseEntity.status(500).body(huelgaExcepcion.getBodyExceptionMessage());
        }
    }

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> eliminarHuelga(@PathVariable String nombre)
    {
        try
        {
            if (nombre == null || nombre.isEmpty())
            {
            	log.error(Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
                throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE, Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
            }

            if (!this.huelgaRepository.existsById(nombre))
            {
                throw new StrikesServerException( Constants.ERR_HUELGA_NO_EXISTE_CODE, Constants.ERR_HUELGA_NO_EXISTE_DESC);
            }

            this.huelgaRepository.deleteById(nombre);
            return ResponseEntity.ok().build();
        }
        catch (StrikesServerException exception)
        {
            return ResponseEntity.badRequest().body(exception.getBodyExceptionMessage());
        }
        catch (Exception excepcion)
        {
        	StrikesServerException huelgaExcepcion = new StrikesServerException(Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion) ;
        	log.error("Error genérico al eliminar huelga", excepcion);
            return ResponseEntity.status(500).body(huelgaExcepcion.getBodyExceptionMessage());
        }
    }

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @GetMapping("/")
    public ResponseEntity<?> obtenerHuelgas(@PageableDefault(size = 5,sort = "nombre") Pageable pageable)
    {
	    try
	    {
	    	return ResponseEntity.ok(this.huelgaRepository.findHuelgasResponseDto(pageable)) ;
	    }
	    catch (Exception excepcion)
		{
			StrikesServerException huelgaExcepcion= new StrikesServerException(Constants.ERR_SERVIDOR_CODE,Constants.ERR_SERVIDOR) ;
			log.error("Error genérico obtener el/las huelga/s", excepcion);
		    return ResponseEntity.status(500).body(huelgaExcepcion.getBodyExceptionMessage()) ;
		}
    }
    

    /* =========================
       MÉTODOS AUXILIARES
    ========================= */

    private Date toDate(Long fecha) throws StrikesServerException
    {
        if (fecha == null || fecha <= 0)
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHA_INICIO_NULA_CODE,Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC);
        }
        return new Date(fecha);
    }

    private void validarCrearHuelga(String nombre,Long fechaInicio,Long fechaFin, Long fechaLimite) throws StrikesServerException
    {
        if (nombre == null || nombre.isEmpty())
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE,Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC);
        }

        if (fechaInicio == null || fechaInicio <= 0)
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHA_INICIO_NULA_CODE,Constants.ERR_HUELGA_FECHA_INICIO_NULA_DESC);
        }

        if (fechaFin != null && fechaFin < fechaInicio)
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHAS_INCOHERENTES_CODE,Constants.ERR_HUELGA_FECHAS_INCOHERENTES_DESC);
        }
        
        if (fechaLimite <= 0 || fechaLimite == null)
        {
            throw new StrikesServerException(Constants.ERR_HUELGA_FECHAS_INCOHERENTES_CODE,Constants.ERR_HUELGA_FECHAS_INCOHERENTES_DESC);
        }

    }
}
