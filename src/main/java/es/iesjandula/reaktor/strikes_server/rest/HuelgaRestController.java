package es.iesjandula.reaktor.strikes_server.rest;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.strikes_server.dtos.HuelgaRequestDto;
import es.iesjandula.reaktor.strikes_server.dtos.HuelgaResponseDto;
import es.iesjandula.reaktor.strikes_server.models.EstadoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.repository.IHuelgaRepository;
import es.iesjandula.reaktor.strikes_server.utils.Constants;
import es.iesjandula.reaktor.strikes_server.utils.StrikesServerException;
import lombok.extern.slf4j.Slf4j;
import java.util.Calendar;

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
            validarCrearHuelga(dto.getTitulo(),dto.getFechaInicio(),dto.getFechaFin());

            Date fechaInicio = toDate(dto.getFechaInicio());
            Date fechaFin = dto.getFechaFin() != null ? toDate(dto.getFechaFin()) : null;

            if (this.huelgaRepository.existsById(dto.getTitulo()))
            {
                throw new StrikesServerException(Constants.ERR_HUELGA_EXISTE_CODE, Constants.ERR_HUELGA_EXISTE_DESC);
            }

            // ==============================
            // LLAMAR A GOOGLE APPS SCRIPT
            // ==============================

            String webAppCreateUrl = "https://script.google.com/macros/s/AKfycbxBZMx9zV8tmk15DDtmf5bXA9xOzGSaZCtk65E6TorKkUoTfezQJd3PwgkaWNrsLGq05A/exec"; 

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("titulo", dto.getTitulo());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(webAppCreateUrl, request, Map.class);

            Map responseBody = response.getBody();

            if (responseBody == null || responseBody.get("formUrl") == null)
            {
                throw new RuntimeException("Error creando recursos en Google");
            }

            String formUrl = (String) responseBody.get("formUrl");
            String formId = (String) responseBody.get("formId");
            String spreadsheetId = (String) responseBody.get("spreadsheetId");
            String sheetName = (String) responseBody.get("sheetName");

            // ==============================
            // CREAR ENTIDAD
            // ==============================

            Huelga huelga = new Huelga();
            huelga.setTitulo(dto.getTitulo());
            huelga.setFechaInicio(fechaInicio);
            huelga.setFechaFin(fechaFin);
            huelga.setEstado(EstadoHuelga.CONVOCADA);
            huelga.setUrlEncuestado(formUrl);
            huelga.setGoogleFormId(formId);
            huelga.setGoogleSpreadsheetId(spreadsheetId);
            huelga.setGoogleSheetName(sheetName);

            this.huelgaRepository.saveAndFlush(huelga);

            HuelgaResponseDto responseDto = new HuelgaResponseDto(
                    huelga.getTitulo(),
                    huelga.getFechaInicio() != null ? huelga.getFechaInicio().getTime() : null,
                    huelga.getFechaFin() != null ? huelga.getFechaFin().getTime() : null,
                    huelga.getEstado() != null ? huelga.getEstado().name() : null,
                    huelga.getUrlEncuestado(),
                    0L
            );

            return ResponseEntity.ok(responseDto);
        }
        catch (StrikesServerException excepcion)
        {
            return ResponseEntity.badRequest().body(excepcion.getBodyExceptionMessage());
        }
        catch (Exception excepcion)
        {
            log.error("Error creando huelga", excepcion);
            return ResponseEntity.status(500).body("Error interno al crear huelga");
        }
    }

    @PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_PROFESOR + "')")
    @DeleteMapping("/{titulo}")
    public ResponseEntity<?> eliminarRecursosGoogle(@PathVariable String titulo)
    {
        try
        {
            if (titulo == null || titulo.isEmpty())
            {
                throw new StrikesServerException(Constants.ERR_HUELGA_TITULO_NULO_VACIO_CODE, Constants.ERR_HUELGA_TITULO_NULO_VACIO_DESC) ;
            }

            Huelga huelga = this.huelgaRepository.findById(titulo).orElseThrow(() ->
                    new StrikesServerException(Constants.ERR_HUELGA_NO_EXISTE_CODE,Constants.ERR_HUELGA_NO_EXISTE_DESC));

            // 🔹 Llamar a Apps Script para borrar Form y Sheet
            borrarRecursosGoogle(huelga);

            // 🔹 Limpiar datos Google en BD
            huelga.setGoogleFormId(null);
            huelga.setGoogleSpreadsheetId(null);
            huelga.setGoogleSheetName(null);
            huelga.setUrlEncuestado(null);

            this.huelgaRepository.save(huelga);

            return ResponseEntity.ok("Recursos Google eliminados correctamente");
        }
        catch (StrikesServerException exception)
        {
            return ResponseEntity.badRequest().body(exception.getBodyExceptionMessage());
        }
        catch (Exception excepcion)
        {
            log.error("Error al eliminar recursos Google", excepcion);
            return ResponseEntity.status(500).body("Error interno al eliminar recursos Google");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> obtenerHuelgas(@PageableDefault(size = 5, sort = "titulo") Pageable pageable)
    {
        try
        {
            Page<HuelgaResponseDto> dtoPage = this.huelgaRepository.findHuelgasResponseDto(pageable);

            return ResponseEntity.ok(dtoPage);
        }
        catch (Exception excepcion)
        {
            log.error("Error obteniendo huelgas", excepcion);

            StrikesServerException ex = new StrikesServerException( Constants.ERR_SERVIDOR_CODE, Constants.ERR_SERVIDOR, excepcion);

            return ResponseEntity.status(500).body(ex.getBodyExceptionMessage());
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

    private void validarCrearHuelga(String titulo,Long fechaInicio,Long fechaFin) throws StrikesServerException
    {
        if (titulo == null || titulo.isEmpty())
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

    }
    
    private void borrarRecursosGoogle(Huelga huelga) throws Exception
    {
        if (huelga.getGoogleFormId() == null ||
            huelga.getGoogleSpreadsheetId() == null)
        {
            throw new RuntimeException("No hay recursos Google para borrar");
        }

        String webAppDeleteUrl =
        "https://script.google.com/macros/s/AKfycbywUgI6wpxu-kfaPl8rKgW90EsBDcMr_n-8X0bq_f1IoonzoDGxAX4Y7Ff0SauqKlMtWQ/exec";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();

        body.put("token", "MI_TOKEN_SEGURO");   
        body.put("formId", huelga.getGoogleFormId());
        body.put("spreadsheetId", huelga.getGoogleSpreadsheetId());

        HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(body, headers);

        restTemplate.postForEntity(webAppDeleteUrl, request, String.class);
    }
    
}
